<template>
  <div style="display: flex; justify-content: center;">
    <div style="margin: 20px; padding: 20px; width: 700px;">
      <h3 style="margin-bottom: 20px; padding-bottom: 10px;">
        자유게시판
      </h3>
      <div style="display: flex; justify-content: center;">
        <v-card
          style="padding: 20px; border: 1px solid #979797; width: 700px;"
        >
          <UpdateArticleForm />
        </v-card>
      </div>
    </div>
  </div>
</template>

<script>
  import UpdateArticleForm from '../components/UpdateArticleForm.vue'

  export default {
    name: 'UpdateArticle',
    components: {
      UpdateArticleForm,
    },
    data: function() {
      return {
      }
    },
  }
</script>