package com.ncx.dms.remote;

import com.ncx.dms.remote.dto.DealerDto;
import com.ncx.dms.remote.dto.McOrderDetailDto;
import com.ncx.dms.remote.dto.McSparePart;
import com.ncx.dms.remote.dto.MechanicDto;
import com.ncx.dms.remote.dto.ResultDto;
import com.ncx.dms.remote.dto.UserDto;
import com.ncx.dms.remote.dto.WarrantyRegistrationRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    String URL = "http://wini2020-001-site1.etempurl.com/";
    //String URL = "http://10.0.2.2:5000";

    @GET("api/account?")
    Call<ResultDto<UserDto>> login(@Query("userName") String userName, @Query("password") String password);

    @GET("api/dealer")
    Call<ResultDto<List<DealerDto>>> GetAllDealers();

    @GET("api/mechanic?")
    Call<ResultDto<List<MechanicDto>>> getMechanicByDealerCode(@Query("dealerCode") String dealerCode);

    @GET("api/warranty?")
    Call<ResultDto<McOrderDetailDto>> verifyFrameNo(@Query("frameNo") String frameNo);

    @GET("api/warranty/{dealerCode}")
    Call<ResultDto<List<String>>> getAvailableFrameNos(@Path("dealerCode") String dealerCode);

    @POST("api/warranty")
    Call<ResultDto> warrantyRegister(@Body WarrantyRegistrationRequest request);

    @GET("api/mccatalog")
    Call<ResultDto<List<McSparePart>>> getMcSpareParts();
}
