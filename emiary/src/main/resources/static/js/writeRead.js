

const editor = new EditorJS({
  holder: "editorjs",
  minHeight : 30,
  tools: {
    header: Header,
    quote: Quote,
    checklist: {
      class: Checklist,
      inlineToolbar: true,
    },


  },

});



$("#diarybtn").click(function() {
  let content = $("#editorjs").html();
  let daystring = $("#dayString").text();
  let content_notag = $("#editorjs").text();
    // 버튼 클릭시 스크롤 최상단으로 이동
    window.scrollTo(0, 0);

  $(".box").css("display", "block");
  $(".box").css("display", "block");
  $(".modalBackDrop").css("display", "block");
  $.ajax({
    type: "POST",
    url: "write",
    data: { content : content, created_at : daystring, content_notag : content_notag},
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
          emotionText = "요즘은 단조로운 날이군요 이것도 좋아요!";
          break;
          //     약간 긍정
        case n > 0.1 && n <= 1.0 :
          emotionTitle.classList.add("fa-solid");
          emotionTitle.classList.add("fa-face-laugh");
          emotionTitle.classList.add("fa-bounce");
          emotionTitle.style.color = "#4476ff";
          emotionText = "요새 기분이 좋으신가봐요~";
          break;
          //     매우 긍정
        case n > 1.0 && n <= 2.0 :
          emotionTitle.classList.add("fa-solid");
          emotionTitle.classList.add("fa-face-laugh-squint")
          emotionTitle.classList.add("fa-bounce");
          emotionTitle.style.color = "#00FF7F";
          emotionText = "요새 완전 행복하신가봐요!";
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

// $("#contentEmptyHeart").on("click", function(){
//   $("#contentEmptyHeart").css("display", "none");
//   $("#contentFullHeart").css("display", "block");
// })



$(document).ready(function(){
  $.ajax({
    url : "heartStatus",
    data : { email : $("#getEmail").text(), diaryId : $("#getDiaryId").text() },
    dataType : "json",
    success : function(n){
      console.log(n);
       $(".countHeart").html(n);

    }
  })


  $("#reply-btn").on("click", function(){
    $.ajax({
      url : "insertReply",
      method : "POST",
      data : {comment : $("#commentText").val(), diaryId : $("#getDiaryId").text()},
      success : function(n){
        console.log(n);
        $.ajax({
          url : "getReply",
          data : { diaryId : $("#getDiaryId").text() },
          success : function(n){
            for (let items of n) {


              let replies = "";
              for (let items of n) {
                replies +=
                    `
            <div class="reply row p-3 mt-2 mx-auto d-flex align-items-center">
                <div class="replyContent col-2">${items.nickname}</div>
                <div class="replyContent col-7">${items.replyContent}</div>
                <div class="replyContent col-3">${items.created_at_reply}</div>
            </div>
            `
              }
              console.log(replies)
              $("#commentBox").html(replies)

            }
          }
        })

      }
    })
    $("#commentText").val('')
  })


  $.ajax({
    url : "getReply",
    data : { diaryId : $("#getDiaryId").text() },
    success : function(n){
      for (let items of n) {

        let replies = "";
        for (let items of n) {
          replies +=
              `
            <div class="reply row p-3 mt-2 d-flex align-items-center">
                <div class="replyContent col-2">${items.nickname}</div>
                <div class="replyContent col-7">${items.replyContent}</div>
                <div class="replyContent col-3">${items.created_at_reply}</div>
            </div>
            `
        }
        console.log(replies)
        $("#commentBox").html(replies)

      }
    }
  })
})


let cccc = 0;
setInterval(function() {
  var changeMessage = document.querySelector('.changeMessage'); // 변경할 요소 선택
  var messages = [
    "감정 분석 중...",
    "AI 이미지 생성 중...",
    "인스타 갖고 오는 중...",
    "잠시 기다려주세요..."
  ]; // 변경할 메시지들 배열
  changeMessage.textContent = messages[cccc++]; // 선택한 메시지로 변경
  if(cccc >= messages.length){
    cccc = 0;
  }
}, 4000); // 5초(5000ms)마다 실행