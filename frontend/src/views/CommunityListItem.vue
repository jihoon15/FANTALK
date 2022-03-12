<template>
<div class="main-container">

  <div class="main-container" style="display:flex;">

    <div style="padding: 6% 0 2% 2%;">
      <h2 style="padding: 0 0 0 3%;">{{communityName}} 팬 커뮤니티</h2>
      <img src="../assets/boardsidebar.png" style="padding: 10% 0 0 0;">
    </div>    

    <div class="community-list-item" style="width: 80%;">
      <br><br>
      <div style="display: flex; width: 60%;">
        <div style="width: 100%; margin-right: 10px;">
          <v-text-field
            label="제목"
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

      <br>
      <div class="community-list-item-list">
        <div class="community-list-item-list-articles-1" style="padding-bottom: 5px; border-style: solid; border-width: 0px 0px 2px 0px; border-color: gray;">
          <span style="width: 10%" class="community-list-item-list-articles-elements">조회수</span>
          <span style="width: 35%" class="community-list-item-list-articles-elements">제목</span>
          <span style="width: 20%" class="community-list-item-list-articles-elements">작성날짜</span>
          <span style="width: 35%" class="community-list-item-list-articles-elements">작성자</span>
        </div>
        <div v-for="(article, key) in articles" :key="article.key">
          <button v-if="key >= articleAtTop && key < articleAtTop + dataPerPage" @click="enterDetailArticle(article.articleId)" class="community-list-item-list-articles-2">
            <span style="width: 10%" class="community-list-item-list-articles-elements">{{ article.hits }}</span>
            <span style="width: 35%" class="community-list-item-list-articles-elements">{{ article.title }}</span>
            <span style="width: 20%" class="community-list-item-list-articles-elements">{{ article.createdAt.slice(0,19) }}</span>
            <span style="width: 35%" class="community-list-item-list-articles-elements">***{{ article.email.slice(3) }}</span>
          </button>
        </div>
      </div>
      <br>
      <div class="community-list-item-create">
        <v-btn
          rounded
          @click="enterCreateArticle()"
          style="background-color: #797BF8; color: white; height: 30px;"
        >
          글쓰기
        </v-btn>
      </div>
      <div id="pages"></div>
    </div>
    <div class="community-list-item-side">
    </div>
  </div>


</div>
</template>

