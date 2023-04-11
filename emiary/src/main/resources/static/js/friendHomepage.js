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
        url: 'checkDiary',
        type: 'GET',
        success: function (diaries) {
            emotionColor = diaries;
            load();
        },
        error: function (xhr, status, error) {
            console.log(status);
        }
    });

    // $.ajax({
    //
    // })

});


// load 시작
function load() {
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
                    url : "modalCheck",
                    type : "GET",
                    data : {dateForOne : dateForOne},
                    success : function(n){
                        if(n == 1){
                            delReadModal.style.display = "block";
                            modalBackDrop.style.display = "block";
                        }else{
                            // writeEventModal.style.display = "block";
                        }

                    },
                    error : function (xhr, status, error) {
                    console.log(status);
                }
                })
                // 모달 구분 코드 끝

                // document.getElementById("writeDiary").onclick = function () {
                //     location.href = `/emiary/friend/write?dayString=${barDayString}`;
                // }

                document.getElementById("readDiary").onclick = function () {
                    location.href = `/emiary/friend/read?dayString=${barDayString}`;
                }

                // document.getElementById("deleteDiary").onclick = function () {
                //     location.href = `/emiary/friend/deleteDiary?dayString=${barDayString}`;
                // }
            })

            // document.querySelector('.write-close').addEventListener('click', () => {
            //     writeEventModal.style.display = "none";
            //     modalBackDrop.style.display = "none";
            // })

            document.querySelector('.delRead-close').addEventListener('click', () => {
                delReadModal.style.display = "none";
                modalBackDrop.style.display = "none";
            })

            // 색깔 및 이모티콘 넣기
            for(let count = 0; count < emotionColor.length; ++count){
                if (emotionColor[count].created_at == cleanDayString) {
                    let scores = emotionColor[count].emotionscore
                    let emoticon = document.createElement("img");
                    switch (true){
                        //     매우 부정
                        case scores > -2 && scores <= -1.2  :
                            emoticon.classList.add("shiba");
                            emoticon.src = "https://res.cloudinary.com/dn6holbea/image/upload/v1681213626/%EC%9A%B0%EB%8A%94%EC%8B%9C%EB%B0%94_liodwa.png";
                            daySquare.appendChild(emoticon);
                            break;
                        //     약간 부정
                        case scores > -1.2 && scores <= -0.5 :
                            emoticon.classList.add("shiba");
                            emoticon.src = "https://res.cloudinary.com/dn6holbea/image/upload/v1681213626/%EC%95%BD%EA%B0%84%EB%B6%80%EC%A0%95%EC%8B%9C%EB%B0%94_xalmhh.png";
                            daySquare.appendChild(emoticon);
                            break;
                        //     중립
                        case scores > -0.5 && scores <= 0.1 :
                            emoticon.classList.add("shiba");
                            emoticon.src = "https://res.cloudinary.com/dn6holbea/image/upload/v1681213626/%EC%A4%91%EB%A6%BD%EC%8B%9C%EB%B0%94_cykb2v.png";
                            daySquare.appendChild(emoticon);
                            break;
                        //     약간 긍정
                        case scores > 0.1 && scores <= 1.0 :
                            emoticon.classList.add("shiba");
                            emoticon.src = "https://res.cloudinary.com/dn6holbea/image/upload/v1681213626/%EA%B8%8D%EC%A0%95%EC%8B%9C%EB%B0%94_kehf7d.png";
                            daySquare.appendChild(emoticon);
                            break;
                        //     매우 긍정
                        case scores > 1.0 && scores <= 2.0 :
                            emoticon.classList.add("shiba");
                            emoticon.src = "https://res.cloudinary.com/dn6holbea/image/upload/v1681213626/%EC%99%84%EC%A0%84%EA%B8%8D%EC%A0%95%EC%8B%9C%EB%B0%94_vxnrl3.png";
                            daySquare.appendChild(emoticon);
                            break;
                        //     10같은 경우 판변 못함으로 기본값처리
                        default :
                            emoticon.classList.add("shiba");
                            emoticon.src = "https://res.cloudinary.com/dn6holbea/image/upload/v1681213626/%EA%B3%84%EC%82%B0%EB%AA%BB%ED%95%9C%EC%8B%9C%EB%B0%94_ndh3w7.png";
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



$("#colorExplanation").on("click", function(){

})

