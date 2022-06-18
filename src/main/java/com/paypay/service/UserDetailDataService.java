package com.paypay.service;

import com.paypay.dto.Request.InputNoTelpRequest;
import com.paypay.dto.Request.TopUpRequest;
import com.paypay.dto.Response.Response;

public interface UserDetailDataService {
    Response getUserByEmail(String email)throws Exception;
    Response inputNoTelp(String email, InputNoTelpRequest inputNoTelpRequest) throws Exception;
    Response deleteNoTelp(String email) throws Exception;
    Response topUpPayment(String email, TopUpRequest topUpRequest) throws Exception;
}
