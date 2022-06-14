package com.paypay.service.impl;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypay.constant.VariableConstant;
import com.paypay.dto.Response.Response;
import com.paypay.dto.Response.UserDetailResponse;
import com.paypay.model.User;
import com.paypay.model.UserDetail;
import com.paypay.repository.UserDetailRepo;
import com.paypay.repository.UserRepo;
import com.paypay.service.UserDetailDataService;

@Service
public class UserDetailDataImpl implements UserDetailDataService{
    private Response response;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserDetailRepo userDetailRepo;

    @Autowired
    private ModelMapper map;

    @Autowired
    private VariableConstant constant;

    @Override
    public Response getUserByEmail(String email) throws Exception {
        User user = userRepo.findByEmail(email);
        UserDetail userDetail = userDetailRepo.findByUser(user);
        UserDetailResponse res = map.map(userDetail, UserDetailResponse.class);
        response = new Response(constant.getSTATUS_OK(), "Data didapatkan", res); 
        return response;
    }
    
}
