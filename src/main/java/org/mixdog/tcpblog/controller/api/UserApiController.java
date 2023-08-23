package org.mixdog.tcpblog.controller.api;

import jakarta.servlet.http.HttpSession;
import org.mixdog.tcpblog.dto.ResponseDto;
import org.mixdog.tcpblog.model.RoleType;
import org.mixdog.tcpblog.model.User;
import org.mixdog.tcpblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Console;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user")
    public ResponseDto<Integer> save(@RequestBody User user) {
    // json 데이터를 받기위해선 @RequestBody 어노테이션 필요
        System.out.println("UserApiController : save 호출됨");
        user.setRole(RoleType.USER);
        userService.회원가입(user); // 1이면 회원가입 성공, -1 실패
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
        // 자바 오브젝트를 json 으로 변환해서 리턴 (jackson)
    }

    @PostMapping("/api/user/login")
    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
        System.out.println("UserApiController : login 호출됨");
        User principal = userService.로그인(user); // principal (접근주체)
        System.out.println(principal);
        if(principal != null) {
            session.setAttribute("principal", principal);
//            System.out.println(principal);
            return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
        } else {
            System.out.println(principal);
            return new ResponseDto<Integer>(HttpStatus.UNAUTHORIZED.value(), -1);
        }

    }
}
