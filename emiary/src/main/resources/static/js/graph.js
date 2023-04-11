let presentYear = new Date().getFullYear();
let presentMonth = new Date().getMonth() + 1;
const barChart = document.getElementById("barChart");
const lineChart = document.getElementById("lineChart");
const DoughnutChart = document.getElementById("DoughnutChart");
let barDraw;
let lineDraw;
let doughnutDraw =[1, 1, 1, 1, 1];
let emotionscoreArr = [];
let n;
$(document).ready(function(){
  console.log("presentYear", presentYear)
  console.log("presentMonth", presentMonth)
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
  console.log("불러오기 - 바")

  $.ajax({
    url : "bar",
    data : {presentYear : presentYear},
    dataType : "json",
    success : barFunction,
    error : function(data){
      console.log("에러");
    }
  })
/*처음 페이지 로드 시 불러오는 문*/

//   line ajax 처리
  $.ajax({
    url : "line",
    data : {presentMonth : presentMonth, presentYear : presentYear},
    dataType: "json",
    success : lineFunction,
    error : function(n){
    }
  })
//   line ajax끝

  // 바그래프 왼쪽 버튼
  $("#lastYear").on("click", function(){
    barDraw.destroy();
    presentYear -= 1;
    total = 0;
    $.ajax({
      url : "bar",
      data : {presentYear : presentYear},
      dataType : "json",
      success : barFunction,
      error : function(data){
        console.log("에러");
      }
    })
  })

  // 바그래프 오른쪽 버튼
  $("#nextYear").on("click", function(){
    barDraw.destroy();
    presentYear += 1;
    total = 0;
    $.ajax({
      url : "bar",
      data : {presentYear : presentYear},
      dataType : "json",
      success : barFunction,
      error : function(data){
        console.log("에러");
      }
    })
  })

  // 선그래프 왼쪽 버튼
  $('#lastMonth').on('click', function(){
    lineDraw.destroy();
    presentMonth  -= 1;
    if(presentMonth <= 0){
      presentYear -= 1;
      presentMonth = 12;
    }

    $.ajax({
      url : "line",
      data : {presentMonth : presentMonth, presentYear : presentYear},
      dataType : "json",
      success : lineFunction,
      error : function(n){
        console.log("error")
      }
    })
  })

  // 선그래프 오른쪽 버튼
  $('#nextMonth').on('click', function(){
    lineDraw.destroy();
    presentMonth  += 1;
    if(presentMonth > 12){
      presentYear += 1;
      presentMonth = 1;
    }
    $.ajax({
      url : "line",
      data : {presentMonth : presentMonth, presentYear : presentYear},
      dataType : "json",
      success : lineFunction,
      error : function(n){
        console.log("error")
      }
    })
  })

  // 일별 버튼을 누르면 현재 달의 일별 그래프를 출력
  $("#daily").on("click", function(){
    lineDraw.destroy();
    $("#dailyBox").css("display", "block");
    $("#monthBox").css("display", "none");
    presentMonth = new Date().getMonth() + 1;
    presentYear = new Date().getFullYear();

    $.ajax({
      url : "line",
      data : {presentMonth : presentMonth, presentYear : presentYear},
      dataType : "json",
      success : lineFunction,
      error : function(n){
        console.log("error")
      }
    })
  })


  // 선그래프 월별 버튼
  $('#monthly').on('click', function(){
    lineDraw.destroy();
    $("#dailyBox").css("display", "none");
    $("#monthBox").css("display", "block");
    // 누르면 원래 버튼 사라지고 새로운 버튼 나오게 코드
    $.ajax({
      url : "monthlyLine",
      data : {presentYear : presentYear},
      dataType : "json",
      success : lineFunctionMonth,
      error : function(n){
        console.log("error")
      }
    })
  })


  // 라인 그래프 작년로 이동
  $('#lastYearLine').on('click', function(){
    lineDraw.destroy();
    presentYear -= 1;
    $.ajax({
      url : "monthlyLine",
      data : {presentYear : presentYear},
      dataType : "json",
      success : lineFunctionMonth,
      error : function(n){
        console.log("error")
      }
    })
  })

  // 라인 그래프 다음달로 이동
  $('#nextYearLine').on('click', function(){
    lineDraw.destroy();
    presentYear += 1;
    $.ajax({
      url : "monthlyLine",
      data : {presentYear : presentYear},
      dataType : "json",
      success : lineFunctionMonth,
      error : function(n){
        console.log("error")
      }
    })
  })

  //   radar ajax 처리
  $.ajax({
    url : "doughnut",
    data : {givenMonth : 1},
    dataType: "json",
    success : radarFunction,
    error : function(n){

    }
  })


//   doughnut 1개월
$("#doughnut1").on("click", function(){
  doughnutDraw.destroy();
  $.ajax({
    url : "doughnut",
    data : {givenMonth : 1},
    dataType: "json",
    success : radarFunction,
    error : function(n){
    }
  })
})

  //   doughnut 3개월
  $("#doughnut3").on("click", function(){
    doughnutDraw.destroy();
    $.ajax({
      url : "doughnut",
      data : {givenMonth : 3},
      dataType: "json",
      success : radarFunction,
      error : function(n){
      }
    })
  })


  //   doughnut 6개월
  $("#doughnut6").on("click", function(){
    doughnutDraw.destroy();
    $.ajax({
      url : "doughnut",
      data : {givenMonth : 6},
      dataType: "json",
      success : radarFunction,
      error : function(n){
      }
    })
  })


  //   doughnut 12개월
  $("#doughnut12").on("click", function(){
    doughnutDraw.destroy();
    $.ajax({
      url : "doughnut",
      data : {givenMonth : 12},
      dataType: "json",
      success : radarFunction,
      error : function(n){
      }
    })
  })


  // 년별로 일기 수 체크
  function barFunction(n){

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
    barDraw = new Chart(barChart, {
      type: "bar",
      data: {
        labels : YEAR,
        datasets: [
          {
            label: presentYear,
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
        $("#plantBar").html('<i class="fa-solid fa-tree"></i>');
        break;
    }
  }

  // 일별로 감정변화 확인
  function lineFunction(n){
    emotionscoreArr = [];
    if(n.length != 0) {
      for(let items of n){
        let dayOfMonth = items.yearAndMonthAndDay.substring(8);
        let emotionScore = items.emotionScore;

        for(let i = 0; i < 31; ++i){
          if(dayOfMonth == i + 1){
            emotionscoreArr[i] = emotionScore;
          }
        }
      }
    }


    let yLabels = {"-2" : '-', "0" : "0","2" : "+"};
    lineDraw = new Chart(lineChart, {
      type: "line",
      data: {
        labels : DAY,
        datasets: [
          {
            label: `${presentYear}년 ${presentMonth}월`,
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
        responsive: true,
        plugins: {
          legend: {
            labels: {
              font: {
                size: 20,
              },
            }
          }
        },
        maintainAspectRatio: false,
        tension : 1,
        scales: {
          x: {
            ticks: {
              beginAtZero: true },
            grid: {
              display: false,
            },
          },
          y: {
            ticks:
                {
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

    lineContent(n);
  }

  // 선그래프 월별 함수
  function lineFunctionMonth(n){
    emotionscoreArr = [];
    if(n.length != 0){
      for(let items of n){
        let monthOfYear = items.yearAndMonth.substring(5);
        let emotionScore = items.emotionScore;

        for(let i = 0; i < 12; ++i){
          if(monthOfYear == i + 1){
            emotionscoreArr[i] = emotionScore;
          }
        }
      }
    }


    let yLabels = {"-2" : '-', "0" : "0","2" : "+"};
    lineDraw = new Chart(lineChart, {
      type: "line",
      data: {
        labels : YEAR,
        datasets: [
          {
            label: `${presentYear}년`,
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
        responsive: true,
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

    lineContentMonth(n);
    }

    function lineContent(n){
      if(n.length < 3) return;

      // 값이 1개만 있으면 안되어서
      let countNull = 0;
      for(let i = 0; i < emotionscoreArr.length; ++i){
        if(emotionscoreArr[i] == null){
          countNull += 1;
        }
      }

      let d_day = 0;
      let d_1 = null;
      let d_2 = null;

      d_day = emotionscoreArr[emotionscoreArr.length -1];
      let dcount = 2;
      while(d_1 == null){
        d_1 = emotionscoreArr[emotionscoreArr.length - dcount];
        dcount++;
      }
      while(d_2 == null){
        d_2 = emotionscoreArr[emotionscoreArr.length - dcount];
        dcount++;
      }

      console.log("d_day, d_1, d_2", d_day, d_1, d_2)

      if(d_2 > 0){
        if(d_1 > 0){
          if(d_day > 0){
            $("#emotionLineTitle").text("행복한 나날")
            $("#emotionLineContent").text(" 너무 멋있어! 계속해서 좋은 일들이 일어나길 바랄게.")
            $("#emotionLine").html('<i class="fa-solid fa-sun" style="color : #ffd400;"></i>');
          }else{
            $("#emotionLineTitle").text("갑자기 우울")
            $("#emotionLineContent").text("무슨 일이 있는거야? 조금 지나면 괜찮아질 거야.")
            $("#emotionLine").html('<i class="fa-solid fa-meteor" style="color : #a9cbd7;"></i>');
          }
        }else{
          if(d_day > 0){
            $("#emotionLineTitle").text("감정이 요동쳐")
            $("#emotionLineContent").text("조금 쉬어가면서 마음을 진정시키고, 긍정적인 에너지를 유지하는 노력을 해보자")
            $("#emotionLine").html('<i class="fa-solid fa-tornado" style="color : #808080;"></i>');
          }else{
            $("#emotionLineTitle").text("감정 하락중")
            $("#emotionLineContent").text("조금만 기다리면 좋은 날이 일어날거야. 내일은 더 나은 날이 되기를")
            $("#emotionLine").html('<i class="fa-solid fa-cloud" style="color : #8b00ff;"></i>');
          }
        }
      }else{
        if(d_1 > 0){
          if(d_day > 0){
            $("#emotionLineTitle").text("좋아지고 있어~")
            $("#emotionLineContent").text("요즘 좋은 일이 가득한거야? 앞으로도 좋은 일들이 일어나길!")
            $("#emotionLine").html('<i class="fa-solid fa-cloud-sun" style="color : #00ff80;"></i>');
          }else{
            $("#emotionLineTitle").text("감정 롤러코스터")
            $("#emotionLineContent").text("감정이 진정되지 않은 상태야, 긍정적인 에너지를 유지하는 노력을 해보자")
            $("#emotionLine").html('<i class="fa-solid fa-tornado" style="color : #808080;"></i>');
          }
        }else{
          if(d_day > 0){
            $("#emotionLineTitle").text("오랜만에 좋아!")
            $("#emotionLineContent").text("다행이야 요새 계속 우울했던데, 앞으로 좋은 일만 있기를!")
            $("#emotionLine").html('<i class="fa-solid fa-rainbow" style="color : #096da9;"></i>');
          }else{
            $("#emotionLineTitle").text("완전 어두워")
            $("#emotionLineContent").text("주변 사람들과 자신의 감정을 이야기하면, 조금씩 나아질 거야. 함께 이겨내보자.")
            $("#emotionLine").html('<i class="fa-sharp fa-solid fa-cloud-bolt" style="color : #000;"></i>');
          }
        }
      }
    }

  function lineContentMonth(n){
    if(n.length == 0) return;

    // 값이 1개만 있으면 안되어서
    let countNull = 0;
    for(let i = 0; i < emotionscoreArr.length; ++i){
      if(emotionscoreArr[i] == null){
        countNull += 1;
      }
    }
    if(countNull > 10) return;

    let d_day = 0;
    let d_1 = null;
    let d_2 = null;

    d_day = emotionscoreArr[emotionscoreArr.length -1];
    let dcount = 2;
    while(d_1 == null){
      d_1 = emotionscoreArr[emotionscoreArr.length - dcount];
      dcount++;
    }
    while(d_2 == null){
      d_2 = emotionscoreArr[emotionscoreArr.length - dcount];
      dcount++;
    }

    console.log("d_day, d_1, d_2", d_day, d_1, d_2)

    if(d_2 > 0){
      if(d_1 > 0){
        if(d_day > 0){
          $("#emotionLineTitle").text("행복한 나날")
          $("#emotionLineContent").text(" 너무 멋있어! 계속해서 좋은 일들이 일어나길 바랄게.")
          $("#emotionLine").html('<i class="fa-solid fa-sun" style="color : #ffd400;"></i>');
        }else{
          $("#emotionLineTitle").text("갑자기 우울")
          $("#emotionLineContent").text("무슨 일이 있는거야? 조금 지나면 괜찮아질 거야.")
          $("#emotionLine").html('<i class="fa-solid fa-meteor" style="color : #a9cbd7;"></i>');
        }
      }else{
        if(d_day > 0){
          $("#emotionLineTitle").text("감정이 요동쳐")
          $("#emotionLineContent").text("조금 쉬어가면서 마음을 진정시키고, 긍정적인 에너지를 유지하는 노력을 해보자")
          $("#emotionLine").html('<i class="fa-solid fa-tornado" style="color : #808080;"></i>');
        }else{
          $("#emotionLineTitle").text("감정 하락중")
          $("#emotionLineContent").text("조금만 기다리면 좋은 날이 일어날거야. 내일은 더 나은 날이 되기를")
          $("#emotionLine").html('<i class="fa-solid fa-cloud" style="color : #8b00ff;"></i>');
        }
      }
    }else{
      if(d_1 > 0){
        if(d_day > 0){
          $("#emotionLineTitle").text("좋아지고 있어~")
          $("#emotionLineContent").text("요즘 좋은 일이 가득한거야? 앞으로도 좋은 일들이 일어나길!")
          $("#emotionLine").html('<i class="fa-solid fa-cloud-sun" style="color : #00ff80;"></i>');
        }else{
          $("#emotionLineTitle").text("감정 롤러코스터")
          $("#emotionLineContent").text("감정이 진정되지 않은 상태야, 긍정적인 에너지를 유지하는 노력을 해보자")
          $("#emotionLine").html('<i class="fa-solid fa-tornado" style="color : #808080;"></i>');
        }
      }else{
        if(d_day > 0){
          $("#emotionLineTitle").text("오랜만에 좋아!")
          $("#emotionLineContent").text("다행이야 요새 계속 우울했던데, 앞으로 좋은 일만 있기를!")
          $("#emotionLine").html('<i class="fa-solid fa-rainbow" style="color : #096da9;"></i>');
        }else{
          $("#emotionLineTitle").text("완전 어두워")
          $("#emotionLineContent").text("주변 사람들과 자신의 감정을 이야기하면, 조금씩 나아질 거야. 함께 이겨내보자.")
          $("#emotionLine").html('<i class="fa-sharp fa-solid fa-cloud-bolt" style="color : #000;"></i>');
        }
      }
    }
  }

    function radarFunction(n){
      console.log("doughnut - n : ", n);

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

        doughnutDraw = new Chart(DoughnutChart, {
          type: "doughnut",
          data: {
            labels: nameOfDay,
            datasets: [
              {
                label: presentYear,
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
              labels: {
                render : (args) => {
                  return args.label
                },
                legend: {
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
            $("#radarTitle").text("같이 화이팅!");
            $("#radarContent").text("앞으로는 올라갈 일만 남았으니, 행복한 나날이 오길 빌겠습니다.");
            $("#radarEmoticon").html('<i class="fa-solid fa-face-dizzy" style="color : #EC00FFFF;"></i>')
            break;
          case "쓸쓸한 날" :
            $("#radarTitle").text("힘내요~");
            $("#radarContent").text("답답한 날이 많아서 힘드셨죠. 이 또한 지나갈 것입니다.");
            $("#radarEmoticon").html('<i class="fa-solid fa-face-frown-open" style="color : #ff2d2d;"></i>')
            break;
          case "잔잔한 날" :
            $("#radarTitle").text("당신은 수도승?");
            $("#radarContent").text("감정이 요동치지 않고 한결같은 당신은 감정 컨트롤의 신입니다.");
            $("#radarEmoticon").html('<i class="fa-solid fa-face-meh" style="color : #919191;"></i>')
            break;
          case "행복한 날" :
            $("#radarTitle").text("요새 행복~");
            $("#radarContent").text("축하드려요! 요새 좋은 일이 가득한가봐요! 앞으로도 좋은 일만 일어나길!");
            $("#radarEmoticon").html('<i class="fa-solid fa-face-laugh" style="color : #4476ff;"></i>')
            break;
          case "최고의 날" :
            $("#radarTitle").text("완전 최고!");
            $("#radarContent").text("최고의 날이 이렇게나 많다니~ 당신은 대체 어떤 인생을 사는 겁니까! 축하드려요~");
            $("#radarEmoticon").html('<i class="fa-solid fa-face-laugh-squint" style="color : #00FF7F;"></i>')
            break;
        }
    }

})


