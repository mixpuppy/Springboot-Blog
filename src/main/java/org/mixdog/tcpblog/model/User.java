package org.mixdog.tcpblog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에 연결된 DB의 넘버링 전략 따라감
    private int id; //시퀀스(오라클), auto_increment로 넘버링 (MySQL)
    @Column(nullable = false, length = 30)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 50)
    private String email;
    @CreationTimestamp // 시간 자동 입력
    private Timestamp createDate;
    @ColumnDefault("'user'")  // 기본값 설정 user를 기본값으로 설정한다
    private String role;
    //admin, user, manager... 정확히는 Enum을 사용하는것이 좋음. 어떤 데이터의 도메인(범위)을 만들 수 있다.
    //role 이 string 이므로 adminn 과 같은 오타를 낼 수 있다. Enum을 사용하면 방지 가능

}
