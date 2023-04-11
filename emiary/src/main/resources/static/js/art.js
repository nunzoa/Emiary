$(document).ready(function(){
    const dt = new Date();
    let yearAndMonth = `${dt.getFullYear()}-${dt.getMonth() + 1}`

    $.ajax({
        url: "getAIImg",
        data: {yearAndMonth: yearAndMonth},
        success: function (n) {
            let content = "";
            for(let items of n){

                let keyword = items.wordsforai.split(",");
                keyword.shift();
                keyword.pop();

                content +=
                    `
                     <div class="image1 m-3">
                        <a href="${items.aiIMG}">
                            <img src="${items.aiIMG}" alt="">
                        </a>
                        <div><a class="created_at" href="/emiary/diary/read?dayString=${items.created_at}" style="text-decoration: none; color : #000000">${items.created_at}</a></div>
                        <div>    
                        `

                for(let word of keyword){
                    content +=
                        `
                        <button type="button" class="btn btn-sm btn-outline-secondary mt-1">${word}</button>
                        `
                }

                content +=
                    `
                        </div>
                    </div>
                    `

            }
            $(".galleryblock").html(content);



            $(".btn").on("click", function(){
                $.ajax({
                    url : "getAIImg",
                    data : {keyword : $(this).text()},
                    dataType : "json",
                    success : function(n){

                        let content = "";
                        for(let items of n){

                            let keyword = items.wordsforai.split(",");
                            keyword.shift();
                            keyword.pop();

                            content +=
                                `
                     <div class="image1 m-3">
                        <a href="${items.aiIMG}">
                            <img src="${items.aiIMG}" alt="">
                        </a>
                        <div><a class="created_at" href="/emiary/diary/read?dayString=${items.created_at}" style="text-decoration: none; color : #000000">${items.created_at}</a></div>
                        <div>    
                        `

                            for(let word of keyword){
                                content +=
                                    `
                        <button type="button" class="btn btn-sm btn-outline-secondary mt-1">${word}</button>
                        `
                            }

                            content +=
                                `
                        </div>
                    </div>
                    `

                        }
                        $(".galleryblock").html(content);

                    }
                })
            })


        }
    });




})