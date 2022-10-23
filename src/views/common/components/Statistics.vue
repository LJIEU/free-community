<template>
  <div class="mod-demo-echarts">
    <el-row>
      <el-card shadow="never">
        <h3 style="margin: 0;padding: 0;">数据统计</h3>
      </el-card>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card>
          <div id="J_postLineChart" class="Statistics-chart-box"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import echarts from 'echarts'
// npm install file-saver以及npm install xlsx => 0.16版本
import FileSaver from 'file-saver'
import XLSX from 'xlsx/xlsx.mini'

export default {
  data () {
    return {
      // postLineChart: null
    }
  },
  created () {
  },
  mounted () {
    this.initPostLineChart()
  },
  activated () {
    // 由于给echart添加了resize事件, 在组件激活时需要重新resize绘画一次, 否则出现空白bug
    // if (this.postLineChart) {
    //   this.postLineChart.resize()
    // }
  },
  methods: {
    // 发帖数据折线图
    initPostLineChart () {
      var option = {
        'title': {
          'text': '发帖数据'
        },
        'tooltip': {
          'trigger': 'axis'
        },
        animationDuration: 3000, // 绘制速度 3000ms
        'legend': {
          'data': ['发帖量']
        },
        'grid': {
          'left': '3%',
          'right': '4%',
          'bottom': '3%',
          'containLabel': true
        },
        'toolbox': {
          'feature': {
            dataView: {
              show: true,
              title: '数据视图',
              lang: ['数据视图', '关闭', '导出Excel'],
              // optionToContent为重画表格的函数
              optionToContent: function (opt) {
                // axisData是你想定义的表格第一列的数据，我这里设置为柱形图的x轴数据
                var axisData = opt.xAxis[0].data
                var series = opt.series
                // 表头
                var tdHeads = ''
                var tdBodys = ''
                console.log(axisData)
                // console.log('表头1:', opt.xAxis[0].name)
                // console.log('表头2:', series[0].name)
                tdHeads += '<td style="padding: 0 10px">' + opt.xAxis[0].name + '</ td >'
                tdHeads += '<td style="padding: 0 10px">' + series[0].name + '</ td >'
                var table = '<table id="Mytable" border="1" class="table table-bordered table-striped table-hover" style="width:100%;text-align:center" ><tbody><tr>' + tdHeads + ' </tr>'
                for (let i = 0, l = axisData.length; i < l; i++) {
                  for (var j = 0; j < series.length; j++) {
                    var temp = series[j].data[i]
                    if (temp != null && temp !== undefined) {
                      tdBodys += '<td>' + temp + '</td>'
                    } else {
                      tdBodys += '<td></td>'
                    }
                  }
                  table += '<tr><td style="padding: 0 10px">' + axisData[i] + '</td>' + tdBodys + '</tr>'
                  tdBodys = ''
                }
                table += '</tbody></table>'
                return table
              },
              // contentToOption为重写“刷新”按钮的语句
              contentToOption: function (HTMLDomElement, opt) {
                let et = XLSX.utils.table_to_book(
                  document.getElementById('Mytable')
                )
                let etout = XLSX.write(et, {
                  bookType: 'xlsx',
                  bookSST: true,
                  type: 'array'
                })
                try {
                  FileSaver.saveAs(
                    new Blob([etout], {
                      type: 'application/octet-stream'
                    }),
                    (new Date().getMonth() + 1) + '月' + '-' + new Date().toLocaleString() + '.xlsx'
                  )
                } catch (e) {
                }
                return etout
              }
            },
            magicType: {show: true},
            restore: {show: true},
            saveAsImage: {show: true}
            // dataZoom: {show: true}
          }
        },
        'xAxis': {
          name: '日期',
          'type': 'category',
          'boundaryGap': true,
          alignTicks: true,
          axisLine: {
            lineStyle: {
              color: 'rgba(72,174,171,1)',
              width: 2
            }
          },
          'data': ['10-1', '10-2', '10-3', '10-4', '10-5', '10-6', '10-7']
        },
        'yAxis': {
          'type': 'value',
          axisLine: {
            lineStyle: {
              color: 'rgba(72,174,171,1)',
              width: 2
            }
          }
        },
        'series': [
          {
            'name': '发帖量',
            'type': 'line',
            'stack': '发帖总量',
            'data': [100, 200, 300, 400, 50, 1, 4],
            'smooth': true, // 平滑线条
            scale: true,
            itemStyle: {
              normal: {
                color: 'rgba(66,184,131,1)',
                lineStyle: { // 折线颜色
                  color: 'rgba(2,233,233,1)'
                }
              }
            },
            axisTick: {
              show: true
            }
            /*             areaStyle: {
                          normal: {
                            color: '#091e3b' // 改变区域颜色
                          }
                        } */
          }
        ]
      }
      // console.log('执行', '\t', option.xAxis, '\t', option.yAxis)
      var postDom = document.getElementById('J_postLineChart')
      var chart = echarts.init(postDom/* , null, {renderer: 'svg'} */)
      chart.clear() // 清除之后再赋值【展示刷新之后重新渲染效果】
      chart.setOption(option) // 设置数据
      // this.postLineChart = echarts.init(postDom)
      // this.postLineChart.setOption(option) // 设置数据
      // 调整大小
      // window.addEventListener('resize', () => {
      //   this.postLineChart.resize()
      // })
    }
  }
}
</script>

<style lang="scss">
.mod-demo-echarts {
  > .el-alert {
    margin-bottom: 10px;
  }

  > .el-row {
    margin-top: -10px;
    margin-bottom: -10px;

    .el-col {
      padding-top: 10px;
      padding-bottom: 10px;
    }
  }

  .Statistics-chart-box {
    margin-left: -10px;
    min-height: 400px;
  }
}
</style>
