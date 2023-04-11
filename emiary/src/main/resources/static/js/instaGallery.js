$(document).ready(function(){
    $.ajax({
        url : "getInsta",
        dataType : "json",
        success : function(n){
            console.log(n);
        },
        error: function (n) {
            console.log(n);
        },
    })
})

//