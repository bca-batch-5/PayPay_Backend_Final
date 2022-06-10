package com.paypay.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paypay.Exception.BadRequestException;
import com.paypay.dto.Request.ForgetPassRequest;
import com.paypay.dto.Request.LoginRequest;
import com.paypay.dto.Request.NewPassRequest;
import com.paypay.dto.Request.RegisterRequest;
import com.paypay.model.User;

@Service
public class UserValidation {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(User emailDb, RegisterRequest userReq) throws Exception {
        if (emailDb != null) {
            throw new BadRequestException("Email sudah terdaftar");
        }
        if (!userReq.getEmail().contains("@")|| userReq.getEmail() == null) {
            throw new BadRequestException("Format email tidak tersedia");
        }
        if (!userReq.getEmail().contains(".com") || userReq.getEmail() == null) {
            throw new BadRequestException("Format email tidak tersedia");
        }
        if(userReq.getPassword().length() < 8 || userReq.getPassword() == null){
            throw new BadRequestException("Password harus lebih dari 8 word atau lebih");
        }
        
    }

    public void validationEmail(RegisterRequest userReq) throws Exception{
        
    }

    public void loginUser(User emailDb, LoginRequest loginRequest) throws Exception {
        if (emailDb == null) {
            throw new BadRequestException("Email tidak terdaftar");
        }
        if (!emailDb.getEmail().contains("@")) {
            throw new BadRequestException("Format email tidak tersedia");
        }
        if (!emailDb.getEmail().contains(".com")) {
            throw new BadRequestException("Format email tidak tersedia");
        }
       
    }

    public void checkingUserByEmail(User user) throws Exception {
        if (user == null) {
            throw new BadRequestException("Email tidak terdaftar");
        }
    }

    public void forgetPass(NewPassRequest forgetPassRequest) throws Exception {
        // if(userDb == null){
        //     throw new BadRequestException("Email Tidak terdaftar");
        // }
        if (forgetPassRequest.getNewPassword() != null && forgetPassRequest.getSameNewPassword() != null) {
            if (!forgetPassRequest.getNewPassword().equals(forgetPassRequest.getSameNewPassword())) {
                throw new BadRequestException("Password tidak sesuai");
            }
        }
    }

}
