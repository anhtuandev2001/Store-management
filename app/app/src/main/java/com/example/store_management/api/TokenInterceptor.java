package com.example.store_management.api;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class TokenInterceptor implements Interceptor {
    private String token;

    public TokenInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request.Builder requestBuilder = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + token);

        Request request = requestBuilder.build();

        try {
            return chain.proceed(request);
        } catch (IOException e) {
            // Xử lý lỗi IOException ở đây nếu cần
            e.printStackTrace();
            throw e; // Rethrow exception để thông báo lỗi lên cho phần gọi
        }
    }
}
