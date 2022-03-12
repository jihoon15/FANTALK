<template>
  <div class="big-container">
    <div class="first-small-container">
      <div>
        <h2
          v-for="(t, index) in text1"
          :key="index"
          class="item"
          :style="{animationDelay: index*100+'ms'}"
          v-text="t"
        >
        </h2>
      </div>
      <div>
        <h2
          v-for="(t, index) in text2"
          :key="index"
          class="item"
          :style="{animationDelay: index*100+'ms'}"
          v-text="t"
        ></h2>
      </div>
    </div>

    <div class="second-small-container">
      <h2>FANTALK을 이용하는 방법</h2>
      <h4 style="padding: 2%;">총 3단계로 FANTALK을 이용해보세요!</h4>
    </div>

    <div class="third-small-container">
      <div style="padding: 0 5%; height: 50%;">
        <img src="../assets/1.png" alt="1" width="80%;">
      </div>
      <div style="text-align: center;">
        <h2 style="padding: 0 0 5% 0;">팬미팅 신청</h2>
        <h4>사전에 등록하신 기업회원은</h4>
        <h4>팬미팅을 신청해주세요.</h4>
      </div>
    </div>

    <div class="fourth-small-container">
      <div style="padding: 0 5%; height: 50%;">
        <img src="../assets/2.png" alt="2" width="80%;">
      </div>
      <div style="text-align: center;">
        <h2 style="padding: 0 0 5% 0;">1:1 팬미팅 방 입장</h2>
        <h4>팬미팅 입장을 클릭하고</h4>
        <h4>입장코드를 입력하여</h4>
        <h4>1:1 팬미팅 방으로 입장해주세요.</h4>
      </div>
    </div>

    <div class="fifth-small-container">
      <div style="padding: 0 5%; height: 50%;">
        <img src="../assets/3.png" alt="3" width="80%;">
      </div>
      <div style="text-align: center;">
        <h2 style="padding: 0 0 5% 0;">연예인과 팬미팅</h2>
        <h4>팬미팅 에티켓을 지키며</h4>
        <h4>연예인과 1:1 팬미팅을</h4>
        <h4>즐겨주세요.</h4>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Body',

    data: () => ({
      text1: '당신의 연예인과',
      text2: '지금 바로 만나보세요!',
    }),
  }
</script>

<style scoped>
  .big-container {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .first-small-container {
    width: 100%;
    padding: 6%;
    display: flex;
    flex-direction: column;
    align-items: center;
    color: #535353;
    background-image: url("../assets/main.svg");
    background-size: cover;
    font-size: 1.5em;
  }

  .second-small-container {
    display: flex;
    padding: 2%;
    flex-direction: column;
    align-items: center;
    color: #797BF8;
    font-size: 1.3em;
    animation: text-in .2s cubic-bezier(0.22, 0.15, 0.25, 1.43) 0s backwards;
  }

  .third-small-container {
    display: flex;
    justify-content: flex-start;
    width: 100%;
    padding: 3% 5% 3% 10%;
    background-color: #EBEBFF;
    color: #03B962;
    font-size: 1.1em;
    animation: text-in .6s cubic-bezier(0.22, 0.15, 0.25, 1.43) 0s backwards;
  }


  .fourth-small-container {
    display: flex;
    justify-content: flex-end;
    width: 100%;
    padding: 3% 10% 3% 5%;
    background-color: #DADBFF;
    color: #FF6666;
    font-size: 1.1em;
    animation: text-in 1.0s cubic-bezier(0.22, 0.15, 0.25, 1.43) 0s backwards;
  }

  .fifth-small-container {
    display: flex;
    justify-content: flex-start;
    width: 100%;
    padding: 3% 5% 3% 10%;
    background-color: #ACAEFF;
    color: #FFD98E;
    font-size: 1.1em;
    animation: text-in 1.4s cubic-bezier(0.22, 0.15, 0.25, 1.43) 0s backwards;
  }
  
  .item {
    display: inline-block;
    min-width: 0.3em;
    font-size: 2rem;
    animation: text-in .8s cubic-bezier(0.22, 0.15, 0.25, 1.43) 0s backwards;
  }

  @keyframes text-in {
    0% {
      transform: translate(0, -20px);
      opacity: 0;
    }
  }
</style>