package de.appsfactory.androidworkshop.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiProvider {

    private ApiProvider() {
    }

    public static Api createApi() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Api.BASE_URL)
                .client(new OkHttpClient.Builder()
                        .addInterceptor(interceptor).build())
                .build();
        return retrofit.create(Api.class);
    }
}
