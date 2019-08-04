package com.sanjay.networking.interceptors;


import com.sanjay.networking.persistence.LoginManager;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthHeaderInterceptor implements Interceptor {
    private final LoginManager loginManager;

    public AuthHeaderInterceptor(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        String token = loginManager.getToken();

        if (token == null) {
            return chain.proceed(request);
        }

        if (!request.url().encodedPath().endsWith("authenticate")) {
            request = request.newBuilder()
                    .addHeader("Authorization", "Token " + token)
                    .build();
        }

        return chain.proceed(request);
    }
}
