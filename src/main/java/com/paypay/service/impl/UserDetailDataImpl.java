package com.paypay.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypay.constant.VariableConstant;
import com.paypay.dto.Request.InputNoTelpRequest;
import com.paypay.dto.Request.TopUpRequest;
import com.paypay.dto.Response.Response;
import com.paypay.dto.Response.UserDetailResponse;
import com.paypay.model.User;
import com.paypay.model.UserDetail;
import com.paypay.repository.UserDetailRepo;
import com.paypay.repository.UserRepo;
import com.paypay.service.UserDetailDataService;
import com.paypay.validation.TopUpValidation;
import com.paypay.validation.UserValidation;

@Service
public class UserDetailDataImpl implements UserDetailDataService{
    private Response response;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserDetailRepo userDetailRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private VariableConstant constant;

    @Autowired
    private UserValidation userValidation;

    @Autowired
    private TopUpValidation topUpValidation;

    @Override
    public Response getUserByEmail(String email) throws Exception {
        User user = userRepo.findByEmail(email);
        userValidation.checkingUserByEmail(user);
        UserDetail userDetail = userDetailRepo.findByUser(user);
        UserDetailResponse res = mapper.map(userDetail, UserDetailResponse.class);
        response = new Response(constant.getSTATUS_OK(), "Data didapatkan", res); 
        return response;
    }

    @Override
    public Response inputNoTelp(String email, InputNoTelpRequest inputNoTelpRequest) throws Exception {
        User user = userRepo.findByEmail(email);
        userValidation.checkingUserByEmail(user);
        UserDetail userDetail = userDetailRepo.findByUser(user);
        userDetail.setNoTelp(inputNoTelpRequest.getNoTelp());
        userDetailRepo.save(userDetail);
        response = new Response(constant.getSTATUS_CREATED(), "No Telpon Terbuat", userDetail);
        return response;
    }

    @Override
    public Response deleteNoTelp(String email) throws Exception {
        User user = userRepo.findByEmail(email);
        userValidation.checkingUserByEmail(user);
        UserDetail userDetail = userDetailRepo.findByUser(user);
        userDetail.setNoTelp(null);
        userDetailRepo.save(userDetail);
        response = new Response(constant.getSTATUS_OK(), "Nomor Telfon Terhapus", userDetail);
        return response;
    }

    @Override
    public Response topUpPayment(String email, TopUpRequest topUpRequest) throws Exception {
        User user = userRepo.findByEmail(email);
        userValidation.checkingUserByEmail(user);
        UserDetail userDetail = userDetailRepo.findByUser(user);
        topUpValidation.checkingNominal(topUpRequest.getNominal());
        userDetail.setSaldo(userDetail.getSaldo() + topUpRequest.getNominal());
        userDetailRepo.save(userDetail);
        UserDetailResponse res = mapper.map(userDetail, UserDetailResponse.class);
        response = new Response(constant.getSTATUS_OK(), "Top Up Berhasil", res);
        return response;
    }
    
}
