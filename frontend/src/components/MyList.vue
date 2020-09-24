<template>
  <v-main>
    <Layout/>
    <v-row rows="1" id="srrg">
      <v-col cols="3" align="left" mb="1">
        <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details/>
      </v-col>
    </v-row>
    <v-data-table
      id="tables"
      :headers="headers"
      :items="mainboard"
      :search="search"
      class="numbering"
      >
      <template v-slot:item.title="{ item }">
        <div @click="read(item.memberNo)">{{ item.title }}</div>
      </template>
    </v-data-table>
  </v-main>
</template>
<script>
import Layout from '@/components/Layout.vue'
import router from '../router'
import axios from 'axios'
import { mapGetters } from 'vuex'
export default {
  computed: {
    ...mapGetters(['getid'])
  },
  data () {
    return {
      message: '',
      TF: false,
      search: '',
      headers: [
        {
          text: 'No.',
          align: 'center',
          sortable: false,
          filterable: false,
          value: 'memberNo',
          width: '10%'
        },
        {
          text: '제목',
          align: 'center',
          sortable: false,
          value: 'title'
        },
        {
          text: '아이디',
          align: 'center',
          sortable: false,
          filterable: false,
          value: 'id',
          width: '15%'
        }
      ]
    }
  },
  components: {
    Layout
  },
  props: {
    mainboard: {
      type: Array
    }
  },
  methods: {
    sendMessage () {
      this.message = ''
    },
    logout () {
      this.TF = false
      router.push('/')
    },
    read (memberNo) {
      console.log('number : ' + memberNo)
      axios.post('http://localhost:1234/member/readmember', { memberNo })
        .then(res => {
          console.log('res.data : ' + res.data)
          router.push({ name: 'Show', params: { title: res.data.title, file: res.data.file, ex: res.data.ex } })
        })
        .catch(err => {
          alert(err.response.data)
        })
    }
  },
  numbering () {
    this.num = this.num + 1
  }
}
</script>

<style>
#srrg {
  padding-top:30px;
  margin-left:60px;
  margin-right:60px;
}
</style>
