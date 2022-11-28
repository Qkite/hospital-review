package com.example.hospitalreview.domain;

import com.example.hospitalreview.domain.dto.UserJoinResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
public class Response<T> {

    private String resultCode;
    private T result;

    private static Response<Void> error(String resultCode){

        return new Response(resultCode, null);


    }


    public static <T> Response<T> success(T result) {

        return new Response("SUCCESS", result);
    }
}
