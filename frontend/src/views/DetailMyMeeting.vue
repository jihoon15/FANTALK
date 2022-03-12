<template>
  <div>
    <img src="../assets/sidemymeetinglist.png" alt="check-manager" style="padding: 6% 0 2% 2%;" @click="moveMyMeetingList">
    <div style="display: flex; justify-content: center;">
      <div style="margin: 20px; padding: 20px; width: 70%;">
        <h2 style="margin-bottom: 20px; padding-bottom: 10px; color: #797BF8;">
          팬미팅 상세 조회
        </h2>
        <div style="display: flex; justify-content: center;">
          <v-card
            style="padding: 20px; border: 1px solid #979797; width: 100%;"
          >
            <template>
              <h3 style="margin-top: 10px; margin-bottom: 10px;">
                {{ title }}
              </h3>

              <v-row style="border-bottom: 1px solid #979797; margin: 0px 0px 10px 0px;">
                <v-col
                  cols="12"
                  sm="6"
                  md="6"
                  style="padding: 12px 12px 12px 0px;"
                >
                  <h5>날짜/시간</h5>
                  <v-card outlined style="padding: 3px; height: 32px;">
                    <div>
                      {{ openDate }}
                    </div>
                  </v-card>
                </v-col>
                
                <v-col
                  cols="12"
                  sm="6"
                  md="6"
                  style="padding: 12px 0px 12px 12px;"
                >
                  <h5>인원</h5>
                  <v-card outlined style="padding: 3px; height: 32px;">
                    <div>
                      {{ maxUser }}
                    </div>
                  </v-card>
                </v-col>
              </v-row>
              
              <div style="color: #FF0000; font-size: 1.2em; font-weight: bold; padding: 1% 0 0 0;">
                팬미팅 신청 안내 사항
              </div>
              <ul style="list-style-type: square; color: #979797; font-weight: bold; margin-left: 20px; margin-top: 10px;">
                <li>팬미팅 제목을 지정해 주세요. ex) 'TIMEROOM'의 올해 첫 1:1 팬미팅</li>
                <li>1:1 팬미팅 시작 날짜와 시간을 지정해 주세요. ex) 2022-01-01 15:00</li>
                <li>위 항목들을 입력 후 제출하시면, 본 기업회원의 이메일로 기업회원의 입장 코드를 포함한 전체 입장 코드 목록을 제공합니다.</li>
                <li>팬미팅에 참여하는 인원들에게 각 입장 코드와 입장 시간을 배부하면, 원활한 팬미팅 진행을 할 수 있습니다.</li>
                <li>팬미팅을 주관하는 기업회원은 시작 시간 전에 미리 리허설을 진행할 수 있습니다.</li>
                <li>기업회원이 팬미팅 방에 들어가 있지 않으면 팬들이 입장하지 못하니 이 점 꼭 유의해 주세요.</li>
                <li>이후 문의사항은 timeroom@ssafy.com으로 문의해주시면 감사하겠습니다.</li>
              </ul>
              
              <div style="display: flex; justify-content: end; margin-top: 30px;">
                <v-btn @click="edit()" style="background-color: #797BF8; color: #FFFFFF; margin: 0 10px;">수정</v-btn>
              </div>
            </template>
          </v-card>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
  const SERVER_URL = process.env.VUE_APP_API_URL
  export default {
    name: 'DetailMyMeeting',
    data: function() {
      return {
        meetingId: '',
        title: '',
        maxUser: '',
        openDate: '',
      }
    },

    methods: {
      setToken () {
        const token = localStorage.getItem('jwt')
        const config = {
          Authorization: `Bearer ${token}`
        }
        return config
      },
      edit () {
        this.$router.push({ name: 'UpdateMeeting' })
      },
      moveMyMeetingList: function () {
        this.$router.push({name: 'MyMeetingList'})
      },
    },

    created: function () {
      this.meetingId = this.$route.params.meetingId

      this.$axios({
        method: 'get',
        url: `${SERVER_URL}/api/v1/meetings/me/${this.meetingId}`,
        headers: this.setToken(),
      })
      .then(res => {
        this.title = res.data.title
        this.openDate = res.data.openDate.slice(0, 16)
        this.maxUser = res.data.maxUser
      })
      .catch(() => {
      })
    }
  }
</script>

<style scoped>
  img:hover {
    cursor: pointer
  }
</style>