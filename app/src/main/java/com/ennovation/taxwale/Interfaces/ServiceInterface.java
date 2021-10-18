package com.ennovation.taxwale.Interfaces;

import com.ennovation.taxwale.Model.SignUpResponse;
import com.ennovation.taxwale.Model.loginResponse;
import com.ennovation.taxwale.Model.otpverifymodel;
import com.ennovation.taxwale.Utils.Constants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceInterface {

    @POST(Constants.SIGNUP)
    Call<SignUpResponse> doSignup(@Body HashMap<String, String> map);

    @POST(Constants.LOGIN)
    Call<loginResponse> doLogin(@Body HashMap<String, String> map);


    @POST(Constants.OTP)
    Call<otpverifymodel> verifyotp(@Body HashMap<String, String> map);

}
