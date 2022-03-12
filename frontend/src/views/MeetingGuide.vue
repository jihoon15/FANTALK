<template>
  <div class="main-container">
    <v-card
      style="width: 60%; margin-top: 8%;"
      class="guide-container"
    >
      <div id="title">
        {{ title }}
      </div>
      <div id="open-date">
        팬미팅 날짜/시간: {{ openDate }}
      </div>
      <div class="notification-container">
        <div id="notification">
          <팬미팅 안내사항>
        </div>
        <div>
          <div id="first">
            1. 본인의 미팅 시간에 맞춰 입장해 주세요.
          </div>
          <div id="second">
            2. 사전에 받은 입장 코드를 입력해 주세요. (주의!! 한번 사용하신 입장 코드는 재사용할 수 없습니다.)
          </div>
          <div id="third">
            3. 팬미팅 중 비속어, 성희롱 등 팬미팅 에티켓에 어긋나는 행동은 삼가해 주세요.
          </div>
        </div>
      </div>

      <v-text-field
        v-model="enterCode"
        label="입장 코드를 입력해 주세요."
        solo
        required
        style="width: 60%; color: #DFDFDF; margin-left: 20%;"
        @keyup.enter="enterMeeting()"
      ></v-text-field>

      <v-btn
        @click="enterMeeting()"
        style="width: 25%; background-color: #797BF8; color: #FFFFFF; margin-left: 38%;"
      >
        입장
      </v-btn>
    </v-card>
  </div>
</template>

<script>
  const SERVER_URL = process.env.VUE_APP_API_URL
  export default {
    name: 'MeetingGuide',
    data: function() {
      return {
        meetingId: '',
        title: '팬미팅!',
        openDate: '2020.01.01 10:00',
        enterCode: '',
      }
    },
    methods:{
      setToken () {
        const token = localStorage.getItem('jwt')
        const config = {
          Authorization: `Bearer ${token}`
        }
        return config
      },
      enterMeeting: function () {
        const enterCodeItem = {
          enterCode: this.enterCode,
        }
        this.$axios({
          method: 'put',
          url: `${SERVER_URL}/api/v1/meetings/${this.meetingId}/enter`,
          data: enterCodeItem,
          headers: this.setToken(),
        })
        .then(res => {
          if (res.data === "SUCCESS") {
            this.$router.push({name:'MeetingRoom', params:{ meetingId: this.meetingId }})
          }
        })
        .catch(err => {
          if (err.response.data === "MEETING ING") {
            alert('현재 팬미팅이 진행중입니다. 잠시만 기다려주세요.')
          } else if (err.response.data === "NO ENTER TWICE") {
            alert('팬미팅을 중복해서 들어갈 수 없습니다.')
          } else if (err.response.data === "MANAGER NOT IN") {
            alert('아직 팬미팅이 시작하지 않았습니다. 잠시만 기다려주세요.')
          } else if (err.response.data === "Wrong EnterCode") {
            alert('코드를 다시 확인해 주세요.')
          } else {
            alert('로그인을 해주세요.')
          }
        })
        
      }
    },
    created: function () {
      this.meetingId = this.$route.params.meetingId

      this.$axios({
        method: 'get',
        url: `${SERVER_URL}/api/v1/meetings/${this.meetingId}`,
      })
      .then(res => {
        this.title = res.data.title
        this.content = res.data.content
      })
      .catch(() => {
      })
    }
  }
</script>

<style scoped>
  .main-container {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .guide-container {
    padding: 3%;
    display: flex;
    flex-direction: column;
  }

  #title {
    font-size: 1.6em;
    padding: 0 0 2% 0%;
    font-weight: bold;
    color: #797BF8;
    text-align: center;
  }

  #open-date {
    font-size: 1em;
    padding: 0 0 3% 65%;
    color: #ACAEFF;
    font-weight: bold;
  }

  .notification-container {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  #notification {
    font-size: 1.4em;
    padding: 0 0 3% 0;
    color: #FF6666;
    font-weight: bold;
  }

  #first {
    font-size: 1.1em;
    padding: 0 0 1% 0;
    color: #979797;
    font-weight: bold;
  }

  #second {
    font-size: 1.1em;
    padding: 0 0 1% 0;
    color: #979797;
    font-weight: bold;
  }

  #third {
    font-size: 1.1em;
    padding: 0 0 3% 0;
    color: #979797;
    font-weight: bold;
  }
</style>
