(function ($) {

    // 검색 결과 vue object
    var search_result = new Vue({
        el: '#search-result',
        data: {
            search_result : {}
        },
        methods: {
            wishButton: function (event) {
                console.log("add");
            }
        }
    });

    // 맛집 목록 vue object
    var wish_list = new Vue({
        el: '#wish-list',
        data: {
            wish_list : {}
        },
        methods: {
            addVisit: function (index) {
                $.ajax({
                    type: "POST" ,
                    async: true ,
                    url: `/api/restaurant/${index}`,
                    timeout: 3000
                });

                getWishList();
            },
            deleteWish: function (index) {
                $.ajax({
                    type: "DELETE" ,
                    async: true ,
                    url: `/api/restaurant/${index}`,
                    timeout: 3000
                });
                getWishList();
            }
        }
    });

    // search
    $("#searchButton").click(function () {
        const query = $("#searchBox").val();
        $.get(`/api/restaurant/search?query=${query}`, function (response) {
            console.log("API 응답:", response); // 응답 확인
            search_result.search_result = response;
            console.log("Vue 데이터:", search_result.search_result); // 데이터 확인
            $('#search-result').attr('visibility','visible');
        }).fail(function (jqXHR, textStatus) {
            console.error("API 호출 실패:", textStatus); // 오류 확인
        });
    });

    // Enter
    $("#searchBox").keydown(function(key) {
        if (key.keyCode === 13) {
            const query = $("#searchBox").val();
            $.get(`/api/restaurant/search?query=${query}`, function (response) {
                search_result.search_result = response;
                $('#search-result').attr('visibility','visible');
            });
        }
    });

    $("#wishButton").click(function () {
        $.ajax({
            type: "POST" ,
            async: true ,
            url: "/api/restaurant",
            timeout: 3000,
            data: JSON.stringify(search_result.search_result),
            contentType: "application/json",
            error: function (request, status, error) {

            },
            success: function (response, status, request) {
                getWishList();
            }
        });
    });

    function getWishList(){
        $.get(`/api/restaurant/all`, function (response) {
            wish_list.wish_list = response;
        });
    }

    $(document).ready(function () {
        console.log("init")
    });

})(jQuery);