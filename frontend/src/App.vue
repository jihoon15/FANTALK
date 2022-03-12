<template>
  <div id="app">
    <Nav />
    <router-view/>
  </div>
</template>

<script>
import Nav from "./components/Nav.vue"

export default {
  name: 'App',
  components: {
    Nav,
  },
  data: function() {
  return {
    }
  },
}
</script>

<style>
  @font-face {
    font-family: 'MaplestoryOTFBold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/MaplestoryOTFBold.woff') format('woff');
    font-weight: normal;
    font-style: normal;
  }

  body {
    font-family: 'MaplestoryOTFBold';
  }
</style>