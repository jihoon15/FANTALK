<template>
  <div>
    <template>
      <v-form
        ref="form"
        v-model="valid"
        lazy-validation
      >

        <v-text-field
          v-model="title"
          :counter="50"
          :rules="titleRules"
          label="제목"
          solo
          required
        ></v-text-field>

        <v-textarea
          v-model="content"
          label="내용"
          rows="10"
          row-height="60"
          solo
        ></v-textarea>

        <div style="display: flex; justify-content: center;">
          <v-btn @click="goBack()" style="background-color: #979797; color: #FFFFFF; margin: 0 10px;">취소</v-btn>
          <v-btn @click="edit()" style="background-color: #797BF8; color: #FFFFFF; margin: 0 10px;">수정</v-btn>
          <v-btn @click="deleteArticle()" style="background-color: #FF6666; color: #FFFFFF; margin: 0 10px; position: absolute; right: 10px;">삭제</v-btn>
        </div>
      </v-form>
    </template>
  </div>
</template>

<script>
  const SERVER_URL = process.env.VUE_APP_API_URL
  export default {
    name: 'UpdateArticleForm',

    data: function() {
      return {
        valid: true,
        communityId: '',
        title: '',
        titleRules: [
          v => !!v || '제목은 필수입니다.',
          v => (v && v.length <= 50) || '제목은 50자 이하이어야 합니다.'
        ],
        content: '',
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
      goBack () {
        this.$router.push({ name: 'DetailArticle', params: {communityId: this.communityId, articleId: this.articleId} })
      },
      edit () {
        const articleItem = {
          title: this.title,
          content: this.content,
        }
        if (articleItem.title) {
          this.$axios({
            method: 'put',
            url: `${SERVER_URL}/api/v1/communities/${this.communityId}/articles/${this.articleId}`,
            data: articleItem,
            headers: this.setToken(),
          })
          .then(() => {
            this.title = ''
            this.content = ''
            this.$router.push({ name: 'DetailArticle', params: {communityId: this.communityId, articleId: this.articleId} })
          })
          .catch(err => {
            console.log(err)
          })
        }
      },
      deleteArticle () {
        this.$axios({
          method: 'delete',
          url: `${SERVER_URL}/api/v1/communities/${this.communityId}/articles/${this.articleId}`,
          headers: this.setToken(),
        })
        .then(() => {
          this.$router.push({name:'CommunityListItem', params:{ communityId: this.communityId }})
        })
        .catch(err => {
          console.log(err)
        })
      }
    },

    created: function () {
      this.communityId = this.$route.params.communityId
      this.articleId = this.$route.params.articleId

      this.$axios({
        method: 'get',
        url: `${SERVER_URL}/api/v1/communities/${this.communityId}/articles/${this.articleId}`,
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