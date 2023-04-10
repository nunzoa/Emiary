
$(document).ready(function(){
    let imgCount = 0;



    $.ajax({
        url : "getFriendInfo",
        dataType : "json",
        success : function(data){

            const frame = document.body.querySelector("#frame");
            data.forEach((_data) => appendCard(_data));
            const like = document.querySelector("#like");
            const hate = document.querySelector("#hate");
            let current = frame.querySelector(".card_swipe:last-child");
            let likeText = current.children[0];

            let startX = 0,
                startY = 0,
                moveX = 0,
                moveY = 0;
            let firstCardAnimated = true,
                a_count = 0;
            const MAX_DEFAULT_MOVE = frame.clientWidth * 0.6;
            const FPS = 150;
            let lastTimestamp = 0;
            initCard(current);

            let countData = -1;

            like.onclick = () => {
                countData++;
                if(countData >= data.length) countData = -1;


                $.ajax({
                    url : "like",
                    data : { email : data[countData].email },
                    success : function(n){


                        if(n == "MATCHED"){

                            let heart = document.createElement("i");
                            heart.classList.add("fa-solid");
                            heart.classList.add("fa-heart-pulse");
                            heart.classList.add("fa-flip");
                            heart.style.color = "#ff0000";

                            swal({
                                title: "친구가 되었습니다!",
                                content: heart,
                                button: "나가기!",
                            });

                                $.ajax({
                                    url: "getOneFriendList",
                                    dataType: "json",
                                    success: function(matchedFriendList) {
                                        console.log("one", matchedFriendList);
                                        imageBox(matchedFriendList);
                                    }
                                });

                        }


                    }
                })






                if (!firstCardAnimated) return;
                moveX = 1;
                moveY = 0;
                complete();
            };
            hate.onclick = () => {
                if (!firstCardAnimated) return;
                moveX = -1;
                moveY = 0;
                complete();
            };

            function animateFirstCard(timestamp) {
                if (a_count >= Math.round(70 * Math.PI)) {
                    setTransform(0, 0, 0);
                    firstCardAnimated = true;
                    return cancelAnimationFrame(animateFirstCard);
                }
                requestAnimationFrame(animateFirstCard);
                if (timestamp - lastTimestamp < 1000 / FPS) return;
                a_count += 1;
                moveX = MAX_DEFAULT_MOVE * Math.sin(a_count / 35);
                setTransform(moveX, 0, (moveX / MAX_DEFAULT_MOVE) * 25);
                lastTimestamp = timestamp;
            }

            function appendCard(data) {
                const firstCard = frame.children[0];
                const newCard = document.createElement("div");
                newCard.className = "card_swipe";
                newCard.style.backgroundImage = `url(${data.img})`;
                newCard.innerHTML = `
                                    <div class="is-like">LIKE</div>
                                    <div class="bottom">
                                      <div class="title">
                                        <span id="user-email" hidden>${data.email}</span>
                                        <span>${data.nickname}</span>
                                        <span> 총 일기 수 : ${data.countDiary}</span>
                                      </div>
                                      <div class="info">
                                        요즘 나는 : ${data.emotion}
                                      </div>
                                    </div>
                                    `;
                if (firstCard) frame.insertBefore(newCard, firstCard);
                else frame.appendChild(newCard);
                imgCount++;
            }

            function initCard(card) {
                if (!firstCardAnimated) animateFirstCard();
                card.addEventListener("pointerdown", onPointerDown);
            }

            function setTransform(x, y, deg, duration) {
                current.style.transform = `translate3d(${x}px, ${y}px, 0) rotate(${deg}deg)`;
                likeText.style.opacity = Math.abs((x / innerWidth) * 2.1);
                likeText.className = `is-like ${x > 0 ? "like" : "nope"}`;
                if (duration) current.style.transition = `transform ${duration}ms`;
            }

            function onPointerDown({ clientX, clientY }) {
                if (!firstCardAnimated) return;
                startX = clientX;
                startY = clientY;
                current.addEventListener("pointermove", onPointerMove);
                current.addEventListener("pointerup", onPointerUp);
                current.addEventListener("pointerleave", onPointerUp);
            }

            function onPointerMove({ clientX, clientY }) {
                moveX = clientX - startX;
                moveY = clientY - startY;
                setTransform(moveX, moveY, (moveX / innerWidth) * 50);
            }

            function onPointerUp() {
                current.removeEventListener("pointermove", onPointerMove);
                current.removeEventListener("pointerup", onPointerUp);
                current.removeEventListener("pointerleave", onPointerUp);
                if (Math.abs(moveX) > frame.clientWidth / 2) {

                    current.removeEventListener("pointerdown", onPointerDown);
                    complete();
                } else cancel();
            }

            function complete() {
                const flyX = (Math.abs(moveX) / moveX) * innerWidth * 1.3;
                const flyY = (moveY / moveX) * flyX;
                setTransform(flyX, flyY, (flyX / innerWidth) * 50, innerWidth);

                const prev = current;
                const next = current.previousElementSibling;
                if (next) initCard(next);
                current = next;
                likeText = current.children[0];
                appendCard(data[imgCount % data.length]);
                setTimeout(() => frame.removeChild(prev), innerWidth);
            }

            function cancel() {

                setTransform(0, 0, 0, 100);
                setTimeout(() => (current.style.transition = ""), 100);
            }





        }
    })

    $.ajax({
        url: "friendList",
        dataType: "json",
        success: function(n) {
            imageBox(n);
        }
    });



    function imageBox(n){
        console.log("n의 길이 ", n.length);

        for (let item of n) {
            console.log("item이 머가 나오길래  ", item);
            const friendRow = document.createElement('div');  // div 요소 생성
            friendRow.classList.add('row', 'p-1', 'friend_row');  // class 추가
            friendRow.setAttribute("email", item.email);

            // 다른 친구의 일기장으로 가기
            friendRow.onclick = function(){
                location.href="friendHomepage?nickname=" + item.nickname;
            }

            const col1 = document.createElement('div');  // 첫 번째 col div 요소 생성
            col1.classList.add('col-6');
            const img = document.createElement('img');  // img 요소 생성
            img.classList.add('friend_profile');
            img.setAttribute('src', item.img);  // 이미지 소스 설정
            col1.appendChild(img);  // col1에 img 추가
            friendRow.appendChild(col1);  // friendRow에 col1 추가

            const col2 = document.createElement('div');  // 두 번째 col div 요소 생성
            col2.classList.add('col-6', 'ps-1', 'd-flex', 'justify-item-center', 'align-items-center');
            const name = document.createElement('p');  // 닉네임 p 요소 생성
            name.classList.add('info-c');
            name.innerText = item.nickname;  // 닉네임 설정
            col2.appendChild(name);  // col2에 name 추가
            friendRow.appendChild(col2);  // friendRow에 col2 추가
            document.getElementById("friend_list").appendChild(friendRow);  // friendRow를 body에 추가
        }


    }



})



