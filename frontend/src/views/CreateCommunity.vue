<template>
  <div style="display: flex; justify-content: center;">
    <v-card
      style="margin: 20px; padding: 20px; outline: 1px solid #979797; width: 500px;"
    >
      <CreateCommunityForm />
    </v-card>
  </div>
</template>

<script>
  import CreateCommunityForm from '../components/CreateCommunityForm.vue'

  export default {
    name: 'CreateCommunity',
    components: {
      CreateCommunityForm,
    },
    data: function() {
      return {
      }
    },
  }
</script>