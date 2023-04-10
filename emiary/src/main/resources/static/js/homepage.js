let nav = 0;
const calendar = document.getElementById('calendar');
const modalBackDrop = document.querySelector(".modalBackDrop");
const writeEventModal = document.getElementById('writeEventModal');
const delReadModal = document.getElementById("delReadModal");
const todayDiary = document.getElementById("todayDiary");
const weekdays = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']


let emotionColor = "";
let replyAlarm = [];
$(document).ready(function() {

    $.ajax({
        url : "diary/replyAlarm",
        data : {yearMonth : $("#monthDisplay").text()},
        dataType: "json",
        success : function(n){

            replyAlarm = n;
            console.log("알림리스트 : ", n);
            load();

        },error : function(n){
            console.log("알람 에러", n)
        }
    })

    console.log("실행되고1")
    $.ajax({
        url: 'diary/checkDiary',
        type: 'GET',
        success: function (diaries) {
            emotionColor = diaries;
            load();
        },
        error: function (xhr, status, error) {
            console.log(status);
        }
    });

    $.ajax({
        url: "friend/friendList",
        dataType: "json",
        success: function(n) {
            console.log("친구리스트 : ", n)
            imageBox(n);
        }
    });

    console.log("여기는 오니");

});


// load 시작
function load() {

    console.log("실행되나??2")
    const dt = new Date();
    
    // 3월 29일 이후 2월달 캘린더가 안떴는데 그게 month만 빼서 그럼
    dt.setDate(1);
    if (nav !== 0) {
        dt.setMonth(new Date().getMonth() + nav);

    }

    const day = dt.getDate();
    const month = dt.getMonth();
    const year = dt.getFullYear();

    const firstDayOfMonth = new Date(year, month, 1);

    let daysInMonth = new Date(year, month + 1, 0).getDate();
    const dateString = firstDayOfMonth.toLocaleDateString('en-us', {
        weekday: 'long',
        year: 'numeric',
        month: 'numeric',
        day: 'numeric'
    })

    const paddingDays = weekdays.indexOf(dateString.split(',')[0]);
    document.getElementById("monthDisplay").innerText = `${year}-${dt.toLocaleDateString('en-us', {month: 'numeric'})}`

    console.log("달", month)
    console.log("daysInMonth", daysInMonth)

    calendar.innerHTML = '';


    let countAlarm = 0;
    // for문 캘린더 작성
    for (let i = 1; i <= paddingDays + daysInMonth; i++) {
        let daySquare = document.createElement('a');
        daySquare.classList.add('day');

        let cleanDayString = `${year}${month + 1}${i - paddingDays}`;
        let barDayString = `${year}-${month + 1}-${i - paddingDays}`;

        daySquare.setAttribute("num", barDayString);

        // modal창 확인 시 사용 및 read시 사용
        let dayString = `${year}년${month + 1}월${i - paddingDays}일`;

        // +버튼 누를 시 일기 작성으로 이동
        let todayDate = new Date(year, month, day);
        let todayString = `${todayDate.getFullYear()}년${(todayDate.getMonth() + 1)}월${todayDate.getDate()}일`;
        todayDiary.href = `/emiary/diary/write?dayString=` + todayString;
        // paddingDays는 제외하고 출력
        if (i > paddingDays) {
            daySquare.innerText = i - paddingDays;
            
            daySquare.addEventListener("click", function(){
                let dateForOne = daySquare.getAttribute("num");

                // 오늘 이후로 일기를 쓸 수 없게 만드는 코드 구현?
                console.log("barDayString : ", barDayString)
                console.log("dayString : ", dayString)
                console.log("dateForOne : ", dateForOne);

                // 모달 구분 코드 시작
                $.ajax({
                    url : "diary/modalCheck",
                    type : "GET",
                    data : {dateForOne : dateForOne},
                    success : function(n){
                        if(n == 1){
                            delReadModal.style.display = "block";
                        }else{
                            writeEventModal.style.display = "block";
                        }
                        modalBackDrop.style.display = "block";
                    },
                    error : function (xhr, status, error) {
                    console.log(status);
                }
                })
                // 모달 구분 코드 끝

                document.getElementById("writeDiary").onclick = function () {
                    location.href = `/emiary/diary/write?dayString=${barDayString}`;
                }

                document.getElementById("readDiary").onclick = function () {
                    location.href = `/emiary/diary/read?dayString=${barDayString}`;
                }

                document.getElementById("deleteDiary").onclick = function () {
                    location.href = `/emiary/diary/deleteDiary?dayString=${barDayString}`;
                }
            })

            document.querySelector('.write-close').addEventListener('click', () => {
                writeEventModal.style.display = "none";
                modalBackDrop.style.display = "none";
            })

            document.querySelector('.delRead-close').addEventListener('click', () => {
                delReadModal.style.display = "none";
                modalBackDrop.style.display = "none";
            })


            // 색깔 및 이모티콘 넣기
            for(let count = 0; count < emotionColor.length; ++count){
                if (emotionColor[count].created_at == cleanDayString) {
                    let scores = emotionColor[count].emotionscore
                    let emoticon = document.createElement("i");
                        switch (true){
                            //     매우 부정
                            case scores > -2 && scores <= -1.2  :
                                // daySquare.style.background = '#EC00FFFF';
                                emoticon.classList.add("emoticon");
                                emoticon.classList.add("fa-solid");
                                emoticon.classList.add("fa-face-dizzy");
                                emoticon.style.color = "#EC00FFFF";
                                daySquare.appendChild(emoticon);
                                break;
                            //     약간 부정
                            case scores > -1.2 && scores <= -0.5 :
                                // daySquare.style.background = '#ff2d2d';
                                emoticon.classList.add("emoticon");
                                emoticon.classList.add("fa-solid");
                                emoticon.classList.add("fa-face-frown-open");
                                emoticon.style.color = "#ff2d2d";
                                daySquare.appendChild(emoticon);
                                break;
                            //     중립
                            case scores > -0.5 && scores <= 0.1 :
                                // daySquare.style.background = '#919191';
                                emoticon.classList.add("emoticon");
                                emoticon.classList.add("fa-solid");
                                emoticon.classList.add("fa-face-meh");
                                emoticon.style.color = "#919191";
                                daySquare.appendChild(emoticon);
                                break;
                            //     약간 긍정
                            case scores > 0.1 && scores <= 1.0 :
                                // daySquare.style.background = '#4476ff';
                                emoticon.classList.add("emoticon");
                                emoticon.classList.add("fa-solid");
                                emoticon.classList.add("fa-face-laugh");
                                emoticon.style.color = "#4476ff";
                                daySquare.appendChild(emoticon);
                                break;
                            //     매우 긍정
                            case scores > 1.0 && scores <= 2.0 :
                                // daySquare.style.background = '#00FF7F';
                                emoticon.classList.add("emoticon");
                                emoticon.classList.add("fa-solid");
                                emoticon.classList.add("fa-face-laugh-squint");
                                emoticon.style.color = "#00FF7F";
                                daySquare.appendChild(emoticon);
                                break;
                            //     10같은 경우 판변 못함으로 기본값처리
                            default :
                                // daySquare.style.background = '#2f2f2f';
                                emoticon.classList.add("emoticon");
                                emoticon.classList.add("fa-solid");
                                emoticon.classList.add("fa-face-meh-blank");
                                emoticon.style.color = "#2f2f2f";
                                daySquare.appendChild(emoticon);

                        }
                }
            }


            if (i - paddingDays === new Date().getDate() && nav === 0) {
                daySquare.id = 'currentDay';
            }
        } else {
            daySquare.classList.add('padding');
        }

        
        calendar.appendChild(daySquare);

        //메시지 추가 부분
        let replyDot = document.createElement("a")
        replyDot.classList.add("inform");
            for(let k = 0; k < replyAlarm.length; ++k){
                if(replyAlarm[k].created_at == barDayString){
                    replyDot.innerHTML = replyAlarm[k].countPerDay;
                    daySquare.appendChild(replyDot);
                }
            }
        //끝

        }


}
// load 끝

