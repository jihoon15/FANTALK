<template>
  <div>
    <div class="community-list-cards-container">
      <div style="display: flex; justify-content: space-between; width: 100%;">
        <div style="">
          <h2 style="font-size: 1.5em;">팬 커뮤니티 목록</h2>
        </div>
        <div style="display: flex; width: 60%;">
          <div style="width: 100%; margin-right: 10px;">
            <v-text-field
              label="이름"
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
        <div v-if="userId == 1 || userId == 2 || userId == 3 || userId == 4">
          <v-btn
          rounded
          @click="createCommunity"
          style="background-color: #797BF8; color: white; height: 30px;"
          >
          등록
          </v-btn>
        </div>
        <div v-else>
        </div>
      </div>

      <div class="community-list-cards">
        <div class="community-list-cards-column">
          <button v-for="community in communityList1" :key="community" class="community-list-card" @click="enterCommunity(community.id)" style="background-color: #FF6666;">
            <div class="community-list-card-circle" style="background-color: #FF8C8C;">
              <div class="community-list-card-name">
                {{ community.name }}
              </div>
            </div>
            <div class="community-list-card-title">
              {{ community.title }}
            </div>
          </button>
        </div>
        <div class="community-list-cards-column">
          <button v-for="community in communityList2" :key="community" class="community-list-card" @click="enterCommunity(community.id)" style="background-color: #F7A400;">
            <div class="community-list-card-circle" style="background-color: #FFC85C;">
              <div class="community-list-card-name">
                {{ community.name }}
              </div>
            </div>
            <div class="community-list-card-title">
              {{ community.title }}
            </div>
          </button>
        </div>
        <div class="community-list-cards-column">
          <button v-for="community in communityList3" :key="community" class="community-list-card" @click="enterCommunity(community.id)" style="background-color: #03B962;">
            <div class="community-list-card-circle" style="background-color: #14D076;">
              <div class="community-list-card-name">
                {{ community.name }}
              </div>
            </div>
            <div class="community-list-card-title">
              {{ community.title }}
            </div>
          </button>
        </div>
        <div class="community-list-cards-column">
          <button v-for="community in communityList4" :key="community" class="community-list-card" @click="enterCommunity(community.id)" style="background-color: #ACAEFF;">
            <div class="community-list-card-circle" style="background-color: #D3D3FF;">
              <div class="community-list-card-name">
                {{ community.name }}
              </div>
            </div>
            <div class="community-list-card-title">
              {{ community.title }}
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
    name: 'CommunityList',
    data: function() {
      return {
        communityList: [],
        communityList1: [],
        communityList2: [],
        communityList3: [],
        communityList4: [],
        q: '',
        userId: '',
      }
    },
    methods:{
      enterCommunity: function (idx) {
        this.$router.push({name:'CommunityListItem', params:{ communityId:idx }})
      },
      search() {
        this.$axios({
          method: 'get',
          url: `${SERVER_URL}/api/v1/communities/search`,
          params: {
            q: this.q,
          }
        })
        .then(res => {
          this.communityList = res.data
          this.communityList1 = []
          this.communityList2 = []
          this.communityList3 = []
          this.communityList4 = []

          for (var i = 0; i < this.communityList.length; i++) {
            if (i % 4 === 0){
              this.communityList1.push(res.data[i])
            } else if (i % 4 === 1) {
              this.communityList2.push(res.data[i])
            } else if (i % 4 === 2) {
              this.communityList3.push(res.data[i])
            } else if (i % 4 === 3) {
              this.communityList4.push(res.data[i])
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

      createCommunity () {
        this.$router.push({name: 'CreateCommunity'})
      }
    },
    created: function () {
      this.$axios({
        method: 'get',
        url: `${SERVER_URL}/api/v1/communities`
      })
      .then(response => {
        return response.data
      })
      .then(response => {
        this.communityList = response

        for (var i = 0; i < this.communityList.length; i++) {
          if (i % 4 === 0){
            this.communityList1.push(response[i])
          }else if (i % 4 === 1){
            this.communityList2.push(response[i])
          }else if (i % 4 === 2){
            this.communityList3.push(response[i])
          }else if (i % 4 === 3){
            this.communityList4.push(response[i])
          }
        }
        return this.communityList
      })
      .catch(() => {
      })

      this.$axios({
        method: 'get',
        url: `${SERVER_URL}/api/v1/users/me`,
        headers: this.setToken(),
      })
      .then(res => {
        this.userId = res.data.id
      })
      .catch(() => {
      })
    }
  }
</script>

<style>
  .community-list-cards-container {
    width: 100%;
    display: flex;
    padding: 5%;
    flex-direction: column;
    align-items: left;
  }

  .community-list-title {
    display: flex;
  }

  .community-list-cards {
    display: flex;
    justify-content: center;
    margin: 0 10% 0 10%;
  }

  .community-list-cards-column {
    display: flex;
    flex-direction: column;
    width: 20%;
    margin: 1%;
  }

  .community-list-card {
    display: flex;
    flex-flow: column;
    align-items: center;
    justify-content: center;
    padding: 5%;
    margin: 10px 10px 30px 10px;
    color: white;
    border-radius: 15px;
  }

  .community-list-card:hover {
    transition: 0.3s;
    transform: translateY(-5px);
    opacity: 0.8;
  }

  .community-list-card-circle {
    border:1px;
    border-radius: 5vw;
    width: 10vw;
    height: 10vw;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .community-list-card-name {
    margin: 10px;
    font-size: 2.0vw;
    font-weight: bold;
  }

  .community-list-card-title {
    margin: 30px;
    font-size: 1.2vw;
  }
</style>
