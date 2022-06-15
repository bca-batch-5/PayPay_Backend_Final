package com.paypay.service;

import com.paypay.dto.Response.Response;

public interface UserDetailDataService {
    Response getUserByEmail(String email)throws Exception;
}
