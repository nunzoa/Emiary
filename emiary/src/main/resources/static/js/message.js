$(document).ready(function(){

    showFriendList();

})



function showFriendList(){
    $.ajax({
        url: "/emiary/friend/friendList",
        dataType: "json",
        success: function(n) {
            console.log("친구리스트 : ", n)

            //메일 미확인건

            let listOfFriend = "";
            let ajaxCount = 0;

            for(let item of n){
                $.ajax({
                    url : "checkMail",
                    data : {friend_email: item.email},
                    success : function (count){
                        listOfFriend +=
                            `
                    <a class="list-group-item list-group-item-action border-0 ps-5 one-friend mb-3" email="${item.email}">
                          <!--알람 뜸-->
                          `

                        if(count != 0){
                            console.log("이거 왜 안뜸?")
                            listOfFriend +=
                                `
                      <div class="badge bg-danger float-right">${count}</div>
                      `
                        }


                        listOfFriend +=
                            `
                      <div class="d-flex align-items-center">
                        <!--이미지 들어가고 -->
                        <img src="${item.img}" class="rounded-circle mr-1" alt="Vanessa Tucker" width="40" height="40">
                        <div class="flex-grow-1 ml-3 ms-3">
                          <!--닉네임 들어가고-->
                          ${item.nickname}
                        </div>
                      </div>
                    </a>
                    `;

                        ajaxCount++;

                        // 모든 $.ajax() 함수의 호출이 완료되었을 때 listOfFriend 변수의 값을 업데이트
                        if (ajaxCount === n.length) {
                            $(".friendArray").html(listOfFriend);
                        }
                    }
                })
            }
        },
        error : function(n){
            console.log(n);
        }

    });

}

$(document).on("click", ".one-friend", function() {

        let friend_email = $(this).attr('email');
        console.log("친구 이메일 ", friend_email)


        getProfile(friend_email);
        getMessageList(friend_email);
        messageRead(friend_email);

        // 메시지 보내기 시작
        $("#sendMessageBtn").off().on("click", function(e){
            e.preventDefault(); // 제출 이벤트의 기본 동작 중단
            let msgContent = $("#messageContent").val();
            $.ajax({
                url : "sendMessage",
                data : {"content" : msgContent, "receiver_email" : friend_email},
                success : function(){
                    getMessageList(friend_email)
                    $("#messageContent").val("");

                },
                error : function(n){
                    console.log(n);
                }
            })
        })

});


function getMessageList(email){


    $(".chat-messages").html("");

    $.ajax({
        url : "getFriendProfile",
        data : {friendEmail : email},
        success : function(){

        }
    })

    $.ajax({
        url : "getMessageList",
        data : {friendEmail : email},
        success : function(n){
            console.log("getMessageList", email);

            let chatRoom = "";
            let messageContent = "";


            for(let msg of n){

                if(msg.sender_email != email){

                    messageContent +=
                                `
                                 <div class="chat-message-right pb-4">
                                    <div>
                                      <!--자기 프로필 이미지-->
                                      <img src="${msg.imgurl}" class="rounded-circle mr-1" alt="" width="40" height="40">
                                      <!--보낸 시간 나옴-->
                                      <div class="text-muted small text-nowrap mt-2">${msg.sent_at}</div>
                                    </div>
                                    <div class="flex-shrink-1 bg-light rounded py-2 px-3 mr-3">
                                      <!--메시지 내용-->
                                      <div class="font-weight-bold mb-1">나</div>
                                 `
                    let imgCheck = msg.content.substring(msg.content.lastIndexOf("."));
                    console.log(imgCheck)
                    if(imgCheck == '.png' || imgCheck == '.webp' || imgCheck == '.jpg' || imgCheck == '.jpeg'){
                        messageContent +=  `
                                         <a href="${msg.content}">
                                        <img src="${msg.content}" width="250" height="350">
                                        </a>
                                        `
                    }else{
                        messageContent +=  `${msg.content}`
                    }

                        messageContent +=
                                `
                                    </div>
                                  </div>
                                `
                }else{

                    messageContent +=
                        `
                         <div class="chat-message-left pb-4">
                            <div>
                              <!--상대방 프로필 이미지-->
                              <img src="${msg.imgurl}" class="rounded-circle mr-1" alt="" width="40" height="40">
                              <!--보낸 시간-->
                              <div class="text-muted small text-nowrap mt-2">${msg.sent_at}</div>
                            </div>
                            <div class="flex-shrink-1 bg-light rounded py-2 px-3 ml-3">
                              <!--상대방 내용-->
                              <div class="font-weight-bold mb-1">${msg.nickname}</div>
                              `
                    let imgCheck = msg.content.substring(msg.content.lastIndexOf("."));
                    console.log(imgCheck)
                    if(imgCheck == '.png' || imgCheck == '.webp' || imgCheck == '.jpg' || imgCheck == '.jpeg'){
                        messageContent +=  `
                        <a href="${msg.content}">
                                        <img src="${msg.content}" width="250" height="350">
                                        </a>
                        `
                    }else{
                        messageContent +=  `${msg.content}`
                    }

                    messageContent +=
                        `
                             
                            </div>
                          </div>
                        `
                }
                //     나눠야함
            }
            $(".chat-messages").html(messageContent)
            var chatMessages = document.getElementById('chatMessages');
            chatMessages.scrollTop = chatMessages.scrollHeight;
        },
        error : function(n){
            console.log(n);
        }
    })
}

function getProfile(friend_email){
    $(".chatRoom").html("");
    $.ajax({
        url : "getFriendProfile",
        data : {friendEmail : friend_email},
        success : function(n){
            console.log("여기 갔다오니", n)
            let chatRoom =
                `
                            <div className="position-relative">
                                <img src="${n.imgURL}"
                                     className="rounded-circle mr-1" alt="Sharon Lessman" width="40" height="40">
                            </div>
                            <div className="flex-grow-1 pl-3">
                                <!--상대방 닉네임 들어감-->
                                <strong>${n.nickname}</strong>
                            </div>
                        `

            $(".chatRoom").html(chatRoom);

        }
    })
}

function messageRead(friend_email){

    $.ajax({
        url : "messageRead",
        data : {friend_email: friend_email},
        success : function(n){
            console.log(n);
        }
    })
}
