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
    private Boolean existingEmail = true;
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
        if(user != null){
            user.setPin(createPinRequest.getPin());
            userRepo.save(user);
            res = mapper.map(user, UserResponse.class);
        }else{
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
        if (existingEmail == true) {
            validation.checkingUserByEmail(userDb);
        }
        if (forgetPassRequest.getEmail() == null) {
            User emailCurrent = data.get(1);
            forgetPassRequest.setEmail(emailCurrent.getEmail());
            userDb = mapper.map(emailCurrent, User.class);
        }
        User user = new User();
        if (userDb != null) {
            data.put(1, userDb);
        }

        user = mapper.map(userDb, User.class);
        validation.forgetPass(forgetPassRequest);
        if (forgetPassRequest.getSameNewPassword() != null) {
            user.setPassword(passwordEncoder.encode(forgetPassRequest.getSameNewPassword()));
            userRepo.save(user);
        }
        UserResponse res = mapper.map(user, UserResponse.class);

        existingEmail = false;
        response = new Response(varconstant.getSTATUS_OK(), "Success", res);
        return response;
    }

    
}
