package org.mixdog.tcpblog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // 데이터를 반환하는것이 아닌 파일 반환이므로 rest 를 쓰지 않음. 경로 및 파일 리턴필요
public class TestController {
    @GetMapping("/hello")
    public String hello() {
        return "/hello.html";
    }
}
