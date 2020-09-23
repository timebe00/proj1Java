<template>
  <main-page>
    <template #iconinput>
      <v-app-bar-nav-icon clipped-right @click.stop="drawer = !drawer"/>
      <v-navigation-drawer v-model="drawer">
        <v-list-item @click.stop="left = !left">
          <v-list-item-action>
            <v-icon>mdi-key-variant</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title @click="home">비밀번호 변경</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-navigation-drawer>
    </template>
    <template #content>
      <v-data-table
        id="tables"
        :headers="headers"
        :items="myboard"
        :search="search"
        >
        <template v-slot:item.title="{ item }">
          <div @click="read(item.memberNo)">{{ item.title }}</div>
        </template>
        <template v-slot:item.tru="{ item }">
          <v-icon @click="dle(item.memberNo)">mdi-delete</v-icon>
        </template>
      </v-data-table>
    </template>
  </main-page>
</template>

<script>
import MainPage from '@/components/MainPage.vue'
import router from '../router'
import axios from 'axios'
export default {
  name: 'MyArealist',
  components: {
    MainPage
  },
  data () {
    return {
      drawer: false,
      left: false,
      search: '',
      headers: [
        {
          text: 'No.',
          align: 'center',
          sortable: false,
          filterable: false,
          value: 'memberNo'
        },
        {
          text: '제목',
          align: 'center',
          sortable: false,
          value: 'title'
        },
        {
          text: '삭제',
          align: 'center',
          sortable: false,
          filterable: false,
          value: 'tru'
        }
      ]
    }
  },
  props: {
    myboard: {
      type: Array
    }
  },
  methods: {
    home () {
      (window.location.pathname !== '/myarea/relode') ? router.push('/myarea/relode') : router.go(0)
    },
    dle (memberNo) {
      console.log('memberNo : ' + memberNo)
      this.$emit('del', memberNo)
    },
    read (memberNo) {
      console.log('memberNo : ' + memberNo)
      axios.post('http://localhost:1234/member/readmember', { memberNo })
        .then(res => {
          console.log('res.data : ' + res.data)
          router.push({ name: 'Show', params: { title: res.data.title, file: res.data.file, ex: res.data.ex } })
        })
        .catch(err => {
          alert(err.response.data)
        })
    }
  }
}
</script>

<style>
#tables {
  padding-top:0px;
  margin-left:60px;
  margin-right:60px;
  margin-top:-30px
}
</style>
