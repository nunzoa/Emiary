let nav = 0;
const calendar = document.getElementById('calendar');
const modalBackDrop = document.getElementById("modalBackDrop");
const newEventModal = document.getElementById("modalwindow");
const todayDiary = document.getElementById("todayDiary");
const weekdays = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']
let emotionColor;

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

    for (let i = 1; i <= paddingDays + daysInMonth; i++) {
        const daySquare = document.createElement('a');
        daySquare.classList.add('day');
        let cleanDayString = `${year}${month + 1}${i - paddingDays}`;
        let dayString = `${year}년 ${month + 1}월 ${i - paddingDays}일`;
        let todayDate = new Date(year, month, day);
        let todayString = `${todayDate.getFullYear()}년 ${(todayDate.getMonth() + 1)}월 ${todayDate.getDate()}일`;
        todayDiary.href = `/emiary/diary/write?dayString=` + todayString;


        if (i > paddingDays) {

            daySquare.innerText = i - paddingDays;

            for(let count = 0; count < emotionColor.length; ++count){
                if (emotionColor[count].created_at == cleanDayString) {
                        switch (true){
                            case emotionColor[count].emotionscore < -1 :
                                daySquare.style.background = '#4B0082';
                                break;
                            case emotionColor[count].emotionscore >= -1 && emotionColor[count].emotionscore < 0 :
                                daySquare.style.background = '#C71585';
                                break;
                            case emotionColor[count].emotionscore >= 0 && emotionColor[count].emotionscore < 1 :
                                daySquare.style.background = '#FFFF00';
                                break;
                            case emotionColor[count].emotionscore >= 1 :
                                daySquare.style.background = '#00FF7F';
                                break;
                            default :
                                daySquare.style.background = '#000';
                        }

                }
            }



            if (day >= i - paddingDays || month < new Date().getMonth()) {
                daySquare.addEventListener('click', () => {

                    newEventModal.style.display = "block";
                    document.getElementById("writeDiv").style.display = "block";
                    document.getElementById("readDiv").style.display = "block";
                    // 글이 있으면 글 보기만 볼 수 있게하고
                    // 글이 없으면 글 쓰기만 할 수 있게 한다.
                    // for(let count = 0; count < emotionColor.length; ++count){
                    //     if(emotionColor[count].created_at == cleanDayString){
                    //         document.getElementById("writeDiv").style.display = "none";
                    //     }
                    //
                    // }

                    modalBackDrop.style.display = "block";


                    document.getElementById("writeDiary").onclick = function () {
                        location.href = `/emiary/diary/write?dayString=${dayString}`;
                    }

                    document.getElementById("readDiary").onclick = function () {
                        dayString = dayString
                            .replace("년 ", "-")
                            .replace("월 ", "-")
                            .replace("일", "");
                        console.log("글 읽을 때 날짜는? ", dayString);
                        location.href = `/emiary/diary/read?dayString=${dayString}`;
                    }
                });
            }

            document.querySelector('#close').addEventListener('click', () => {
                newEventModal.style.display = "none";
                modalBackDrop.style.display = "none";
            })

            if (i - paddingDays === day && nav === 0) {
                daySquare.id = 'currentDay';
            }
        } else {
            daySquare.classList.add('padding');
        }
        calendar.appendChild(daySquare);
    }

}

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



