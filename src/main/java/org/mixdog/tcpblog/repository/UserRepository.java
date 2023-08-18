package org.mixdog.tcpblog.repository;

import org.mixdog.tcpblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// DAO
// 자동으로 bean 등록이 된다
// @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {
    // 해당 JpaRepository 는 User 테이블을 관리하는 리파지토리
    // 그리고 User 테이블의 PK는 Integer임
}