// 달력 앞으로 뒤로 움직임
function initButton(){
    document.getElementById("nextButton").addEventListener('click', () => {
        nav++;
        load();
    });

    document.getElementById("backButton").addEventListener('click', () => {
        nav--;
        load();
    });
}

initButton();
load();

$(".searchinput").on("keyup", function(){
    $("#moreThanTwo").html("");
    let searchInput = $(".searchinput").val();
    if(searchInput.length > 1){
        $.ajax({
            url : "findingContent",
            data : {searchInput : searchInput},
            dataType : "json",
            success : function(data){

                let searchLine = '';

                for(let items of data){
                searchLine +=    `
                
                  <div class="card">
                
                  <div class="card-body">
                    <h5 class="card-title">${items.created_at}</h5>
                    <p class="card-text">${items.content_notag}</p>
                    <a th:href="@{/diary/read(dayString=${items.created_at})}" class="btn btn-primary">GO</a>
                  </div>
                </div>
                `
                }

                $("#searchBox").html(searchLine);
            }
        })
    }else{
        $("#moreThanTwo").html("** 2글자 이상 입력해주세요");
    }
    // load(); 입력이 0이면 캘린더가 뜨게 해야하는데... 어캐하지


})

$(".searchinput").on("blur", function(){
    $(".card").css("display", "none");
    $("#moreThanTwo").html("");
})




