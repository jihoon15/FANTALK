<template>
  <div>
    <Body />
    <Footer />
  </div>
</template>

<script>
  import Body from '../components/Body.vue'
  import Footer from '../components/Footer.vue'

  export default {
    name: 'Index',
    components: {
      Body, Footer
    },
    data: function() {
      return {
      }
    },
  }
</script>