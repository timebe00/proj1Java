<template>
  <sin-up-page @listup="onSubmit">
    <template #test>
      <div id="chartdiv" :style="{ height: '400px' }"></div>
    </template>
  </sin-up-page>
</template>

<script>
import SinUpPage from '../../components/List/SinUpPage.vue'
import axios from 'axios'
import router from '../../router'
import { mapGetters, mapState } from 'vuex'
import * as am4core from '@amcharts/amcharts4/core'
import * as am4charts from '@amcharts/amcharts4/charts'
import animated from '@amcharts/amcharts4/themes/animated'
am4core.useTheme(animated)

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
  },
  mounted (min, max) {
    const chart = am4core.create('chartdiv', am4charts.XYChart)

    // Add data
    chart.data = [{
      ax: 1,
      ay: 0.5,
      bx: 4,
      by: 0
    }, {
      ax: 2,
      ay: 1.3,
      bx: 4,
      by: 1
    }, {
      ax: 3,
      ay: 2.3,
      bx: 4,
      by: 2
    }, {
      ax: 4,
      ay: 2.8,
      bx: 4,
      by: 3
    }, {
      ax: 5,
      ay: 3.5,
      bx: 4,
      by: 4
    }, {
      ax: 6,
      ay: 5.1,
      bx: 4,
      by: 5
    }, {
      ax: 7,
      ay: 6.7,
      bx: 4,
      by: 6
    }, {
      ax: 8,
      ay: 8,
      bx: 4,
      by: 7
    }, {
      ax: 9,
      ay: 8.9,
      bx: 4,
      by: 8
    }, {
      ax: 10,
      ay: 9.7,
      bx: 4,
      by: 9
    }, {
      ax: 11,
      ay: 10.4,
      bx: 4,
      by: 10
    }, {
      ax: 12,
      ay: 11.7,
      bx: 4,
      by: 25
    }]

    // Create axes
    const xAxis = chart.xAxes.push(new am4charts.ValueAxis())
    xAxis.max = 12
    xAxis.min = 1
    xAxis.strictMinMax = true

    const yAxis = chart.yAxes.push(new am4charts.ValueAxis())
    yAxis.max = 25
    yAxis.min = 0
    yAxis.strictMinMax = true

    // Create series
    const series1 = chart.series.push(new am4charts.LineSeries())
    series1.dataFields.valueX = 'ax'
    series1.dataFields.valueY = 'ay'
    series1.strokeWidth = 3

    const series2 = chart.series.push(new am4charts.LineSeries())
    series2.dataFields.valueX = 'bx'
    series2.dataFields.valueY = 'by'
    series2.strokeWidth = 3

    // Scrollbars
    chart.scrollbarX = new am4core.Scrollbar()
    chart.scrollbarY = new am4core.Scrollbar()
  }
}
</script>

<style>
</style>
