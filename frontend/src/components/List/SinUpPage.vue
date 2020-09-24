<template>
  <Layout>
    <template #proj>
      <v-row>
        <v-col/>
        <v-col>
          <v-text-field
          label="제목"
          v-model="title"/>
        </v-col>
        <v-col/>
      </v-row>
      <v-row>
        <v-col/>
        <v-col>
          <div id="getimg" v-if="inputpoto">
            <table>
              <tr>
                <td width=100%>
                  <file-pond
                    name="bin"
                    ref="pond"
                    allow-multiple="false"
                    max-files="1"
                    accepted-file-types="image/*"
                    :server="server"
                  />
                </td>
                <td alight="center">
                  <v-btn @click="imgokbtn(nick)">확인</v-btn>
                </td>
              </tr>
            </table>
          </div>
          <div id="getimg" v-else>
            <div id="poto2" ref="chartdiv"/>
            <div id="poto3">
              <v-row>
                <v-col id="minanme">최소</v-col>
                <v-col><input type="number" id="min" v-model="mina"/></v-col>
                <v-col id="maxanme">최대</v-col>
                <v-col><input type="number" id="max" v-model="maxa"/></v-col>
                <v-col><v-btn alight="center" @click="plu(mina, maxa)">확인</v-btn></v-col>
                <v-col><p>{{ plue }}</p></v-col>
              </v-row>
            </div>
          </div>
        </v-col>
        <v-col/>
      </v-row>
      <v-row>
        <v-col cols="5%"/>
        <v-col cols="90%">
          <v-textarea
            no-resize
            outlined
            rows="10"
            v-model="ex"
          />
        </v-col>
        <v-col cols="5%"/>
      </v-row>
      <div align="center">
        <v-btn @click="signup()" id="okbtn">확인</v-btn>
        <v-btn @click="$router.push('/')"> 취소</v-btn>
      </div>
    </template>
  </Layout>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import Layout from '@/components/Layout.vue'
import vueFilePond from 'vue-filepond'
import 'filepond/dist/filepond.min.css'
import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type'
import axios from 'axios'
import * as am4core from '@amcharts/amcharts4/core'
import * as am4charts from '@amcharts/amcharts4/charts'
import animated from '@amcharts/amcharts4/themes/animated'
am4core.useTheme(animated)

const FilePond = vueFilePond(FilePondPluginFileValidateType)
export default {
  components: {
    Layout,
    FilePond
  },
  data () {
    return {
      k: 1,
      filename: '',
      inputpoto: true,
      title: '',
      ex: '',
      file: '',
      nick: '',
      num: 1,
      gra: [],
      mina: 0,
      maxa: 0,
      plue: null,
      server: {
        url: 'http://localhost:1234/file',
        process: {
          url: '/file'
        },
        revert: {
          url: '/remove',
          method: 'POST'
        }
      }
    }
  },
  computed: {
    ...mapState(['pylist'])
  },
  methods: {
    ...mapActions(['pylistrember']),
    signup () {
      console.log('ex : ' + this.ex + ' fil : ' + this.file + ' title : ' + this.title)
      const { title, file, ex } = this
      this.$emit('listup', { title, file, ex })
    },
    submit () {},
    imgokbtn (nick) {
      this.inputpoto = false
      // console.log('myFiles : ' + nick)
      const file = this.$refs.pond.getFile()
      nick = file.filename
      console.log('file Name : ' + nick)
      axios.post('http://127.0.0.1:5000/img', { nick })
        .then(res => {
          console.log('res.data : ' + res.data)
          this.pylistrember(res.data)
          this.gra = res.data
          this.file = res.data.join('-')
          console.log('file : ' + this.file)
          this.grapclick(0, 0)
        })
        .catch(err => {
          alert(err)
        })
    },
    grapclick (min, max) {
      console.log('max : ' + max + ' min : ' + min)
      console.log('getid : ' + this.getid)
      this.id = this.getid

      // this.gra = this.pylist.slice()
      console.log('gra : ' + this.gra)
      // console.log('mounted pylist : ' + this.pylist.slice())

      // 넣는 위치 정하기
      const chart = am4core.create('poto2', am4charts.XYChart)
      // chart.data = generateChartData()

      // 데이터 넣기
      const chartData = []
      let visits = 0
      //  x 축의 원소 개수
      for (var i = 0; i < 255; i++) {
        const newDate = i
        var grapedata = Number(this.gra.slice()[i])
        visits = grapedata
        chartData.push({
          num: newDate,
          visits: visits,
          max: max,
          min: min
        })
      }
      chart.data = chartData.slice()

      // Create axes
      const xAxis = chart.xAxes.push(new am4charts.ValueAxis())
      xAxis.max = 255
      xAxis.min = 1
      xAxis.strictMinMax = true

      const yAxis = chart.yAxes.push(new am4charts.ValueAxis())
      yAxis.strictMinMax = true

      //  차트 선긋기 만들기
      const series1 = chart.series.push(new am4charts.LineSeries())
      series1.dataFields.valueX = 'num'
      series1.dataFields.valueY = 'visits'
      series1.strokeWidth = 2
      series1.baseAxis = xAxis
      if (max > min) {
        const series2 = chart.series.push(new am4charts.LineSeries())
        series2.dataFields.valueX = 'max'
        series2.dataFields.valueY = 'visits'
        series2.strokeWidth = 1
        series2.baseAxis = xAxis

        const series3 = chart.series.push(new am4charts.LineSeries())
        series3.dataFields.valueX = 'min'
        series3.dataFields.valueY = 'visits'
        series3.strokeWidth = 1
        series3.baseAxis = xAxis
        this.plue = 0
        for (var j = min; j < max; j++) {
          this.plue += Number(this.gra.slice()[j])
        }
      }
    },
    plu (min, max) {
      if (max > min) {
        this.grapclick(min, max)
      } else {
        alert('잘못 입력하셨습니다.')
        this.grapclick(0, 0)
      }
    }
  }
}
</script>

<style>
.filepond--root {
  max-height: 100px;
}
.filepond--panel-root {
    background-color: #fff;
    border: 2px solid #2c3340;
}
#poto2 {
  border: solid 2px black;
  height: 300px;
}
#okbtn {
  margin-right: 40px;
}
.col {
  padding: -100px;
  margin-bottom : -10px;
}
#min {
  width: 80px;
  margin: -35px;
  border: solid 1px black;
}
#minanme {
  text-align: center;
  margin: 0px;
}
#max {
  width: 80px;
  margin: -35px;
  border: solid 1px black;
}
#maxanme {
  text-align: center;
  margin: 0px;
}
//  div { border: solid 2px black; }
</style>
