
$(document).ready(function(){

    let imgCount = 0;
    const cloudUrl = "https://djjjk9bjm164h.cloudfront.net/";
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

            like.onclick = () => {
                $.ajax({
                    url : "like",
                    data : { email : $("#user-email").text() },
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
                                        <span>총 일기 수 : ${data.countDiary}</span>
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
                appendCard(data[imgCount % 4]);
                setTimeout(() => frame.removeChild(prev), innerWidth);
            }

            function cancel() {

                setTransform(0, 0, 0, 100);
                setTimeout(() => (current.style.transition = ""), 100);
            }



        }
    })




})



