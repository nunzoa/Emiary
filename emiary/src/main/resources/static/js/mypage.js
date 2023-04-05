$(document).ready(function () {


  $.ajax({
    url : "getImgURL",
    success : function(n){
      $("#profileImage").css({"backgroundImage": `url(${n})`})
    }
  })


  $.ajax({
    url : "checkProfile",
    success : function(n){
      console.log("켜질 때 프로필 허용상태인지 아닌지 체크", n)
      if(n == 'N'){
        $("#check").attr("allow", "N");
        $("#profile_access").css("background", "#adb5db").html("프로필 비공개");
        $("#check").prop("checked", true);
      }else if(n == 'Y'){
        $("#check").attr("allow", "Y");
        $("#profile_access").css("background", "transparent").html("프로필 공개");
        $("#check").prop("checked", false);
      }
    },
    error : function(){
      console.log("error");
    }
  })


  $("#check").change(function () {

    let isAllowed = $("#check").attr("allow");
    console.log("변화가 생기면 attr머 갖고 오는지 체크 ", isAllowed);

    $.ajax({
      url: "allowpf",
      data: {isAllowed: isAllowed},
      success: function (n) {
        // 여기서부터 해야함
        if(n == 'N'){
          $("#check").attr("allow", "N");
          $("#profile_access").css("background", "#adb5db").html("프로필 비공개");
          swal({
            title: "프로필 거부 완료!",
            icon: "info",
            button: "나가기!",
          });
        }else if(n == 'Y'){
          $("#check").attr("allow", "Y");
          $("#profile_access").css("background", "transparent").html("프로필 공개");
          swal({
            title: "프로필 허용 완료!",
            icon: "success",
            button: "나가기!",
          });
        }
      }
    });
  });


  $("#signup-btn").on("click", function (){
    if(confirm("정말로 수정하시겠습니까?")){
      $.ajax({
        url : "modify",
        method : "post",
        data : {
          email : $("#email").val(),
          memberpw : $("#memberpw").val(),
          nickname : $("#nickname").val(),
          phone : $("#phone").val()
        },
        success : function(n){
          if(n){
            swal({
              title: '수정 완료!',
              icon: "success",
              button: "마이페이지로",
            }).then((result) =>
                location.href = 'home'
            );
          }
        }
      })
    }
  })

  $("#quitYes").on("click", function(){
    if(confirm("정말로 탈퇴하시겠습니까?")){
      $.ajax({
        url : "deleteMember",
        success : function(){
          location.href = "/emiary/member/loginForm"
        }
      })
    }
  })

  $("#quitNo").on("click", function(){
    location.href="home"
  })

});

// 타임리프값은 value값이 아닌, 내부 텍스트 노드로 전달된다 따라서 value속성을 사용해 값을 읽을 수 없다
let averageEmo = document.querySelector("#averageEmotion").getAttribute("emotionscore");
let emoticonBox = document.querySelector("#averageEmotion");
let emoticon = document.createElement("i");
switch (true){
    //     매우 부정
  case averageEmo > -2 && averageEmo <= -1.2  :
    emoticon.classList.add("emoticon");
    emoticon.classList.add("fa-solid");
    emoticon.classList.add("fa-face-dizzy");
    emoticon.style.color = "#EC00FFFF";
    emoticonBox.appendChild(emoticon);

    break;
    //     약간 부정
  case averageEmo > -1.2 && averageEmo <= -0.5 :
    emoticon.classList.add("emoticon");
    emoticon.classList.add("fa-solid");
    emoticon.classList.add("fa-face-frown-open");
    emoticon.style.color = "#ff2d2d";
    emoticonBox.appendChild(emoticon);
    break;
    //     중립
  case averageEmo > -0.5 && averageEmo <= 0.1 :
    emoticon.classList.add("emoticon");
    emoticon.classList.add("fa-solid");
    emoticon.classList.add("fa-face-meh");
    emoticon.style.color = "#919191";
    emoticonBox.appendChild(emoticon);

    break;
    //     약간 긍정
  case averageEmo > 0.1 && averageEmo <= 1.0 :
    emoticon.classList.add("emoticon");
    emoticon.classList.add("fa-solid");
    emoticon.classList.add("fa-face-laugh");
    emoticon.style.color = "#4476ff";
    emoticonBox.appendChild(emoticon);
    break;
    //     매우 긍정
  case averageEmo > 1.0 && averageEmo <= 2.0 :
    emoticon.classList.add("emoticon");
    emoticon.classList.add("fa-solid");
    emoticon.classList.add("fa-face-laugh-squint");
    emoticon.style.color = "#4476ff";
    emoticonBox.appendChild(emoticon);
    break;
}


$("#inputImage").on("change", function(){
  $("#profileImageForm").submit();
  console.log("뜨냐?")
  $("#profileImageForm").on("submit", function(){
    console.log("여기 들어오냐")
  })
})
