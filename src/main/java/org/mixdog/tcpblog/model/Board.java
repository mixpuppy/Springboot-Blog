package org.mixdog.tcpblog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int id;
    @Column(nullable = false, length = 100)
    private String title;
    @Lob // 대용량 데이터
    private String content;  // 섬머노트 라이브러리 <html> 태그가 섞여서 디자인됨
    @ColumnDefault("0") // int 니까 '' 없이 숫자만 입력.
    private int count; // 조회수
    //private int userId; // ORM에서는 이 방식을 사용않음.
    @ManyToOne // 연관관계를 맺어줘야 함. Many=Board, User=One
    @JoinColumn(name="userId")
    // 실제로 데이터베이스에 만들어질때는 userId라는 이름으로 만들어짐
    // 즉 테이블에 userId 라는 필드값이 들어가게 됨
    private User user;
    // DB는 오브젝트 저장 불가. FK, 자바는 오브젝트 저장 가능
    // 자바 프로그램에서 데이터베이스의 자료형에 맞춰 테이블을 만들게 됨.
    // ORM을 사용하면 User user 와 같이 객체를 그대로 저장 가능
    // 그런데 이렇게 만들면 테이블이 어떻게 인식하지?
    // User 객체니 User.java 참조하고 Board.java 에서 FK로 만들어 테이블에 저장
    // 데이터베이스에 오브젝트를 저장할 수 없기 때문
    @CreationTimestamp
    private Timestamp createDate;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    // fetch 전략이 EAGER 인 이유 : 기본은 LAZY 지연로딩
    // 덧글 펼치기 버튼으로 숨겨져 있다면 LAZY 사용 됨. 바로 보여질땐 EAGER

    // 하나의 게시물에 여러 댓글이 달림
    // mappedBy : 연관관계의 주인이 아님 (나는 FK가 아님) DB에 컬럼 만들지 말아라!
    // 나는 그냥 Board를 select 할때 join을 통해 값을 얻기 위해 필요한 것.
    private List<Reply> reply;
    // 즉 얘는 테이블에 성생되는 FK가 아니다.
}
