<template>
  <Layout>
    <template #proj>
      <v-row>
        <v-col/>
        <v-col>
          <div id="title">{{ title }}</div>
        </v-col>
        <v-col/>
      </v-row>
      <v-row>
        <v-col/>
        <v-col>
          <div id="getpoto">
            <v-row>
              <v-col>
                <div id="poto2"/>
              </v-col>
            </v-row>
          </div>
        </v-col>
        <v-col/>
      </v-row>
      <v-row>
        <v-col cols="5%"/>
        <v-col cols="90%">
          <div id="bodytext">
            {{ ex }}
          </div>
        </v-col>
        <v-col cols="5%"/>
      </v-row>
      <div align="center">
        <v-btn  @click="$router.push('/')">메인으로</v-btn>
      </div>
    </template>
  </Layout>
</template>

<script>
import Layout from '@/components/Layout.vue'
import router from '../../router'
import * as am4core from '@amcharts/amcharts4/core'
import * as am4charts from '@amcharts/amcharts4/charts'
import animated from '@amcharts/amcharts4/themes/animated'
am4core.useTheme(animated)

export default {
  components: {
    Layout
  },
  data: () => ({
    k: 1,
    gra: [],
    inputpoto: true,
    grapedata: 0
  }),
  props: {
    title: String,
    file: String,
    ex: String
  },
  methods: {
    signup () {
      console.log('ex : ' + this.ex + ' fil : ' + this.fil + ' title : ' + this.title)
      router.push('/')
    }
  },
  mounted () {
    console.log('Show file : ' + this.file)
    // 넣는 위치 정하기
    const chart = am4core.create('poto2', am4charts.XYChart)
    // chart.data = generateChartData()

    // 데이터 넣기
    const chartData = []
    let visits = 0
    //  x 축의 원소 개수
    this.grapedata = this.file.split('-')
    for (var i = 0; i < 255; i++) {
      const newDate = i
      console.log('grape : ' + this.grapedata[i])
      visits = this.grapedata[i]
      console.log('grapedata : ' + this.grapedata)
      chartData.push({
        num: newDate,
        visits: visits
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
  }
}
</script>

<style>
#title {
  margin-top: 40px;
  height: 30px;
  border-bottom: solid 1px black;
}
#bodytext {
  border: solid 1px black;
}
#poto2 {
  width: 500px;
  height: 400px;
}
//  div { border: solid 2px black; }
</style>