// function imageBox(n){
//         let canbody = "";
//     for (let item of n) {
//         console.log("item", item)
//         console.log('item.img, item.nickname', item.img, item.nickname )
//         canbody +=`
//          <div class="friend-popup row d-flex align-items-center mt-3" nickname=${item.nickname}>
//             <div class="friendList col-5 text-start">
//                 <img class="friendImage" src="${item.img}" alt="">
//             </div>
//             <div class="col-5 text-white fw-bold lead">
//                 <span>${item.nickname}</span>
//             </div>
//             `
//
//             if(item.todayDiary == 1){
//                 canbody +=
//                     // 여기는 만약 오늘 일기를 쓴 상대방이라면 알림이 뜬다.
//                     `
//                         <div class="col-2">
//                             <i class="fa-solid fa-bell fa-shake" style="color: #ffff00;"></i>
//                         </div>
//                     `
//             }
//
//             canbody +=
//             `
//                </div>
//             `
//
//
//
//     }
//     document.querySelector(".offcanvas-body").innerHTML = canbody;
//
// }

function imageBox(n) {
    let offcanvasBody = document.querySelector(".offcanvas-body");
    for (let item of n) {
        let friendPopup = document.createElement("div");
        friendPopup.className = "friend-popup row d-flex align-items-center mt-3";
        friendPopup.setAttribute("nickname", item.nickname);

        let friendList = document.createElement("div");
        friendList.className = "friendList col-5 text-start";
        let friendImage = document.createElement("img");
        friendImage.className = "friendImage";
        friendImage.src = item.img;
        friendList.appendChild(friendImage);

        let friendInfo = document.createElement("div");
        friendInfo.className = "col-5 text-white fw-bold lead";
        let nicknameSpan = document.createElement("span");
        nicknameSpan.textContent = item.nickname;
        friendInfo.appendChild(nicknameSpan);

        friendPopup.appendChild(friendList);
        friendPopup.appendChild(friendInfo);

        
        // 수정 중
        if (item.todayDiary == 1) {
            let bellIcon = document.createElement("div");
            bellIcon.className = "col-2";
            let bellIconElement = document.createElement("i");
            bellIconElement.className = "fa-solid fa-bell fa-shake";
            bellIconElement.style.color = "#ffff00";
            bellIcon.appendChild(bellIconElement);
            friendPopup.appendChild(bellIcon);
        }else if(item.todayDiary == -1){
            let eyeBall = document.createElement("div");
            eyeBall.className = "col-2";
            let eyeBallElement = document.createElement("i");
            eyeBallElement.className = "fa-solid fa-eye";
            eyeBallElement.style.color = "#ff0000";
            eyeBall.appendChild(eyeBallElement);
            friendPopup.appendChild(eyeBall);
        }

        friendPopup.onclick = function(){
            location.href="friend/friendHomepage?nickname=" + item.nickname;
        }

        offcanvasBody.appendChild(friendPopup);
    }
}