import { Component, OnInit } from '@angular/core';
import * as Chartist from 'chartist';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor() { }
  animacionLineChart(chart){
      let seq: any, delays: any, durations: any;
      seq = 0;
      delays = 80;
      durations = 500;

      chart.on('draw', function(data) {
        if(data.type === 'line' || data.type === 'area') {
          data.element.animate({
            d: {
              begin: 600,
              dur: 700,
              from: data.path.clone().scale(1, 0).translate(0, data.chartRect.height()).stringify(),
              to: data.path.clone().stringify(),
              easing: Chartist.Svg.Easing.easeOutQuint
            }
          });
        } else if(data.type === 'point') {
              seq++;
              data.element.animate({
                opacity: {
                  begin: seq * delays,
                  dur: durations,
                  from: 0,
                  to: 1,
                  easing: 'ease'
                }
              });
          }
      });

      seq = 0;
  };
  animacionBarChart(chart){
      let seq2: any, delays2: any, durations2: any;

      seq2 = 0;
      delays2 = 80;
      durations2 = 500;
      chart.on('draw', function(data) {
        if(data.type === 'bar'){
            seq2++;
            data.element.animate({
              opacity: {
                begin: seq2 * delays2,
                dur: durations2,
                from: 0,
                to: 1,
                easing: 'ease'
              }
            });
        }
      });

      seq2 = 0;
  };
  ngOnInit() {

      const subsMensuales: any = {
          labels: ['sem 1', 'sem 2', 'sem 3', 'sem 4'],
          series: [
              [12, 17, 7,11]
          ]
      };

     const opcionesSubsMensuales: any = {
          lineSmooth: Chartist.Interpolation.cardinal({
              tension: 0
          }),
          low: 0,
          high: 20, 
          chartPadding: { top: 0, right: 0, bottom: 0, left: 0},
          
          
      }

      var graficoSubsMensuales = new Chartist.Line('#dailySalesChart', subsMensuales, opcionesSubsMensuales);

      this.animacionLineChart(graficoSubsMensuales);





      var aforoSemanal = {
        labels: ['lu', 'ma', 'mi', 'ju', 'vi', 'sá', 'do'],
        series: [
            [40, 41, 32, 43, 21, 13, 3]
        ]
      };
      var opcionesAforoSemanal = {
          axisX: {
              showGrid: true
          },
          low: 0,
          high: 50,
          chartPadding: { top: 0, right: 5, bottom: 0, left: 0}
      };
      var responsive: any[] = [
        ['screen and (max-width: 640px)', {
          seriesBarDistance: 5,
          axisX: {
            labelInterpolationFnc: function (value) {
              return value[0];
            }
          }
        }]
      ];
      var websiteViewsChart = new Chartist.Bar('#websiteViewsChart', aforoSemanal, opcionesAforoSemanal, responsive);

      this.animacionBarChart(websiteViewsChart);
  }

}
