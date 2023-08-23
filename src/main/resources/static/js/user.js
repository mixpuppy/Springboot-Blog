let index = {
    init:function () {
        //jQuery 사용
        $("#btn-save").on("click",()=> {
            this.save();
        });
        $("#btn-login").on("click", () => {
            this.login();
        });
    },
    save:function () {
        // alert("user의 save 함수 호출");
        let data = {
            username:$("#username").val(),
            password:$("#password").val(),
            email:$("#email").val()  // 이 id 들을 찾아 data 오브젝트(js오브젝트) 에 넣음
        }
        // console.log(data);

        // ajax 통신을 이용해 3개의 데이터를 json 으로 변경하여 insert 요청
        $.ajax({
            type:"post",
            url:"/mixdog/api/user", // 요청할 url
            data:JSON.stringify(data), // http body 데이터, JSON.stringify()는 자ㅏ스크립트의 값을 JSON 문자열로 변환
            //서버로 전송할 데이터이다. 즉 서버에 요청할 때 보낼 매개변수 설정하는 것.
            contentType:"application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
            dataType:"json" // 서버에서 전송받을 데이터형식을 말함. 즉 응답 받을 데이터 타입 (XML,HTML,JSON)을 설정
        }).done(function (resp){ // 위 응답의 결과가 함수의 파라미터로 전달
            alert("회원가입이 완료되었습니다.");
            location.href="/mixdog";
            // console.log(resp);
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },

    login:function () {
        // alert("user의 save 함수 호출");
        let data = {
            username:$("#username").val(),
            password:$("#password").val()
        }
        // console.log(data);

        // ajax 통신을 이용해 3개의 데이터를 json 으로 변경하여 insert 요청
        $.ajax({
            type:"post",
            url:"/mixdog/api/user/login", // 요청할 url
            data:JSON.stringify(data), // http body 데이터, JSON.stringify()는 자ㅏ스크립트의 값을 JSON 문자열로 변환
            //서버로 전송할 데이터이다. 즉 서버에 요청할 때 보낼 매개변수 설정하는 것.
            contentType:"application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
            dataType:"json" // 서버에서 전송받을 데이터형식을 말함. 즉 응답 받을 데이터 타입 (XML,HTML,JSON)을 설정
        }).done(function (resp){ // 위 응답의 결과가 함수의 파라미터로 전달
            if(resp.status === 200) {
                alert("로그인이 완료되었습니다.");
                location.href="/mixdog";
            } else {
                alert("아이디 혹은 비밀번호가 일치하지 않습니다.");
            }
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    }
}

index.init();