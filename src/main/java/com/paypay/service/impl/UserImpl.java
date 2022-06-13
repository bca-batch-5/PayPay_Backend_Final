package com.paypay.service.impl;

import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paypay.Exception.BadRequestException;
import com.paypay.config.JwtUtil;
import com.paypay.constant.VariableConstant;
import com.paypay.dto.Request.CreatePinRequest;
import com.paypay.dto.Request.ForgetPassRequest;
import com.paypay.dto.Request.LoginRequest;
import com.paypay.dto.Request.NewPassRequest;
import com.paypay.dto.Request.RegisterRequest;
import com.paypay.dto.Response.Response;
import com.paypay.dto.Response.UserResponse;
import com.paypay.model.User;
import com.paypay.repository.UserRepo;
import com.paypay.service.UserService;
import com.paypay.validation.UserValidation;


@Service
public class UserImpl implements UserService {
    HashMap<Integer, User> data = new HashMap<Integer, User>();
    private Response response;
    @Autowired
    private VariableConstant varconstant;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserValidation validation;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Response register(RegisterRequest registerRequest) throws Exception {
        User emailDb = new User();
        User user = new User();
        emailDb = userRepo.findByEmail(registerRequest.getEmail());
        validation.createUser(emailDb, registerRequest);
        user = mapper.map(registerRequest, User.class);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepo.save(user);
        data.put(2, user);
        UserResponse res = mapper.map(user, UserResponse.class);
        response = new Response(varconstant.getSTATUS_CREATED(), "User Created", res);
        return response;
    }

    @Override
    public Response createPin(CreatePinRequest createPinRequest) throws Exception {
        User user = data.get(2);
        UserResponse res = new UserResponse();
        if (user != null) {
            if (createPinRequest.getPin() == null) {
                throw new BadRequestException("Pin tidak ada");
            }
            if (createPinRequest.getPin().size() < 6) {
                throw new BadRequestException("Pin harus di isi semua");
            }
            String temp = "";
            for (int i = 0; i < createPinRequest.getPin().size(); i++) {
                temp += createPinRequest.getPin().get(i);
            }
            Integer pin = Integer.parseInt(temp);
            user.setPin(pin);
            userRepo.save(user);
            res = mapper.map(user, UserResponse.class);
        } else {
            throw new BadRequestException("Akun tidak ada");
        }
        response = new Response(varconstant.getSTATUS_CREATED(), "Pin Created", res);
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) throws Exception {
        data = new HashMap<>();
        User emailDb = userRepo.findByEmail(loginRequest.getEmail());
        validation.loginUser(emailDb, loginRequest);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        Response token = jwtUtil.generateJWT(authentication);
        response = token;
        return response;
    }

    @Override
    public Response forgetPass(ForgetPassRequest forgetPassRequest) throws Exception {
        User userDb = userRepo.findByEmail(forgetPassRequest.getEmail());
        validation.checkingUserByEmail(userDb);
        data.put(1, userDb);
        response = new Response(varconstant.getSTATUS_OK(), "Success", null);
        return response;
    }

    @Override
    public Response inputNewPass(NewPassRequest newPassRequest) throws Exception {
        if (data.get(1) == null) {
            throw new BadRequestException("Error Email belum di masukan");
        } else {
            validation.forgetPass(newPassRequest);
            User currentUser = data.get(1);
            currentUser.setPassword(passwordEncoder.encode(newPassRequest.getSameNewPassword()));
            userRepo.save(currentUser);
            response = new Response(varconstant.getSTATUS_OK(), "Password telah terganti", currentUser);
        }
        return response;
    }

}
