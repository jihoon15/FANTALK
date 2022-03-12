<template>
	<div>
    <img src="../assets/sidemymeetinglist.png" alt="check-manager" style="padding: 6% 0 2% 2%;" @click="moveMyMeetingList()">
    <div class="meeting-list-item" style="width: 80%;">
      <h2 style="padding: 2% 0; font-size: 1.5em;">신청한 팬미팅 목록</h2>

      <div class="community-list-item-list">
        <div class="community-list-item-list-meetings-1" style="padding-bottom: 5px; border-style: solid; border-width: 0px 0px 2px 0px; border-color: gray;">
          <span style="width: 60%" class="community-list-item-list-meetings-elements">제목</span>
          <span style="width: 20%" class="community-list-item-list-meetings-elements">날짜/시간</span>
          <span style="width: 20%" class="community-list-item-list-meetings-elements">신청인원</span>
        </div>
        <div v-for="(meeting, key) in meetings" :key="meeting.key">
          <button v-if="key >= meetingAtTop && key < meetingAtTop + dataPerPage" @click="enterDetailMeeting(meeting.id)" class="community-list-item-list-meetings-2">
            <span style="width: 60%" class="community-list-item-list-meetings-elements">{{ meeting.title }}</span>
            <span style="width: 20%" class="community-list-item-list-meetings-elements">{{ meeting.openDate }}</span>
            <span style="width: 20%" class="community-list-item-list-meetings-elements">{{ meeting.maxUser }}</span>
          </button>
        </div>
      </div>
      <div id="pages"></div>
      <v-btn
        rounded
        @click="moveCreateMeeting()"
        style="background-color: #797BF8; color: white; height: 30px; width: 30px; margin-left: 95%;"
      >
        신청
      </v-btn>
    </div>
	</div>
</template>

<script>
  const SERVER_URL = process.env.VUE_APP_API_URL
  const dataPerPage = 5
  const pageCount = 3
  export default {

    name: 'MyMeetingList',
    data () {
      return {
        headers: [
          {
            text: '제목',
            value: 'title',
            sortable: false,
          },
          { 
            text: '날짜/시간', 
            value: 'openDate',
            sortable: false,
          },
          { 
            text: '신청인원', 
            value: 'maxUser',
            sortable: false,
          },
        ],
        meetings: [],

        totalMeetingCount: null,
        dataPerPage: dataPerPage,
        pageCount: pageCount,
        totalPage: null,
        totalData: null,
        meetingAtTop: null,
      }
    },
    methods : {
      setToken () {
        const token = localStorage.getItem('jwt')
        const config = {
          Authorization: `Bearer ${token}`
        }
        return config
      },

      paginationSet: function () { // 페이지네이션 구현에 필요한 기본적인 값들을 설정
        // totalData : 전체 게시글 수
        // dataPerPage : 한 페이지에 보여지는 게시글 수
        // pageCount : 한 화면에 보여지는 페이지의 수 (4일 경우 --> [ 1 2 3 4 next ] [ prev 5 6 7 8 next ] [ prev 9 10 11 12 next ] [ prev 13 14 ] 이렇게 최대 4개씩 묶음)
        // totalPage : 전체 페이지 수
        const totalData = this.totalMeetingCount // totalData : 전체 게시글 수
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
        this.meetingAtTop = dataPerPage * currentPage - dataPerPage // 현재 보여지는 페이지의 제일 상단에 위치한 게시물의 전체 순번
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

      enterDetailMeeting: function (idx) {
        this.$router.push({name: 'DetailMyMeeting', params:{ meetingId:idx }})
      },

      moveMyMeetingList: function () {
        this.$router.push({name: 'MyMeetingList'})
      },

      moveCreateMeeting: function () {
        this.$router.push({name: 'CreateMeeting'})
      }
    },

    created: function () {
      this.$axios({
        method: 'get',
        url: `${SERVER_URL}/api/v1/meetings/me`,
        headers: this.setToken(),
      })
      .then(res => {
        this.meetings = res.data
        this.totalMeetingCount = this.meetings.length

        this.paginationSet()
        this.paginationChange(1)
      })
      .catch(() => {
      })
    },
  }
</script>

<style scoped>
  img:hover {
    cursor: pointer;
  }

  .meeting-list-item {
    display: flex;
    flex-direction: column;
    margin-left: 14%;
  }

  .community-list-item-list-meetings-1 {
    display: flex;
    width: 100%;
    flex-direction: row;
    justify-content: space-between;
    margin: 10px;
  }

  .community-list-item-list-meetings-2 {
    display: flex;
    width: 100%;
    flex-direction: row;
    justify-content: space-between;
    margin: 10px;
  }

  .community-list-item-list-meetings-2:hover {
    transition: 0.2s;
    transform: translateY(-2px);
    color: gray;
  }

  .community-list-item-list-meetings-elements {
    display: flex;
    width: 100%;
    justify-content: center;
  }
</style>