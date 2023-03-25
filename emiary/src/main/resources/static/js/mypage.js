$(document).ready(function () {


  $("#check").change(function () {
    if ($("#check").is(":checked")) {
      $("#profile_access").css("background", "#adb5db").html("프로필 비공개");
    } else {
      $("#profile_access").css("background", "transparent").html("프로필 공개");
    }
  });



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

const profileImg = document.querySelector('#profileImage');
const fileInput = document.querySelector('#inputImage');

profileImg.addEventListener("click", function(){
  fileInput.click();
  fileInput.onclick = function(){

  }
})