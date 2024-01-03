package com.todo.todolist.config;

import com.todo.todolist.exception.UserAlreadyExistsException;
import com.todo.todolist.exception.UserNotFoundException;
import com.todo.todolist.models.UserInfo;
import com.todo.todolist.repository.UserInfoRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.OptionalInt;
//import org.springframework.context.annotation.Bean;

@Service
public class UserInfoDetailsService implements UserDetailsService{

    @Autowired
    private UserInfoRepository userInfoRepository;



    public UserInfoDetailsService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

//    public Optional<UserInfo> addUser(UserInfo userInfo) throws UserAlreadyExistsException {
//        Optional<UserInfo> user = userInfoRepository.findByEmail(userInfo.getEmail());
//        if(user.isPresent()) {
//            throw new UserAlreadyExistsException("User Exists");
//        }
//        else {
//            userInfoRepository.save(userInfo);
//            return userInfo;
//        }
//    }

    public UserInfo addUser(UserInfo userInfo) throws UserAlreadyExistsException {
        Optional<UserInfo> existingUser = userInfoRepository.findByEmail(userInfo.getEmail());
        Optional<UserInfo> existingUserName = userInfoRepository.findByName(userInfo.getName());
        if(existingUserName.isPresent()) {
            throw new UserAlreadyExistsException("UserName Already Exists");
        }
        else if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        else {
            // Save the user and return the saved user
            UserInfo savedUser = userInfoRepository.save(userInfo);
            return savedUser;
        }
    }

    public Optional<UserInfo> checkUserCredentialsHelp(String email, String password) {
        return userInfoRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));
    }

    public UserInfo checkUserCredentials  (String email, String password) {
        Optional<UserInfo> user = checkUserCredentialsHelp(email, password);
        if(user.isPresent()) {
            UserInfo userinfo = user.get();
            return userinfo;
            }
        else {
            throw new UserNotFoundException("User Not Found");
        }
    }


    public String getIdByUserName(String name){
        Optional<UserInfo> user = userInfoRepository.findByName(name);
        if(user.isPresent()) {
            UserInfo userIfPresent = user.get();
            return userIfPresent.getId();
        }
        else{
            throw new UserNotFoundException("User Name Not Found");
        }


    }
//    public String getId() {
//        return userInfoRepository.findByEmail()
//    }
}


//email - user ka (srajangarg8272@gmail.com)
//findAll(email == email(user)) - name nikalna hai






