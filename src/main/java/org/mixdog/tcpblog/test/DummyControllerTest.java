package org.mixdog.tcpblog.test;

import jakarta.transaction.Transactional;
import org.mixdog.tcpblog.model.RoleType;
import org.mixdog.tcpblog.model.User;
import org.mixdog.tcpblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 페이지 리턴이 아님
public class DummyControllerTest {

    //@Autowired 전에는 null값이다.
    //의존성 주입 // Autowired 로 UserRepository를 메모리에 띄워줌
    @Autowired //UserRepository 타입으로 스프링이 관리하고 있는 객체가 있다면 userRepository 로 넣어라
    private UserRepository userRepository;

    @PostMapping("/dummy/join")
    public String join(User user) {
        System.out.println("id : " +user.getId());
        System.out.println("username : " + user.getUsername());
        System.out.println("password : " + user.getPassword());
        System.out.println("email : " +user.getEmail());
        System.out.println("role : " + user.getRole());
        System.out.println("createDate : " + user.getCreateDate());
        user.setRole(RoleType.USER); //DynamicInsert 어노테이션 지우고 해당 기본값 설정
        userRepository.save(user);
        return "회원가입이 완료되었습니다";
    }

    //{id} 주소로 파라미터를 전달받을 수 있다
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        //메소드의 파라미터에서 처리해야함. 이를통해 id 에 매핑됨
        ///////////////////////////////////////////////////////////////////////////////////////////
        //1. Optional 객체 (컴파일오류)
//        User user = userRepository.findById(id);
        //CrudRepository 클래스를 보면 findById 리턴은 Optional 이다
        //그 이유는 찾는 값이 null일수 있고, 이를 참조하면 NPE 발생 가능성을 방지하기 위함

        //2. get() 메소드 (잘못된 방법)
//        User user = userRepository.findById(id).get();
        //별도 오류는 없으나, get() 으로 가져온다는건 null일리 없다 확신하고 뽑아오는것.

        //3. orElseGet(Supplier<T>)
//        User user = userRepository.findById(id).orElseGet(new Supplier<User>() { //익명객체 구현부
            // orElseGet 은 null이면 객체하나 만들어서 user 에 넣어줘
//            @Override
//            public User get() {
//                return new User(); //빈 user 를 리턴해준다
//            }
//        });

        //4. orElseThrow
//        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
//            @Override
//            public IllegalArgumentException get() {
//                return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
//            }
//        });
        // 4번의 람다식 적용
        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////

        //웹 브라우저는 html, js 는 이해하지만 자바 오브젝트는 이해 못하며 user 객체는 자바 오브젝트임
        //자바 오브젝트를 리턴하면 MessageConverter 가 Jackson 이라는 라이브러리 호출하여 json 변환하여 브라우저 줌.
        //이 messageConverter는 스프링부트꺼
        return user;
    }
    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }
    // 한 페이지당 2건의 데이터 리턴받아볼 예정 (페이징 처리)
    @GetMapping("dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<User> pagingUsers = userRepository.findAll(pageable);
        // 리스트로 바꿔주는 이유는 page 정보는 보일 필요 없기 때문
        // 이렇게 Page<> 에서 List<> 로 바꿔줌으로써 그 사이에 if(pagingUsers.isLast()){} 같은 처리 가능
        List<User> users = pagingUsers.getContent(); //getContent() 를 통해 리스트로 변경해준다
        return users;
    }

    @Transactional
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
        //json 데이터 받기위해 @RequestBody 필요
        System.out.println("id : " + id);
        System.out.println("password : " + requestUser.getPassword());
        System.out.println("email : " + requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("해당 id 를 찾을수 없습니다. 수정 실패");
        });   // 날짜나 role 이 null 되는것 방지 및 예외처리
        if(requestUser.getPassword()!=null)
        user.setPassword(requestUser.getPassword());
        if(requestUser.getEmail()!=null)
        user.setEmail(requestUser.getEmail());

        ////////////////////////////////////////////////////////////////////////////////////
        //1 save() 를 이용한 방법
//        userRepository.save(user);
//        return null;

        //2. @Transactional 통한 업데이트 (메소드에 붙임)
        // DB 다룰때 트랜잭션 적용. 오류발생 시 모든작업 원복 가능
        // 모든 작업이 성공해야만 최종적으로 데이터베이스 반영
        // save() 호출 없이 데이터가 update 됨
        return null;
    }
    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return "삭제에 실패하였습니다. 요청하신 "+ id + " id 는 DB 에 없습니다.";
        }
        return "삭제되었습니다. id : " +id;
    }
}
