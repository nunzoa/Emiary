

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
    raw: RawTool,
  },
});


$("#lastReadDiary").click(function() {
  let daystring = $("#dayString").text();
  console.log("daystring : ", daystring)
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


$(document).ready(function(){
  $.ajax({
    url : "checkHeart",
    data : { email : $("#getEmail").text(), diaryId : $("#getDiaryId").text(), dayString: $("#dayString").text() },
    dataType : "json",
    success : function(n){
      console.log(n);

      if(n.checkHeart == 'N'){
        $("#contentEmptyHeart").css("display", "block");
        $("#contentFullHeart").css("display", "none");
      }else{
        $("#contentEmptyHeart").css("display", "none");
        $("#contentFullHeart").css("display", "block");
      }

      $(".countHeart").html(n.cntHeart);

    }
  })



  $("#reply-btn").on("click", function() {
    $.ajax({
      url: "insertReply",
      method: "POST",
      data: {comment: $("#commentText").val(), diaryId: $("#getDiaryId").text()},
      success: function (n) {
        console.log(n);

        $.ajax({
          url: "getReply",
          data: {diaryId: $("#getDiaryId").text()},
          success: function (n) {
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
        })
      }
    })
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
                <div class="replyContent col-3">${items.nickname}</div>
                <div class="replyContent col-6">${items.replyContent}</div>
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


$("#contentEmptyHeart").on("click", function(){
  $.ajax({
    url : "updateHeart",
    data : {diaryId : $("#getDiaryId").text(), heartTo : "Y"},
    success : function(n){
      $("#contentEmptyHeart").css("display", "none");
      $("#contentFullHeart").css("display", "block");
      $(".countHeart").html(n);
    }
  })
})


$("#contentFullHeart").on("click", function(){
  $.ajax({
    url : "updateHeart",
    data : {diaryId : $("#getDiaryId").text(), heartTo : "N"},
    success : function(n){
      $("#contentEmptyHeart").css("display", "block");
      $("#contentFullHeart").css("display", "none");
      $(".countHeart").html(n);
    }
  })
})


