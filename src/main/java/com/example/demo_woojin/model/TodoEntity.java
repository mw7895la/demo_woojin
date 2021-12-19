package com.example.demo_woojin.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class TodoEntity {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid")
    private String id;  //이 오브젝트의 아이디
    private String userId;  //오브젝트를 생성한 사용자의 아이디
    private String title;   //Todo 타이틀 ( 예 : 운동하기)
    private boolean done; //true  Todo를 완료한 경우 체크
}
