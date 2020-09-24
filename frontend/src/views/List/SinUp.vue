<template>
  <sin-up-page @listup="onSubmit">
  </sin-up-page>
</template>

<script>
import SinUpPage from '../../components/List/SinUpPage.vue'
import axios from 'axios'
import router from '../../router'
import { mapGetters, mapState } from 'vuex'

export default {
  name: 'SinUp',
  components: {
    SinUpPage
  },
  data: () => ({
    id: '',
    max: 0,
    min: 0
  }),
  computed: {
    ...mapGetters(['getid']),
    ...mapState(['pylist'])
  },
  methods: {
    onSubmit (payload) {
      this.id = this.getid
      payload.id = this.id
      const { title, file, ex, id } = payload
      console.log('payload.id : ' + payload.id)
      axios.post('http://localhost:1234/member/sinup', { title, file, ex, id })
        .then(res => {
          alert('확인')
          router.push('/myarea')
        })
        .catch(err => {
          alert(err.response.data)
        })
    }
  }
}
</script>

<style>
</style>
