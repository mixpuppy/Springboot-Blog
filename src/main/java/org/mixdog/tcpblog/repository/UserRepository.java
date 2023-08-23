package org.mixdog.tcpblog.repository;

import org.mixdog.tcpblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// DAO
// 자동으로 bean 등록이 된다
// @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {
    // 해당 JpaRepository 는 User 테이블을 관리하는 리파지토리
    // 그리고 User 테이블의 PK는 Integer임

    // JPA 네이밍 쿼리
    User findByUsernameAndPassword(String username, String password);

    // 네이티브 쿼리 작성방법
//    @Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//    User login(String username, String password);
}
