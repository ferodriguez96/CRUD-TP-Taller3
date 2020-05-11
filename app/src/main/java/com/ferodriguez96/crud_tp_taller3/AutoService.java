package com.ferodriguez96.crud_tp_taller3;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface AutoService {

    String CREATE_CAR ="app/api/create";
    String RETRIEVE_ALL= "app/api/read";
    String RETRIEVE_BY_ID = "app/api/read/{id}";
    String UPDATE_CAR = "app/api/update/{id}";
    String DELETE_CAR = "app/api/delete/{id}";


    @POST(CREATE_CAR)
    Call<Auto> createAuto(@Body Auto auto);

    @GET(RETRIEVE_ALL)
    Call<List<Auto>> getAutos();

    @GET(RETRIEVE_BY_ID)
    Call<Auto> getAutoById(@Path("id") String id);

    @PUT(UPDATE_CAR)
    Call<Void> updateAuto(@Path("id") String id, @Body Auto auto);

    @DELETE(DELETE_CAR)
    Call<Void> deleteAutoById(@Path("id") String id);
}
