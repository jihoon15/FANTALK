<template>
  <div style="display: flex; justify-content: center;">        
    <v-card style="width: 500px; display: flex; flex-flow: column; align-items: center; margin-top: 100px;">
      <div style="color: #797BF8; font-size: 1.2em; font-weight: bold; margin: 30px 0;">
        FANTALK
      </div>
      <div style="width: 300px;">
        <v-text-field
          label="아이디"
          v-model="credentials.id"
        >
        </v-text-field>
      </div>
      <div style="width: 300px;">
        <v-text-field
          label="비밀번호"
          v-model="credentials.password"
          type="password"
          @keyup.enter="login()"
        >
        </v-text-field>
      </div>
      <div>
        <v-btn
          @click="login()"
          style="background-color: #797BF8; color: #FFFFFF; width: 300px; margin: 30px 0; font-weight: bold;"
        >
          로그인
        </v-btn>
      </div>
      <div style="margin-bottom: 30px;">
        계정이 없으신가요? 
        <router-link style="text-decoration: none; color: #797BF8;" :to="{ name: 'Signup' }">회원가입하기</router-link>
      </div>
    </v-card>
  </div>
</template>

<script>
  const SERVER_URL = process.env.VUE_APP_API_URL
  export default {
    name: "Login",
    data: function () {
      return {
        credentials: {
          id: '',
          password: '',
          accessToken: null,
        }
      }
    },
    methods: {
      login () {
        const loginItem = {
          id: this.credentials.id,
          password: this.credentials.password,
        }

        this.$axios({
            method: 'post',
            url: `${SERVER_URL}/api/v1/users/login`,
            data: loginItem,
          })
        .then((res) => {
          localStorage.setItem('jwt', res.data.accessToken)
          this.credentials.accessToken = res.data.accessToken
          this.$router.push({ name: 'Index' })
          location.reload();
        })
        .catch(() => {
          alert('아이디나 비밀번호를 다시 확인해주세요.')
        })
      }
    }
  }
</script>
