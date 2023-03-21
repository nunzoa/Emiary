let nav = 0;
const calendar = document.getElementById('calendar');
const modalBackDrop = document.getElementById("modalBackDrop");
const newEventModal = document.getElementById("modalwindow");
const todayDiary = document.getElementById("todayDiary");
const weekdays = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']

function load(){

    const dt = new Date();

    if(nav !== 0){
        dt.setMonth(new Date().getMonth() + nav);
    }

    const day = dt.getDate();
    const month = dt.getMonth();
    const year = dt.getFullYear();

    const firstDayOfMonth = new Date(year, month, 1);
    const daysInMonth = new Date(year, month + 1, 0).getDate();
    const dateString = firstDayOfMonth.toLocaleDateString('en-us', {
        weekday : 'long',
        year : 'numeric',
        month : 'numeric',
        day : 'numeric'
    })

    const paddingDays = weekdays.indexOf(dateString.split(',')[0]);
    document.getElementById("monthDisplay").innerText = `${dt.toLocaleDateString('en-us', {month : 'numeric'})}월 ${year}`

    calendar.innerHTML ='';

    for(let i = 1; i <= paddingDays + daysInMonth; i++){
        const daySquare = document.createElement('a');
        daySquare.classList.add('day');

        const dayString = `${year}년 ${month + 1}월 ${i - paddingDays}일`;
        // 모달창 없앨까 말까 고민중...
        // daySquare.href = `/emiary/diary/write?dayString` + dayString;
        let todayDate = new Date(year, month, day);
        todayString = `${todayDate.getFullYear()}년 ${(todayDate.getMonth() + 1)}월 ${todayDate.getDate()}일`;
        todayDiary.href = `/emiary/diary/write?dayString=` + todayString;
        console.log(daySquare);

        if(i > paddingDays){

            daySquare.innerText = i - paddingDays;

            if( day >= i - paddingDays || month < new Date().getMonth()) {
                daySquare.addEventListener('click', () => {
                    newEventModal.style.display = "block";
                    modalBackDrop.style.display = "block";


                    document.getElementById("writeDiary").onclick = function(){
                        location.href = `/emiary/diary/write?dayString=${dayString}`;
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




        }else{
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
//글 쓰기로 이동
let write = document.getElementById("writeDiary");
write.onclick = function(){
    let monthDisplay = document.getElementById("monthDisplay");
    let day =
    console.log("monthdisplay값은? ", monthDisplay.innerHTML);
    console.log("a의 값은?", )
    // location.href = "/emiary/diary/write"
}

//글 읽기로 이동
let read = document.getElementById("readDiary");



initButton();
load();


