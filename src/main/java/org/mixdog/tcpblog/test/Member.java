package org.mixdog.tcpblog.test;

import lombok.*;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class Member {

    private int id;
    private String username;
    private String password;
    private String email;

    @Builder
    public Member(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
