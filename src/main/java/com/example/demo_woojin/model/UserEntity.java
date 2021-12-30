package com.example.demo_woojin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})      //현재 테이블이름은 UserEntity다 ( 명시하지 않았음으로)
//따라서 Table 매핑을 UserEntity와 하는데 email이라는 컬럼과 매핑한다.
public class UserEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid")
    private String id;

    @Column(nullable=false)
    private String username;        //사용자의 이름

    @Column(nullable=false)
    private String email;   //사용자의 email, 아이디와 같은 기능을 하게된다.

    @Column(nullable=false)
    private String password;        //패스워드



}
