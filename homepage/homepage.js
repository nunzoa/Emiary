let nav = 0;
const calendar = document.getElementById('calendar');


const newEventModal = document.getElementById("modalwindow");
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
    document.getElementById("monthDisplay").innerText = `${dt.toLocaleDateString('en-us', {month : 'long'})} ${year}`

    calendar.innerHTML ='';

    for(let i = 1; i <= paddingDays + daysInMonth; i++){
        const daySquare = document.createElement('a');
        daySquare.classList.add('day');
        daySquare.classList.add('d-flex');
        daySquare.classList.add('m-1');
        
        daySquare.setAttribute("href", `#`)

        // // 모달창에 필요한 부분
        // const dayString = `${month + 1}/${i - paddingDays}/${year}`;
        
        
        if(i > paddingDays){
            daySquare.innerText = i - paddingDays;
            // 모달창
             // add event listener to each day element
             daySquare.addEventListener('click', () => {
                // display newEventModal
                newEventModal.style.display = "block";
                
            });

            document.querySelector('#close').addEventListener('click', () => {
                newEventModal.style.display = "none";
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
