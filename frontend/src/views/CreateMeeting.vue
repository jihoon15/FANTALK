<template>
  <div>
    <img src="../assets/sidemymeetinglist.png" alt="check-manager" style="padding: 6% 0 2% 2%;" @click="moveMyMeetingList()">
    <div style="display: flex; justify-content: center;">
      <div style="margin: 20px; padding: 20px; width: 70%;">
        <h2 style="margin-bottom: 20px; padding-bottom: 10px; color: #797BF8;">
          팬미팅 신청
        </h2>
        <div style="display: flex; justify-content: center; width: 100%;">
          <v-card
            style="padding: 20px; border: 1px solid #979797; width: 100%;"
          >
            <CreateMeetingForm />
          </v-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import CreateMeetingForm from '../components/CreateMeetingForm.vue'
  export default {
    name: 'CreateMeeting',
    components: {
      CreateMeetingForm
    },
    methods : {
      setToken () {
        const token = localStorage.getItem('jwt')
        const config = {
          Authorization: `Bearer ${token}`
        }
        return config
      },
      moveMyMeetingList: function () {
        this.$router.push({name: 'MyMeetingList'})
      },
    },
  }
</script>

<style scoped>
  img:hover {
    cursor: pointer
  }
</style>