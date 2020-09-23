import {
  FINDONE,
  MAINBOARD,
  MYBOARD,
  PYREMBER
} from './mutation-types'

import axios from 'axios'
//  import router form '../router'

export default {
  rememberid ({ commit }, a) {
    console.log('rememberid: ' + a)
    commit(FINDONE, a)
  },
  mainBoarder ({ commit }) {
    return axios.get('http://localhost:1234/member/mainmember')
      .then(res => {
        console.log('res.data : ' + res.data)
        commit(MAINBOARD, res.data)
      })
  },
  pylistrember ({ commit }, pylist) {
    console.log('pylistrember: ' + pylist)
    commit(PYREMBER, pylist)
  },
  async myBoarder ({ commit }, id) {
    console.log('action id : ' + id)
    return axios.post('http://localhost:1234/member/mymember', { id })
      .then(res => {
        commit(MYBOARD, res.data)
      })
  }
}
