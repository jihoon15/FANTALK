<template>
  <v-toolbar>
    <router-link style="text-decoration: none; color: #797BF8; font-size: 1.2em; font-weight: bold; position: absolute;" :to="{name: 'Index'}">
        FANTALK
    </router-link>
    <v-toolbar-items class="container">
      <router-link style="text-decoration: none; color: #000000; font-weight: bold" :to="{name: 'MeetingList'}">
        팬미팅 입장
      </router-link>
      <router-link style="text-decoration: none; color: #000000; font-weight: bold" :to="{name: 'CommunityList'}">
        팬 커뮤니티
      </router-link>
      <router-link style="text-decoration: none; color: #000000; font-weight: bold" :to="{name: 'CheckManager'}">
        팬미팅 신청
      </router-link>
    </v-toolbar-items>
    <div style="position: absolute; right: 10px; display: flex; align-items: center;">
      <div v-if="userId != 0" style="margin-right: 10px; font-size: 12px;">
        <span style="font-weight: bold;">
          {{ username }}
        </span>        
        님 환영합니다.
      </div>
      <v-btn style="background-color: #FFFFFF; font-weight: bold;">
        <div v-if="userId == 0">
          <router-link style="text-decoration: none; color: #000000;" :to="{name: 'Login'}">
            로그인
          </router-link>
        </div>
        <div v-else>
          <div @click="logout()">
            로그아웃
          </div>
        </div>
      </v-btn>
    </div>
  </v-toolbar>
</template>

<script>
  const SERVER_URL = process.env.VUE_APP_API_URL
  export default {
    name: 'Nav',
    data: function() {
      return {
        userId: 0,
        username: '',
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
      logout () {
        localStorage.removeItem('jwt')
        this.userId = 0
        location.reload();
      }
    },
    created: function () {
      this.$axios({
        method: 'get',
        url: `${SERVER_URL}/api/v1/users/me`,
        headers: this.setToken(),
      })
      .then(res => {
        this.userId = res.data.id
        this.username = res.data.name
      })
      .catch(() => {
      })
    }
  }
</script>

<style scoped>
  .container {
    width: 80%;
    display: flex-start;
    justify-content: space-between;
    align-items: center;
    padding: 1.2% 17%;
  }
  
  .login {
    padding: 0 5%;
    font-size: 1em;
  }
</style>