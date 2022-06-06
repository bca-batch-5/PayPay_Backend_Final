package com.paypay.service;


import com.paypay.dto.Request.ForgetPassRequest;
import com.paypay.dto.Request.LoginRequest;
import com.paypay.dto.Request.RegisterRequest;
import com.paypay.dto.Response.Response;


public interface UserService {
    public Response register(RegisterRequest registerRequest) throws Exception;
    public Response login(LoginRequest loginRequest) throws Exception;
    public Response forgetPass(ForgetPassRequest forgetPassRequest) throws Exception;
}
