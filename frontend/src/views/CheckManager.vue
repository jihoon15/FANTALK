<template>
  <div>
    <img src="../assets/sidemanagercheck.png" alt="check-manager" style="padding: 6% 0 2% 2%;">
    <div class="container">
      <h1 style="padding: 0 0 2% 0; font-size: 2.5em;">기업회원 확인</h1>
      <button @click="checkManager()" style="padding: 0.7% 7% 0.7% 7%; background-color: #797BF8; color: #FFFFFF">확인</button>
    </div>
  </div>
</template>

<script>
  const SERVER_URL = process.env.VUE_APP_API_URL
  export default {
    name: 'CheckManager',
    methods: {
      setToken () {
        const token = localStorage.getItem('jwt')
        const config = {
          Authorization: `Bearer ${token}`
        }
        return config
      },
      checkManager() {
        this.$axios({
          method: 'get',
          url: `${SERVER_URL}/api/v1/meetings/isManager`,
          headers: this.setToken(),
        })
        .then(res => {
          if (res.data === "SUCCESS") {
            this.$router.push({name: 'MyMeetingList'})
          }
        })
        .catch(err => {
          if (err.response.status === 400) {
            alert('기업회원이 아닙니다. 자세한 사항은 timeroom@ssafy.com에 문의해주세요.')
            this.$router.push({name: 'Index'})
          } else {
            alert('로그인 후 이용해 주세요 :)')
            this.$router.push({name: 'Login'})
          }
        })
      },
    }
  }
</script>

<style scoped>
  .container {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
</style>