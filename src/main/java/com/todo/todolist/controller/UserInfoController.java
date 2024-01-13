    package com.todo.todolist.controller;

    import com.todo.todolist.config.UserInfoDetailsService;
    import com.todo.todolist.exception.UserAlreadyExistsException;
    import com.todo.todolist.models.UserInfo;
    import com.todo.todolist.security.JwtTokenProvider;
    import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;


    @RestController
    @CrossOrigin("*")
    public class UserInfoController {

        @Autowired
        private UserInfoDetailsService userDetailsService;
    //    private JwtTokenProvider jwtTokenProvider;
        private Authentication authentication;

        @PostMapping("/add")
        public ResponseEntity<UserInfo> addNewUser(@RequestBody UserInfo userInfo) throws UserAlreadyExistsException {
            return ResponseEntity.ok(userDetailsService.addUser(userInfo));
        }

        @PostMapping("/login")
        public ResponseEntity<String> login(@RequestBody UserInfo userInfo) {
            UserInfo userDetails = userDetailsService.checkUserCredentials(userInfo.getEmail(), userInfo.getPassword());

            if (userDetails != null) {
                JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
                String token = jwtTokenProvider.generateToken(userDetails.getName());
                return ResponseEntity.ok(token);
            } else {
                // Handle authentication failure
                return ResponseEntity.status(401).body("Authentication failed");
            }
        }


    //    UserSignUp
    //    UserLogin


    }
