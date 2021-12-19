package com.example.demo_woojin.service;


import com.example.demo_woojin.model.TodoEntity;
import com.example.demo_woojin.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TodoService {


    @Autowired
    private TodoRepository repository;

    public String testService(){
        //   return "test service";


        //Create TodoEntity
        TodoEntity entity = TodoEntity.builder().title("My First Todo Item").build();

        //Todoentity 저장
        repository.save(entity);

        //TodoEntity 검색
        TodoEntity savedEntity=repository.findById(entity.getId()).get();

        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity){

        //validation
        validate(entity);
        repository.save(entity);

        log.info("Entity wj Id: {} is saved", entity.getId());
        return repository.findByUserId(entity.getUserId());
    }
    private void validate(final TodoEntity entity){
        if(entity ==null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("entity cannot be null");
        }
        if(entity.getUserId()==null){
            log.warn("UNKNOWN user");
            throw new RuntimeException("Unknown user");
        }
    }

    public List<TodoEntity> retrieve(final String userId){
        log.info("여기 실행 된건가 ?");
        return repository.findByUserId(userId);
    }

    //업데이트 구현
    public List<TodoEntity> update(final TodoEntity entity){

        //<1> 저장할 entity 가 유효한지 확인한다.
        validate(entity);

        //<2> 넘겨받은 엔티티id를 이용해서 TodoEntity를 가져온다 . 존재하지 않는 아이디는 업뎃을 할 수 없다.
        //java.util.Optional 은 발생하는 예외를 여러 복잡한 코드 없이로 처리해준다.
        final Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo ->{
            //<3> 반환된 TodoEntity가 존재하면 값을 새 entity 값으로 덮어 씌운다.
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            //<4> 인터페이스에 set한 새로운 값을 저장한다.
            repository.save(todo);
        });

        //Retrieve를 이용해 사용자의 모든 Todo 리스트를 리턴한다
        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> delete(final TodoEntity entity){
        //저장할 Entity가 유효한지 확인한다.
        validate(entity);

        try{
            //<2> entity를 삭제한다.
            repository.delete(entity);
        }catch(Exception e){
            //<3> exception 발생시 id와 exception을 로깅한다.
            log.error("Error deleting entity ", entity.getId(), e);
            //<4> 컨트롤러로 Exception을 보낸다.
            throw new RuntimeException("Error deleting entity "+entity.getId());
        }

        return retrieve(entity.getUserId());
    }



}
