package org.mixdog.tcpblog.service;

import org.mixdog.tcpblog.model.User;
import org.mixdog.tcpblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional // 오류 발생시 롤백
    public void 회원가입(User user) {
        userRepository.save(user);
//        try {
//            userRepository.save(user);
//            return 1;
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("UserService : 회원가입() : " + e.getMessage());
//        }
//        return -1;
    }

    @Transactional(readOnly = true) // Select 할때 트랜잭션 시작, 서비스 종료 시 트랜젝션 종료 (정합성)
    public User 로그인(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
