let signup = document.getElementById("signup");
signup.onclick = function(){
    location.href = "/emiary/member/register";
}

$(document).ready(function(){
    $("#registerForm").on("submit", function(event){
        event.preventDefault(); // 폼 기본 동작 중단

        var formData = {
            'email': $('input[name=email]').val(),
            'memberpw': $('input[name=memberpw]').val(),
            'cmemberpw': $('input[name=cmemberpw]').val(),
            'nickname': $('input[name=nickname]').val(),
            'birthdate': $('input[name=birthdate]').val(),
            'phone': $('input[name=phone]').val()
        };

        $.ajax({
            url: '/member/register',
            type: 'POST',
            data: formData,
            success : function(){
                location.replace('/emiary/member/loginForm');
            },error : function(){
                console.log("실패");
            }
        })

    })
})