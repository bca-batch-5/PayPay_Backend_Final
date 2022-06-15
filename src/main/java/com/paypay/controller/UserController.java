package com.paypay.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paypay.dto.Request.ChangePassRequest;
import com.paypay.dto.Request.CreatePinRequest;
import com.paypay.dto.Request.ForgetPassRequest;
import com.paypay.dto.Request.LoginRequest;
import com.paypay.dto.Request.NewPassRequest;
import com.paypay.dto.Request.RegisterRequest;
import com.paypay.dto.Response.Response;
import com.paypay.service.UserDetailDataService;
import com.paypay.service.UserService;

@RestController
@RequestMapping("/paypay")
public class UserController {
    private Response response;
   
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailDataService userDetailDataService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) throws Exception{
        response = userService.register(registerRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/create-pin")
    public ResponseEntity<?> createPin(@Valid @RequestBody CreatePinRequest createPinRequest) throws Exception{
        response = userService.createPin(createPinRequest) ;
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@AuthenticationPrincipal @Valid @RequestBody LoginRequest loginRequest) throws Exception{
        response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/forget-pass")
    public ResponseEntity<?> forgetPass(@Valid @RequestBody ForgetPassRequest forgetPassRequest) throws Exception{
        response = userService.forgetPass(forgetPassRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/forget-pass/new-pass")
    public ResponseEntity<?> inputNewPass(@RequestBody NewPassRequest newPassRequest) throws Exception{
        response = userService.inputNewPass(newPassRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable(value = "email") String email) throws Exception{
        response = userDetailDataService.getUserByEmail(email);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @PutMapping("/change-pass")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassRequest changePassRequest)throws Exception{
        response = userService.changePassword(changePassRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
