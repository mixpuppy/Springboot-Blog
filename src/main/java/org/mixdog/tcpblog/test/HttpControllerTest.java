package org.mixdog.tcpblog.test;

import org.springframework.web.bind.annotation.*;


// @Controller : 사용자가 요청하면 HTML 파일을 응답

@RestController  // 사용자가 요청하면 Data 를 응답
public class HttpControllerTest {
    @GetMapping("/test/get")
    public String getTest(Member m) {
        return "get 요청 : " +m.getId() + ", " + m.getUsername() + ", " + m.getPassword();
    }

    @PostMapping("/test/post")
    public String postTest(@RequestBody Member m) {
        // member 객체를 활용하여 로직 수행
        return "post 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }

    @PutMapping("/test/put")
    public String putTest(@RequestBody Member m) {
        return "put 요청" + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }

    @DeleteMapping ("/test/delete")
    public String deleteTest() {
        return "delete 요청";
    }
}
