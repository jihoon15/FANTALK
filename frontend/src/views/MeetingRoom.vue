<template>
	<div class="main-container">
		<div class="video-container" v-if="session">
			<div>
				<div>
					<user-video :stream-manager="publisher" class="user-video"/>
				</div>

				<div v-if="subscribers.length != 0">
					<user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" class="sub-video" />
				</div>
			</div>

			<div class="button-container">
				<span @click='muteAudio'>
					<div v-if='publisher.stream.audioActive'>
						<img src="../assets/mic-on.png" alt="mic-on" width="80%;">
					</div>

					<div v-else>
						<img src="../assets/mic-off.png" alt="mic-off" width="80%;">
					</div>
				</span>

				<span @click='muteVideo'>
					<div v-if='publisher.stream.videoActive'>
						<img src="../assets/cam-on.png" alt="cam-on" width="80%;">
					</div>

					<div v-else>
						<img src="../assets/cam-off.png" alt="cam-off" width="80%;">
					</div>
				</span>
				
				<img src="../assets/exit.png" alt="Leave session" @click="leaveSession" style="padding: 0 2% 0 2%;" width="20%;">
			</div>

		</div>
		<div class="chat-container">
			<Chatting
				:session="session"
				:myUserName="myUserName"
			/>
		</div>
	</div>
</template>

<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '../components/UserVideo';
import Chatting from '../components/Chatting.vue';

axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":8443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";
const SERVER_URL = process.env.VUE_APP_API_URL

export default {
	name: 'App',

	components: {
		UserVideo, Chatting
	},

	data () {
		return {
			OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],
			mySessionId: '',
			myUserName: '',
			title: '',
			meetingId: '',
		}
	},

	created: function () {
		this.meetingId = this.$route.params.meetingId
		this.mySessionId = this.meetingId.toString()

		this.$axios({
			method: 'get',
			url: `${SERVER_URL}/api/v1/users/me`,
			headers: this.setToken(),
		})
		.then(res => {
			this.myUserName = res.data.name
		})
		.catch(() => {
		})

		// --- Get an OpenVidu object ---
		this.OV = new OpenVidu();

		// --- Init a session ---
		this.session = this.OV.initSession();

		// --- Specify the actions when events take place in the session ---

		// On every new Stream received...
		this.session.on('streamCreated', ({ stream }) => {
			const subscriber = this.session.subscribe(stream);
			this.subscribers.push(subscriber);
		});

		// On every Stream destroyed...
		this.session.on('streamDestroyed', ({ stream }) => {
			const index = this.subscribers.indexOf(stream.streamManager, 0);
			if (index >= 0) {
				this.subscribers.splice(index, 1);
			}
		});

		// On every asynchronous exception...
		this.session.on('exception', ({ exception }) => {
			console.warn(exception);
		});

		// --- Connect to the session with a valid user token ---

		// 'getToken' method is simulating what your server-side should do.
		// 'token' parameter should be retrieved and returned by your own backend
		this.getToken(this.mySessionId).then(token => {
			this.session.connect(token, { clientData: this.myUserName })
				.then(() => {

					// --- Get your own camera stream with the desired properties ---

					let publisher = this.OV.initPublisher(undefined, {
						audioSource: undefined, // The source of audio. If undefined default microphone
						videoSource: undefined, // The source of video. If undefined default webcam
						publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
						publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
						resolution: '640x400',  // The resolution of your video
						frameRate: 30,			// The frame rate of your video
						insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
						mirror: false       	// Whether to mirror your local video or not
					});

					this.mainStreamManager = publisher;
					this.publisher = publisher;

					// --- Publish your stream ---

					this.session.publish(this.publisher);
				})
				.catch(error => {
					console.log('There was an error connecting to the session:', error.code, error.message);
				});
		});

			window.addEventListener('beforeunload', this.leaveSession)
	},

	methods: {
		setToken () {
      const token = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },
		leaveSession () {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			if (this.session) this.session.disconnect();

			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;

			window.removeEventListener('beforeunload', this.leaveSession);

			this.$axios({
        method: 'put',
        url: `${SERVER_URL}/api/v1/meetings/${this.meetingId}/exit`,
				headers: this.setToken(),
      })
      .then(() => {
        this.$router.push({name:'MeetingList'})
      })
		},

		getToken (mySessionId) {
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
		createSession (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
		createToken (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},

		muteAudio() {
      if (this.publisher.stream.audioActive) {
        this.publisher.publishAudio(false)
      }else {
        this.publisher.publishAudio(true)
      }
    },

    muteVideo() {
      if (this.publisher.stream.videoActive) {
        this.publisher.publishVideo(false)
      } else {
        this.publisher.publishVideo(true)
      }
    },
	}
}
</script>

<style scoped>
	img:hover {
		cursor: pointer;
	}

	.main-container {
		padding: 2% 0 0 2%;
		display: flex;
		justify-content: center;
	}

	.video-container {
		display: flex;
		flex-direction: column;
	}

	.button-container {
		display: flex;
		justify-content: center;
		padding: 2% 6% 0 6%;
	}

	.chat-container {
		padding: 0 0 0 10%;
	}

	.user-video {
		display: flex;
		flex-direction: column;
		align-items: center;
		font-size: 1.3em;
		font-weight: bold;
		color: #797BF8;
		padding: 0 0 3% 0;
	}
	
	.sub-video {
		display: flex;
		flex-direction: column;
		align-items: center;
		font-size: 1.3em;
		font-weight: bold;
		color: #797BF8;
		padding: 0 0 3% 0;
	}

	video {
		width: 900px;
	}
</style>
