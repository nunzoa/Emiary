$(document).ready(function(){
    $.ajax({
        url : "getInsta",
        dataType : "json",
        success : function(n){
            let content = JSON.parse(n)
            let sources = content.hits.hits;

            let insta = "";

            for(let src of sources){
                // console.log(src)
                // console.log(src._source.post_desc);

                //
                for(let imgurl of src._source.img_list){
                    let img_alt = imgurl.img_alt.substring(0, imgurl.img_alt.indexOf("."))
                    insta +=
                        `
                        <div class="col-xl-3 col-lg-4 col-md-6 mb-4">
                            <div class="bg-white rounded shadow-sm"><img src="${imgurl.img_url}" alt="" class="img-fluid card-img-top">
                                <div class="p-4">
                                    <h5> <a href="#" class="text-dark">${src._source.author_id}</a></h5>
                                    <p class="small text-muted mb-0">${src._source.post_desc}</p>
                                    <div class="d-flex align-items-center justify-content-between rounded-pill bg-light px-3 py-2 mt-4 text-center">
                                        <p class="small mb-0"><i class="fa fa-picture-o mr-2"></i><span class="font-weight-bold p-2">${img_alt}</span></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        `

                }
            }


            $(".main").html(insta);

        },
        error: function (n) {
            console.log(n);
        },
    })
})

//