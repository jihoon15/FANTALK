<template>
	<div>
    <div class="meeting-list-cards-container">
      <div style="display: flex; justify-content: space-between; width: 100%;">
        <div style="">
          <h2 style="font-size: 1.5em;">팬미팅 목록</h2>
        </div>
        <div style="display: flex; width: 60%;">
          <div style="width: 100%; margin-right: 10px;">
            <v-text-field
              label="제목"
              dense
              solo
              v-model="q"
              @keyup.enter="search()"
            >
            </v-text-field>
          </div>
          <v-btn
            rounded
            @click="search()"
            style="background-color: gray; color: white; height: 38px;"
          >
            검색
          </v-btn>
        </div>
        <div></div>
      </div>

      <div class="meeting-list-cards">
        <div class="meeting-list-cards-column">
          <button v-for="meeting in meetingList1" :key="meeting.id" class="meeting-list-card" @click="enterMeetingGuide(meeting.id)" style="background-color: #FF6666;">
            <div class="meeting-list-card-title">
              {{ meeting.title }}
            </div>
            <div class="meeting-list-card-openDate">
              {{ meeting.openDate }}
            </div>
          </button>
        </div>
        <div class="meeting-list-cards-column">
          <button v-for="meeting in meetingList2" :key="meeting.id" class="meeting-list-card" @click="enterMeetingGuide(meeting.id)" style="background-color: #F7A400;">
            <div class="meeting-list-card-title">
              {{ meeting.title }}
            </div>
            <div class="meeting-list-card-openDate">
              {{ meeting.openDate }}
            </div>
          </button>
        </div>
        <div class="meeting-list-cards-column">
          <button v-for="meeting in meetingList3" :key="meeting.id" class="meeting-list-card" @click="enterMeetingGuide(meeting.id)" style="background-color: #03B962;">
            <div class="meeting-list-card-title">
              {{ meeting.title }}
            </div>
            <div class="meeting-list-card-openDate">
              {{ meeting.openDate }}
            </div>
          </button>
        </div>
        <div class="meeting-list-cards-column">
          <button v-for="meeting in meetingList4" :key="meeting.id" class="meeting-list-card" @click="enterMeetingGuide(meeting.id)" style="background-color: #ACAEFF;">
            <div class="meeting-list-card-title">
              {{ meeting.title }}
            </div>
            <div class="meeting-list-card-openDate">
              {{ meeting.openDate }}
            </div>
          </button>
        </div>
      </div>
    </div>
	</div>
</template>

<script>
  const SERVER_URL = process.env.VUE_APP_API_URL
  export default {
    name: 'MeetingList',
    data: function() {
      return {
        meetingList: [],
        meetingList1: [],
        meetingList2: [],
        meetingList3: [],
        meetingList4: [],
        q: '',
        userEmail: '',
      }
    },
    methods:{
      enterMeetingGuide: function (idx) {
        if (this.userEmail === '') {
          alert('로그인 후 이용해 주세요 :)')
          this.$router.push({name: 'Login'})
        }
        else {
          this.$router.push({name:'MeetingGuide', params:{ meetingId:idx }})
        }
      },
      search() {
        this.$axios({
          method: 'get',
          url: `${SERVER_URL}/api/v1/meetings/search`,
          params: {
            q: this.q,
          }
        })
        .then(res => {
          this.meetingList = res.data
          this.meetingList1 = []
          this.meetingList2 = []
          this.meetingList3 = []
          this.meetingList4 = []

          for (var i = 0; i < this.meetingList.length; i++) {
            if (i % 4 === 0){
              this.meetingList1.push(res.data[i])
            }else if (i % 4 === 1){
              this.meetingList2.push(res.data[i])
            }else if (i % 4 === 2){
              this.meetingList3.push(res.data[i])
            }else if (i % 4 === 3){
              this.meetingList4.push(res.data[i])
            }
          }
        })
        .catch(() => {
        })
      },
      setToken () {
        const token = localStorage.getItem('jwt')
        const config = {
          Authorization: `Bearer ${token}`
        }
        return config
      },
    },
    created: function () {
      this.$axios({
        method: 'get',
        url: `${SERVER_URL}/api/v1/meetings`
      })
      .then(res => {
        this.meetingList = res.data

        for (var i = 0; i < this.meetingList.length; i++) {
          if (i % 4 === 0){
            this.meetingList1.push(res.data[i])
          }else if (i % 4 === 1){
            this.meetingList2.push(res.data[i])
          }else if (i % 4 === 2){
            this.meetingList3.push(res.data[i])
          }else if (i % 4 === 3){
            this.meetingList4.push(res.data[i])
          }
        }

        return this.meetingList
      })
      .catch(() => {
      })

      this.$axios({
        method: 'get',
        url: `${SERVER_URL}/api/v1/users/me`,
        headers: this.setToken(),
      })
      .then(res => {
        this.userEmail = res.data.email
      })
      .catch(() => {
      })
    }
  }
</script>

<style>
  .meeting-list-cards-container {
    width: 100%;
    display: flex;
    padding: 5%;
    flex-direction: column;
    align-items: left;
  }

  .meeting-list-cards {
    display: flex;
    justify-content: center;
    margin: 0 10% 0 10%;
  }

  .meeting-list-cards-column {
    display: flex;
    flex-direction: column;
    width: 20%;
    margin: 1%;
  }

  .meeting-list-card {
    display: flex;
    flex-flow: column;
    align-items: center;
    justify-content: center;
    padding: 5%;
    margin: 10px 10px 30px 10px;
    height: 11vw;
    color: white;
    border-radius: 15px;
  }

  .meeting-list-card:hover {
    transition: 0.3s;
    transform: translateY(-5px);
    opacity: 0.8;
  }

  .meeting-list-card-title {
    margin: 10px;
    font-size: 1.4vw;
    font-weight: bold;
  }

  .meeting-list-card-openDate {
    margin: 10px;
    font-size: 1.2vw;
  }
</style>