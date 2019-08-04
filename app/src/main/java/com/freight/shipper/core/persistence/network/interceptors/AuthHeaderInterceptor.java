package com.freight.shipper.core.persistence.network.interceptors;


import androidx.annotation.NonNull;
import com.freight.shipper.core.persistence.preference.LoginManager;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

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
        } else {
            request = request.newBuilder()
                    .addHeader("token", token)
                    .build();
        }

        return chain.proceed(request);
    }
}
