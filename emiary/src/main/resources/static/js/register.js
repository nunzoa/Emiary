$(document).ready(function(){
    $("#signup-btn").on("click", function(event){
        event.preventDefault(); // 폼 기본 동작 중단

        var formData = {
            'email': $('input[name=email]').val(),
            'memberpw': $('input[name=memberpw]').val(),
            'nickname': $('input[name=nickname]').val(),
            'birthdate': $('input[name=birthdate]').val(),
            'phone': $('input[name=phone]').val()
        };

        $.ajax({
            url: 'register',
            type: 'POST',
            data: formData,
            success : function(n){
                swal({
                    title: '회원가입 축하드립니다!',
                    icon: "success",
                    button: "로그인 화면으로!",
                }).then((result) =>
                    location.href = '/emiary/member/loginForm'
                );

            },error : function(){
                console.log("회원가입 실패");
            }
        })

    })
})