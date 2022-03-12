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
          label="이름"
          v-model="credentials.name"
        >
        </v-text-field>
      </div>
      <div style="width: 300px;">
        <v-text-field
          label="이메일"
          v-model="credentials.email"
        >
        </v-text-field>
      </div>
      <div style="width: 300px;">
        <v-text-field
          label="비밀번호"
          v-model="credentials.password"
          type="password"
        >
        </v-text-field>
      </div>
      <div style="width: 300px;">
        <v-text-field
          label="비밀번호확인"
          v-model="credentials.passwordCheck"
          type="password"
        >
        </v-text-field>
        <div style="color: red; float: right;" v-if="credentials.passwordCheck != credentials.password">불일치</div>
        <div style="color: #797BF8; float: right;" v-if="credentials.password && credentials.passwordCheck === credentials.password">일치</div>
      </div>
      <div>
        <v-btn
          @click="signup()"
          style="background-color: #797BF8; color: #FFFFFF; width: 300px; margin: 30px 0;"
          :disabled="!credentials.passwordCheck || !credentials.password || credentials.password != credentials.passwordCheck"
        >
          회원가입
        </v-btn>
      </div>
      <div style="margin-bottom: 30px;">
        이미 회원이신가요? 
        <router-link style="text-decoration: none; color: #797BF8;" :to="{ name: 'Login' }">로그인하기</router-link>
      </div>
    </v-card>
  </div>
</template>

<script>
  const SERVER_URL = process.env.VUE_APP_API_URL
  export default {
    name: "Signup",
    components:{
      
    },
    data(){
      return{
        credentials: {
          id:'',
          name:'',
          password: '',
          passwordCheck: '',
          email: '',
        }  
      }
    },
    methods: {
      signup () {
        const signUpItem = {
          id: this.credentials.id,
          name: this.credentials.name,
          password: this.credentials.password,
          email: this.credentials.email,
        }

        this.$axios({
          method: 'post',
          url: `${SERVER_URL}/api/v1/users`,
          data: signUpItem,
        })
        .then(() => {
          this.$router.push({ name: 'Login' })
        })
        .catch(err => {
          if (err.response.status === 403) {
            alert('아이디가 존재합니다.')
          }
        })
      },
    }
  }
</script>
