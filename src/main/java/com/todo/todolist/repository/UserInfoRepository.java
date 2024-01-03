package com.todo.todolist.repository;

import com.todo.todolist.models.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends MongoRepository<UserInfo, String> {

    Optional<UserInfo> findByEmail(String email);

    Optional<UserInfo> findByName(String name);
}