<script>
  const SERVER_URL = process.env.VUE_APP_API_URL
  const dataPerPage = 10
  const pageCount = 3
  export default {
    name: 'CommunityListItem',
    components: {
    },
    data: function() {
      return {
        communityId: null,
        communityName: null,
        communityTitle: null,
        userEmail: '',
        articles: [],
        headers: [
          { 
            text: '', 
            value: 'articleId',
            sortable: false,
          },
          { 
            text: '제목', 
            value: 'title',
            sortable: false,
          },
          { 
            text: '작성날짜', 
            value: 'createdAt',
            sortable: false,
          },
          {
            text: '조회수',
            value: 'hits',
            sortable: false,
          },
        ],
        q: '',
        totalArticleCount: null,
        dataPerPage: dataPerPage,
        pageCount: pageCount,
        totalPage: null,

        articleAtTop: null,
        // firstPage: null,
        // lastPage: null,
      }
    },
    methods: {

      paginationSet: function () { // 페이지네이션 구현에 필요한 기본적인 값들을 설정
        // totalData : 전체 게시글 수
        // dataPerPage : 한 페이지에 보여지는 게시글 수
        // pageCount : 한 화면에 보여지는 페이지의 수 (4일 경우 --> [ 1 2 3 4 next ] [ prev 5 6 7 8 next ] [ prev 9 10 11 12 next ] [ prev 13 14 ] 이렇게 최대 4개씩 묶음)
        // totalPage : 전체 페이지 수
        const totalData = this.totalArticleCount // totalData : 전체 게시글 수
        const totalPage = Math.ceil(totalData / dataPerPage) // totalPage : 전체 페이지 수
        this.totalData = totalData
        this.totalPage = totalPage
      },

      paginationChange: function (currentPage) {
        var self = this;
        // event.preventDefault()

        const pageGroup = Math.ceil(currentPage / pageCount) // pageGroup : 현재 몇 번째 페이지 묶음에 있는지
        let last = pageGroup * pageCount // 현재 위치한 페이지 묶음의 마지막 페이지
        let first = last - (pageCount - 1) // 현재 위치한 페이지 묶음의 첫 번째 페이지
        if (last > this.totalPage) {last = this.totalPage}
        if (first < 1) {first = 1}
        if (this.totalPage <= 1) {first = last}
        this.articleAtTop = dataPerPage * currentPage - dataPerPage // 현재 보여지는 페이지의 제일 상단에 위치한 게시물의 전체 순번
        const next = last + 1 // 다음 묶음의 첫 번째 페이지
        const prev = first - 1 // 이전 묶음의 마지막 페이지

        const pages = document.querySelector("#pages")
        pages.setAttribute("class", "pagination-container")

        if (first > pageCount) {
          const prevBtn = document.createElement("button")
          prevBtn.append("<")
          prevBtn.setAttribute("class", "pagination-btn-prev-next")
          prevBtn.setAttribute("id", `page(${prev})`)
          prevBtn.addEventListener('click', function() {
            self.paginationMove(prev)
          })
          pages.appendChild(prevBtn)
        }
        for (let j = first; j <= last; j++) {
          const pageBtn = document.createElement("button")
          pageBtn.append((j))
          if (j === currentPage) {
            pageBtn.setAttribute("class", "pagination-btn-selected")
          } else {
            pageBtn.setAttribute("class", "pagination-btn")
          }
          pageBtn.setAttribute("id", `page(${j})`)
          pageBtn.addEventListener('click', function(){
            self.paginationMove(j);
          })
          pages.appendChild(pageBtn)
        }
        if (first < Math.ceil(this.totalPage / pageCount) * pageCount - (pageCount - 1)) {
          const nextBtn = document.createElement("button")
          nextBtn.setAttribute("class", "pagination-btn-prev-next")
          nextBtn.setAttribute("id", `page(${next})`)
          nextBtn.append(">")
          nextBtn.addEventListener('click', function() {
            self.paginationMove(next);
          })
          pages.appendChild(nextBtn)
        }

      },

      paginationMove: function (pageNum) {
        const pages = document.querySelector("#pages")
        pages.replaceChildren()
        this.paginationChange(pageNum)
      },

      enterDetailArticle: function (idx) {
        this.$router.push({name: 'DetailArticle', params:{ articleId:idx }})
        this.$router.push({name: 'DetailArticle', params:{ communityId:this.communityId }})
      },

      enterCreateArticle: function () {
        if (this.userEmail === '') {
          alert('로그인 후 이용해 주세요 :)')
          this.$router.push({name: 'Login'})
        }
        else {
          this.$router.push({name: 'CreateArticle'})
        }
      },
      search() {
        this.$axios({
          method: 'get',
          url: `${SERVER_URL}/api/v1/communities/${this.communityId}/search`,
          params: {
            q: this.q,
          }
        })
        .then(res => {
          const pagination = document.querySelector("#pages")
          pagination.innerHTML = ''
          this.articles = res.data
          this.totalArticleCount = this.articles.length
          this.paginationSet()
          this.paginationChange(1)
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
    },
    created: function () {
      this.communityId = this.$route.params.communityId
      // console.log(this.communityId)
      this.$axios({
        method:'get',
        url: `${SERVER_URL}/api/v1/communities/${this.communityId}/articles`
      })
      .then(res => {
        return res.data
      })
      .then(res => {
        this.articles = res
        this.totalArticleCount = this.articles.length
        
        this.paginationSet()
        this.paginationChange(1)
      })
      .catch(() => {
      })

      this.$axios({
        method:'get',
        url: `${SERVER_URL}/api/v1/communities/${this.communityId}`
      })
      .then(res => {
        return res.data
      })
      .then(res => {
        this.communityName = res.name
        this.communityTitle = res.title
        return res
      })
      .catch(() => {
      })

      this.$axios({
        method: 'get',
        url: `${SERVER_URL}/api/v1/users/me`,
        headers: this.setToken(),
      })
      .then(res => {
        this.userEmail = res.data.email
      })
      .catch(() => {
      })
    }
  }
</script>

<style>
  .community-list-item-side {
    display: flex;
    /* background-color: beige; */
    /* padding: 4%; */
    flex-direction: column;
  }
  .community-list-item {
    display: flex;
    /* background-color: beige; */
    padding: 4%;
    flex-direction: column;
  }
  .community-list-item-title {
    display: flex;
    flex-direction: column;
    /* background-color: bisque; */
    margin: 10px;
  }
  .community-list-item-list {
    display: flex;
    flex-direction: column;
  }
  .community-list-item-list-articles-1 {
    display: flex;
    width: 100%;
    flex-direction: row;
    justify-content: space-between;
    margin: 10px;
  }
  .community-list-item-list-articles-2 {
    display: flex;
    width: 100%;
    flex-direction: row;
    justify-content: space-between;
    margin: 10px;
  }
  .community-list-item-list-articles-2:hover {
    transition: 0.2s;
    transform: translateY(-2px);
    color: gray;
  }
  .community-list-item-list-articles-elements {
    display: flex;
    width: 100%;
    justify-content: center;
  }
  .community-list-item-create {
    display: flex;
    /* background-color: bisque; */
    flex-direction: row-reverse;
    margin: 10px;
  }
  .pagination-container {
    display: flex;
    flex-direction: row;
    justify-content: center;
  }
  .pagination-btn {
    /* background-color: whitesmoke; */
    border-radius: 5px;
    padding: 8px;
    margin: 8px;
  }
  .pagination-btn-selected {
    background-color: #797BF8;
    color: white;
    border-radius: 5px;
    padding: 8px;
    margin: 8px;
  }
  .pagination-btn-prev-next {
    background-color: lightgray;
    border-radius: 5px;
    padding: 8px;
    margin: 8px;
  
  }
</style>