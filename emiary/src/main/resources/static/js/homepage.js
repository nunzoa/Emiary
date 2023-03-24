let nav = 0;
const calendar = document.getElementById('calendar');
const modalBackDrop = document.querySelector(".modalBackDrop");
const writeEventModal = document.getElementById('writeEventModal');
const delReadModal = document.getElementById("delReadModal");
const todayDiary = document.getElementById("todayDiary");
const weekdays = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']

let emotionColor = "";

$(document).ready(function() {
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
});


// load 시작
function load() {
    const dt = new Date();

    if (nav !== 0) {
        dt.setMonth(new Date().getMonth() + nav);
    }

    const day = dt.getDate();
    const month = dt.getMonth();
    const year = dt.getFullYear();

    const firstDayOfMonth = new Date(year, month, 1);
    const daysInMonth = new Date(year, month + 1, 0).getDate();
    const dateString = firstDayOfMonth.toLocaleDateString('en-us', {
        weekday: 'long',
        year: 'numeric',
        month: 'numeric',
        day: 'numeric'
    })

    const paddingDays = weekdays.indexOf(dateString.split(',')[0]);
    document.getElementById("monthDisplay").innerText = `${dt.toLocaleDateString('en-us', {month: 'numeric'})}월 ${year}`

    calendar.innerHTML = '';

    // for문 캘린더 작성
    for (let i = 1; i <= paddingDays + daysInMonth; i++) {
        let daySquare = document.createElement('a');
        daySquare.classList.add('day');
        let cleanDayString = `${year}${month + 1}${i - paddingDays}`;
        let barDayString = `${year}-${month + 1}-${i - paddingDays}`;
        
        // modal창 확인 시 사용 및 read시 사용
        daySquare.setAttribute("num", barDayString);
        let dayString = `${year}년 ${month + 1}월 ${i - paddingDays}일`;

        // +버튼 누를 시 일기 작성으로 이동
        let todayDate = new Date(year, month, day);
        let todayString = `${todayDate.getFullYear()}년 ${(todayDate.getMonth() + 1)}월 ${todayDate.getDate()}일`;
        todayDiary.href = `/emiary/diary/write?dayString=` + todayString;
        // paddingDays는 제외하고 출력
        if (i > paddingDays) {
            daySquare.innerText = i - paddingDays;
            
            daySquare.addEventListener("click", function(){
                let dateForOne = daySquare.getAttribute("num");

                // 오늘 이후로 일기를 쓸 수 없게 만드는 코드 구현?

                console.log("dateForOne : ", dateForOne);
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
                    },
                    error : function (xhr, status, error) {
                    console.log(status);
                }

                })

                document.getElementById("writeDiary").onclick = function () {
                    location.href = `/emiary/diary/write?dayString=${dayString}`;
                }

                document.getElementById("readDiary").onclick = function () {
                    console.log(barDayString);
                    location.href = `/emiary/diary/read?dayString=${barDayString}`;
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
                                daySquare.style.background = '#EC00FFFF';
                                emoticon.classList.add("emoticon");
                                emoticon.classList.add("fa-solid");
                                emoticon.classList.add("fa-face-dizzy");
                                daySquare.appendChild(emoticon);
                                break;
                            //     약간 부정
                            case scores > -1.2 && scores <= -0.5 :
                                daySquare.style.background = '#ff2d2d';
                                emoticon.classList.add("emoticon");
                                emoticon.classList.add("fa-solid");
                                emoticon.classList.add("fa-face-frown-open");
                                daySquare.appendChild(emoticon);
                                break;
                            //     중립
                            case scores > -0.5 && scores <= 0.1 :
                                daySquare.style.background = '#919191';
                                emoticon.classList.add("emoticon");
                                emoticon.classList.add("fa-solid");
                                emoticon.classList.add("fa-face-meh");
                                daySquare.appendChild(emoticon);
                                break;
                            //     약간 긍정
                            case scores > 0.1 && scores <= 1.0 :
                                daySquare.style.background = '#4476ff';
                                emoticon.classList.add("emoticon");
                                emoticon.classList.add("fa-solid");
                                emoticon.classList.add("fa-face-laugh");
                                daySquare.appendChild(emoticon);
                                break;
                            //     매우 긍정
                            case scores > 1.0 && scores <= 2.0 :
                                daySquare.style.background = '#00FF7F';
                                emoticon.classList.add("emoticon");
                                emoticon.classList.add("fa-solid");
                                emoticon.classList.add("fa-face-laugh-squint");
                                daySquare.appendChild(emoticon);
                                break;
                            //     10같은 경우 판변 못함으로 기본값처리
                            default :
                                daySquare.style.background = '#2f2f2f';

                        }
                }
            }


            if (i - paddingDays === day && nav === 0) {
                daySquare.id = 'currentDay';
            }
        } else {
            daySquare.classList.add('padding');
        }
        calendar.appendChild(daySquare);
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





