$(document).ready(function(){

    showingMessage();







})

function showingMessage(){
    
    
    $.ajax({
        url: "/emiary/friend/friendList",
        dataType: "json",
        success: function(n) {
            console.log("친구리스트 : ", n)

            let listOfFriend = "";
            for(let item of n){
                listOfFriend +=
                    `
                    <a class="list-group-item list-group-item-action border-0 ps-5 one-friend" email="${item.email}">
                      <!--알람 뜸-->
                      <div class="badge bg-success float-right">1</div>
                      <div class="d-flex align-items-start">
                        <!--이미지 들어가고 -->
                        <img src="${item.img}" class="rounded-circle mr-1" alt="Vanessa Tucker" width="40" height="40">
                        <div class="flex-grow-1 ml-3">
                          <!--닉네임 들어가고-->
                          ${item.nickname}
                        </div>
                      </div>
                    </a>
                    `
            }

            $(".friendArray").html(listOfFriend)
            //친구 리스트 불러오기




            //     친구 리스트 버튼 눌러서 메시지 갖고 오기 시작
            $(".one-friend").on("click", function(){

                let email = $(this).attr('email');
                // 메시지 보내기 시작
                $("#sendMessageBtn").on("click", function(){
                    let msgContent = $("#messageContent").val();
                    $.ajax({
                        url : "sendMessage",
                        data : {"content" : msgContent, "receiver_email" : email},
                        success : function(){
                            getMessageList(email)
                        },
                        error : function(n){
                            console.log(n);
                        }
                    })
                })
                // 메시지 보내기 끝

                // 친구와의 메시지 불러오기
                getMessageList(email)




            })
        //     친구 리스트 버튼 눌러서 메시지 갖고 오기 끝




        },
        error : function(n){
            console.log(n);
        }
    //    친구 리스트 갖고 오기 끝
        
    });
}


function getMessageList(email){
    $.ajax({
        url : "getMessageList",
        data : {friendEmail : email},
        success : function(n){
            console.log(n);

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
                                      ${msg.content}
                                    </div>
                                  </div>
                                `
                }else{
                    chatRoom =
                        `
                                        <div className="position-relative">
                                            <img src="${msg.imgurl}"
                                                 className="rounded-circle mr-1" alt="Sharon Lessman" width="40" height="40">
                                        </div>
                                        <div className="flex-grow-1 pl-3">
                                            <!--상대방 닉네임 들어감-->
                                            <strong>${msg.nickname}</strong>
                                        </div>
                                    `


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
                                          ${msg.content}
                                        </div>
                                      </div>
                                    `
                }
                //     나눠야함
            }

            $(".chatRoom").html(chatRoom)
            $(".chat-messages").html(messageContent)

        },
        error : function(n){
            console.log(n);
        }
    })
}