package com.todo.todolist;

//import lombok.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import com.todo.todolist.security.JwtTokenProvider;
import com.todo.todolist.models.UserInfo;

@SpringBootApplication
public class TodolistApplication {

	public static void main(String[] args) {


		SpringApplication.run(TodolistApplication.class, args);

		JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

		UserInfo userInfo = new UserInfo();
		userInfo.setName("exampleUser");

		String token = jwtTokenProvider.generateToken(userInfo.getName());
		System.out.println("Generated Token: " + token);
	}




}
