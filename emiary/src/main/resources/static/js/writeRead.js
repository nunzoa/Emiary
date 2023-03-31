

const editor = new EditorJS({
  holder: "editorjs",
  tools: {
    header: Header,
    quote: Quote,

    checklist: {
      class: Checklist,
      inlineToolbar: true,
    },
    raw: RawTool,
  },
});



$("#diarybtn").click(function() {
  let content = $("#editorjs").html();
  let daystring = $("#dayString").text();
    // 버튼 클릭시 스크롤 최상단으로 이동
    window.scrollTo(0, 0);

  $(".box").css("display", "block");
  $(".box").css("display", "block");
  $(".modalBackDrop").css("display", "block");
  $.ajax({
    type: "POST",
    url: "write",
    data: { content : content, created_at : daystring },
    dataType : 'text',
    success: function(n) {
      let emotionTitle = document.createElement("i");
      let emotionText;

      switch (true){
          //     매우 부정
        case n > -2 && n <= -1.2  :
          emotionTitle.classList.add("fa-solid");
          emotionTitle.classList.add("fa-face-dizzy");
          emotionTitle.classList.add("fa-bounce");
          emotionTitle.style.color = "#EC00FFFF";
          emotionText = "지금은 힘든 상황이지만, 시간이 지나면 이것도 지나갈 것입니다.";
          break;
          //     약간 부정
        case n > -1.2 && n <= -0.5 :
          emotionTitle.classList.add("fa-solid");
          emotionTitle.classList.add("fa-face-frown-open");
          emotionTitle.classList.add("fa-bounce");
          emotionTitle.style.color = "#ff2d2d"
          emotionText = "지금은 조금 힘들겠지만, 내일은 더 나은 날이 될 것입니다.";
          break;
          //     중립
        case n > -0.5 && n <= 0.1 :
          emotionTitle.classList.add("fa-solid");
          emotionTitle.classList.add("fa-face-meh");
          emotionTitle.classList.add("fa-bounce");
          emotionTitle.style.color = "#919191";
          emotionText = "상황이 아직 명확하지 않지만, 최선을 다하면 좋은 결과를 얻을 수 있을 것입니다.";
          break;
          //     약간 긍정
        case n > 0.1 && n <= 1.0 :
          emotionTitle.classList.add("fa-solid");
          emotionTitle.classList.add("fa-face-laugh");
          emotionTitle.classList.add("fa-bounce");
          emotionTitle.style.color = "#4476ff";
          emotionText = "지금까지의 노력이 결실을 맺을 것입니다.";
          break;
          //     매우 긍정
        case n > 1.0 && n <= 2.0 :
          emotionTitle.classList.add("fa-solid");
          emotionTitle.classList.add("fa-face-laugh-squint")
          emotionTitle.style.color = "#00FF7F";
          emotionText = "축하합니다! 노력하고 기다렸던 보상을 받으셨군요!";
          break;
          //     10같은 경우 판변 못함으로 기본값처리
        default :
          emotionText = "충분한 양을 쓰지 못하셔서 감정 분석에 실패하였습니다.";

      }

        swal({
          content: emotionTitle,
          text: emotionText,
          icon: "success",
          button: "나가기!",
        }).then((result) =>
            location.href ='/emiary'
        );


    },
    error: function(e) {
      alert(JSON.stringify(e));
      console.log("Error occurred while saving data!");
      // Handle error response here
    }
  });
});



$("#lastDiary").click(function() {
  let daystring = $("#dayString").text();
  $.ajax({
      url : "lastDiary",
      data : {dayString : daystring},
      success : function(dayString){
        location.href = `write?dayString=${dayString}`;
      },
      error : function(){
        alert("error");
      }
  })
});

$("#nextDiary").click(function() {
  let daystring = $("#dayString").text();
  $.ajax({
    url : "nextDiary",
    data : {dayString : daystring},
    success : function(dayString){
      location.href = `write?dayString=${dayString}`;
    },
    error : function(){
      alert("error");
    }
  })
});

$("#lastReadDiary").click(function() {
  let daystring = $("#dayString").text();
  $.ajax({
    url : "lastReadDiary",
    data : {dayString : daystring},
    dataType: "json",
    success : function(n){
      location.href = `read?dayString=${n.created_at}`;
    },
    error : function(){
      alert("error");
    }
  })
});

$("#nextReadDiary").click(function() {
  let daystring = $("#dayString").text();
  $.ajax({
    url : "nextReadDiary",
    data : {dayString : daystring},
    dataType: "json",
    success : function(n){
      location.href = `read?dayString=${n.created_at}`;
    },
    error : function(){
      alert("error");
    }
  })
});