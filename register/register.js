
:root{
    --orange: #ff7f00;
    --green-dark: #13bc7e;
    --light-grey: #f1f1f1;
  
  }

*{
    margin : 0;
    padding : 0;
    box-sizing: border-box;
}

body{
    height : 100vh;
}


/* header 부분 */
header .container{
    max-width: 1200px;
  }
  
  .bg-orange{
    background-color: var(--orange) !important;
  }
  
  
  .container{
    width : 770px;
  }



.login{
    width : 360px;
    height :min-content;
    padding : 20px;
    border-radius : 12px;
    background: var(--orange);
}

.invalid-feedback{
    color : var(--green-dark);
    
}

.form-control:focus{
    box-shadow: 3px 3px 0px var(--green-dark)!important;
}

.form-check-in{
    accent-color: var(--green-dark);
}

.btn{
    width: 150px;
}

.btn:hover{
    background-color: var(--green-dark);
    font-weight: 600;
    border : 1px solid var(--orange)!important;
}

.btn:active{
    background-color: #fff!important;
    color : var(--green-dark)!important;
}


.icon{
    width: 40px;
    height: 40px;
}
.icon:hover{
    border-radius: 50%;
    box-shadow: 4px 4px 0px var(--green-dark);
}

.icon:active{
    border-radius: 50%;
    box-shadow: 4px 4px 0px black;
}

.google{
    background: url(../image/google.png) center/contain no-repeat;

}

.naver{
    background: url(../image/naver.png) center/contain no-repeat;
   
}