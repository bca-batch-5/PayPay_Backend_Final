package com.paypay.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paypay.Exception.BadRequestException;
import com.paypay.dto.Request.ForgetPassRequest;
import com.paypay.dto.Request.LoginRequest;
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
        if (!userReq.getEmail().contains("@")) {
            throw new BadRequestException("Format email tidak tersedia");
        }
        if (!userReq.getEmail().contains(".com")) {
            throw new BadRequestException("Format email tidak tersedia");
        }
    }

    public void loginUser(User emailDb, LoginRequest loginRequest) throws Exception {
        loginRequest.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
        String passEncoded = loginRequest.getPassword();
        if (emailDb == null) {
            throw new BadRequestException("Email tidak terdaftar");
        }
        if (!emailDb.getEmail().contains("@")) {
            throw new BadRequestException("Format email tidak tersedia");
        }
        if (!emailDb.getEmail().contains(".com")) {
            throw new BadRequestException("Format email tidak tersedia");
        }
        if (!loginRequest.getPassword().equals(emailDb.getPassword())) {
            throw new BadRequestException("Password tidak sesuai");
        }
    }

    public void checkingUserByEmail(User user) throws Exception {
        if (user == null) {
            throw new BadRequestException("Email tidak terdaftar");
        }
    }

    public void forgetPass(ForgetPassRequest forgetPassRequest) throws Exception {
        if (!forgetPassRequest.getEmail().contains("@")) {
            throw new BadRequestException("Format email tidak tersedia");
        }
        if (!forgetPassRequest.getEmail().contains(".com")) {
            throw new BadRequestException("Format email tidak tersedia");
        }
        if (forgetPassRequest.getNewPassword() != null && forgetPassRequest.getSameNewPassword() != null) {
            if (!forgetPassRequest.getNewPassword().equals(forgetPassRequest.getSameNewPassword())) {
                throw new BadRequestException("Password tidak sesuai");
            }
        }
    }

}
