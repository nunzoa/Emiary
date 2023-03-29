

$(document).ready(function(){
  const lineChart = document.getElementById("lineChart");
  const barChart = document.getElementById("barChart");
  const RadarChart = document.getElementById("RadarChart");

  const YEAR = [
    "1월",
    "2월",
    "3월",
    "4월",
    "5월",
    "6월",
    "7월",
    "8월",
    "9월",
    "10월",
    "11월",
    "12월",
  ]
  let DAY = [];
      for(let i = 0; i < new Date(new Date().getFullYear(), new Date().getMonth() + 1, 0).getDate(); ++i){
        DAY[i] = `${i+1}일`;
      }
  let total = 0;

  $.ajax({
    url : "bar",
    dataType : "json",
    success : function(n){
      // console.log("년월 : ", n[0].yearAndMonth, ", 카운트 : ", n[0].countPerMonth);

      let months = [];
      for(let items of n){
        let monthOfTheYear = items.yearAndMonth.substring(5);
        let countDiariesOfMonth = items.countPerMonth;
        total += Number(countDiariesOfMonth);

        for(let i = 0; i < 12; ++i){
          if(monthOfTheYear == i + 1){
            months[i] = countDiariesOfMonth
          }
        }
      }

      new Chart(barChart, {
        type: "bar",
        data: {
          labels : YEAR,
          datasets: [
            {
              label: new Date().getFullYear(),
              data: months,
              borderWidth: 1,
              backgroundColor: "rgb(19, 188, 126)",
            },
          ],
        },
        options: {
          plugins: {
            legend: {
              labels: {
                // This more specific font property overrides the global property
                font: {
                  size: 20,
                },
                // color: "white",
              }
            }
          },
          // 비율을 유지하라는 속성을 빼버리면 전체 width와 height를 갖게 된다.
          maintainAspectRatio: false,
          scales: {
            x: {
              ticks: {
                // color: 'white',
                beginAtZero: true },
              grid: {
                display: false,
              },
            },
            y: {
              ticks: {
                // color: 'white',
                beginAtZero: true },
              grid: {
                display: false,
              },
            },
          },
        },
      });

      switch (true){
        case total < 30 :
          $("#CountingDiary").text("감정일기 걸음마");
          $("#messageOfDiary").text("감정일기를 쓰는 것은 마치 자신과 대화를 나누는 것과 같은 효과를 줍니다. 이를 통해 스트레스를 해소하고, 감정적인 안정을 유지하는데 도움이 됩니다.");
          $("#plantBar").html('<i class="fa-solid fa-leaf"></i>');
          break;
        case total < 60 :
          $("#CountingDiary").text("감정일기 어린이");
          $("#messageOfDiary").text("감정다이어리를 씀에 따라 감정이 조금씩 나아지고 계신가요? 감정일기를 쓰면 감정을 조절하는데 도움이 됩니다:)");
          $("#plantBar").html('<i class="fa-sharp fa-solid fa-seedling"></i>');
          break;
        case total >= 60 :
          $("#CountingDiary").text("감정일기 중독자");
          $("#messageOfDiary").text("당신의 일기는 참으로 뛰어나요. 감정과 경험을 섬세하게 기록하고, 인생의 깊은 곳에서 얻은 진실을 다루고 있습니다.");
          $("#plantBar").html('<i class="fa-sharp fa-solid fa-seedling"></i>');
          break;
      }
      /*스위치 문 끝*/
    },
    error : function(data){
      console.log("에러");
    }
  })
/*처음 페이지 로드 시 불러오는 문*/

//   line ajax 처리
  $.ajax({
    url : "line",
    data : {presentMonth : new Date().getMonth() + 1 },
    dataType: "json",
    success : function(n){
      console.log("왜 다 안나오는거야 : ", n);
      let emotionscoreArr = [];
      for(let items of n){
        let dayOfMonth = items.yearAndMonthAndDay.substring(8);
        let emotionScore = items.emotionScore;

        for(let i = 0; i < 31; ++i){
          if(dayOfMonth == i + 1){
            emotionscoreArr[i] = emotionScore;
          }
        }
      }

      let yLabels = {"-2" : '-', "0" : "0","2" : "+"};
      console.log("emotionscoreArr : ", emotionscoreArr)
      new Chart(lineChart, {
        type: "line",
        data: {
          labels : DAY,
          datasets: [
            {
              label: `${new Date().getMonth() + 1}월`,
              data: emotionscoreArr,
              borderColor: "rgb(19, 188, 126)",
              borderWidth: 1,
              pointRadius: 4,
              pointHoverRadius: 8,
              pointBorderColor: "#13bc7e",
              pointBackgroundColor: "#13bc7e",
              spanGaps: true,
              cubicInterpolationMode: 'monotone',
            },
          ],
        },
        options: {
          plugins: {
            legend: {
              labels: {
                font: {
                  size: 20,
                },
                // color: "white",
              }
            }
          },
          // 비율을 유지하라는 속성을 빼버리면 전체 width와 height를 갖게 된다.
          maintainAspectRatio: false,
          tension : 1,
          scales: {
            x: {
              ticks: {
                // color: 'white',
                beginAtZero: true },
              grid: {
                display: false,
              },
            },
            y: {
              ticks:
                  {
                    // color: 'white',
                  beginAtZero: true,
                  font : {
                    size : 20,
                  },
                  callback : function(value, index, values){
                    return yLabels[value];
                    }},
                  grid: {
                  display: false,
                  },
            },
          },
        },
      });

      let d_2 = emotionscoreArr[emotionscoreArr.length -3];
      let d_1 = emotionscoreArr[emotionscoreArr.length -2];
      let d_day = emotionscoreArr[emotionscoreArr.length -1];
      if(d_2 > 0){
        if(d_1 > 0){
          if(d_day > 0){
            $("#emotionLineTitle").text("긍정적인 나날들")
            $("#emotionLineContent").text(" 너무 멋있어! 계속해서 좋은 일들이 일어나길 바랄게.")
            $("#emotionLine").html('<i class="fa-solid fa-sun" style="color : #ffd400;"></i>');
          }else{
            $("#emotionLineTitle").text("기분이 갑자기 우울해졌어.")
            $("#emotionLineContent").text("무슨 일이 있는거야? 조금 지나면 괜찮아질 거야.")
            $("#emotionLine").html('<i class="fa-solid fa-meteor" style="color : #a9cbd7;"></i>');
          }
        }else{
          if(d_day > 0){
            $("#emotionLineTitle").text("기분이 오락가락")
            $("#emotionLineContent").text("조금 쉬어가면서 마음을 진정시키고, 긍정적인 에너지를 유지하는 노력을 해보자")
            $("#emotionLine").html('<i class="fa-solid fa-tornado" style="color : #808080;"></i>');
          }else{
            $("#emotionLineTitle").text("요즘 감정이 하락중")
            $("#emotionLineContent").text("조금만 기다리면 좋은 날이 일어날거야. 내일은 더 나은 날이 되기를")
            $("#emotionLine").html('<i class="fa-solid fa-cloud" style="color : #8b00ff;"></i>');
          }
        }
      }else{
        if(d_1 > 0){
          if(d_day > 0){
            $("#emotionLineTitle").text("요즘 감정 상승 중!")
            $("#emotionLineContent").text("요즘 좋은 일이 가득한거야? 앞으로도 좋은 일들이 일어나길!")
            $("#emotionLine").html('<i class="fa-solid fa-cloud-sun" style="color : #00ff80;"></i>');
          }else{
            $("#emotionLineTitle").text("기분이 오락가락")
            $("#emotionLineContent").text("감정이 진정되지 않은 상태야, 긍정적인 에너지를 유지하는 노력을 해보자")
            $("#emotionLine").html('<i class="fa-solid fa-tornado" style="color : #808080;"></i>');
          }
        }else{
          if(d_day > 0){
            $("#emotionLineTitle").text("오랜만에 좋은 기분!")
            $("#emotionLineContent").text("다행이야 요새 계속 우울했던데, 앞으로 좋은 일만 있기를!")
            $("#emotionLine").html('<i class="fa-solid fa-rainbow" style="color : #096da9;"></i>');
          }else{
            $("#emotionLineTitle").text("완전 어두워")
            $("#emotionLineContent").text("주변 사람들과 자신의 감정을 이야기하면, 조금씩 나아질 거야. 함께 이겨내보자.")
            $("#emotionLine").html('<i class="fa-sharp fa-solid fa-cloud-bolt" style="color : #000;"></i>');
          }
        }
      }

    },
    error : function(n){
      console.log(n);
    }
  })
//   line ajax끝

//   radar ajax 처리
  $.ajax({
    url : "radar",
    data : {presentMonth : new Date().getMonth() + 1 },
    dataType: "json",
    success : function(n){


      let nameOfDay = [];
      let resultOfSum = [];

      for(let items of n){

        switch(items.nameOfDay){
          case "disaster" :
            nameOfDay[0] = "최악의 날";
            resultOfSum[0] = items.resultOfSum;
            break;
          case "badDay" :
            nameOfDay[1] = "쓸쓸한 날";
            resultOfSum[1] = items.resultOfSum;
            break;
          case "okayDay" :
            nameOfDay[2] = "잔잔한 날";
            resultOfSum[2] = items.resultOfSum;
            break;
          case "goodDay" :
            nameOfDay[3] = "행복한 날";
            resultOfSum[3] = items.resultOfSum;
            break;
          case "bestDay" :
            nameOfDay[4] = "최고의 날";
            resultOfSum[4] = items.resultOfSum;
            break;
        }
      }

      let maxEmotion = Math.max(...resultOfSum);
      let maxDay = nameOfDay[resultOfSum.indexOf(maxEmotion)];

      console.log("가장 많은 숫자 : ", maxEmotion);
      console.log("maxDay : ", maxDay);

      new Chart(RadarChart, {
        type: "doughnut",
        data: {
          labels: nameOfDay,
          datasets: [
            {
              label: new Date().getFullYear(),
              data: resultOfSum,
              borderWidth: 1,
              backgroundColor: "rgba(19, 188, 126, 0.8)",
              backgroundColor: [
                '#EC00FFFF',
                '#ff2d2d',
                '#919191',
                '#4476ff',
                '#00FF7F',
              ],
              hoverOffset: 4
            },
          ],
        },
        options: {
          plugins: {
            legend: {
              labels: {
                font: {
                  size: 10,
                },
                // color: "white",
              }
            }
          },
          maintainAspectRatio: true,
          scales : {
          }
        },
      });


      switch (maxDay){
        case "최악의 날" :
          $("#radarTitle").text("이번 달은 힘든 날이 많았네요");
          $("#radarContent").text("앞으로는 올라갈 일만 남았으니, 행복한 나날이 오길 빌겠습니다.");
          $("#radarEmoticon").html('<i class="fa-solid fa-face-dizzy" style="color : #EC00FFFF;"></i>')
          break;
        case "쓸쓸한 날" :
          $("#radarTitle").text("조금만 더 힘내보아요");
          $("#radarContent").text("답답한 날이 많아서 힘드셨죠. 이 또한 지나갈 것입니다.");
          $("#radarEmoticon").html('<i class="fa-solid fa-face-frown-open" style="color : #ff2d2d;"></i>')
          break;
        case "잔잔한 날" :
          $("#radarTitle").text("당신은 수도승?");
          $("#radarContent").text("감정이 요동치지 않고 한결같은 당신은 감정 컨트롤의 신입니다.");
          $("#radarEmoticon").html('<i class="fa-solid fa-face-meh" style="color : #919191;"></i>')
          break;
        case "행복한 날" :
          $("#radarTitle").text("요새 행복하신가봐요!");
          $("#radarContent").text("축하드려요! 요새 좋은 일이 가득한가봐요! 앞으로도 좋은 일만 일어나길!");
          $("#radarEmoticon").html('<i class="fa-solid fa-face-laugh" style="color : #4476ff;"></i>')
          break;
        case "최고의 날" :
          $("#radarTitle").text("대박! 당신은 행운아!");
          $("#radarContent").text("최고의 날이 이렇게나 많다니~ 당신은 대체 어떤 인생을 사는 겁니까! 축하드려요~");
          $("#radarEmoticon").html('<i class="fa-solid fa-face-laugh-squint" style="color : #00FF7F;"></i>')
          break;
      }

    },
    error : function(n){

    }
  })
})




