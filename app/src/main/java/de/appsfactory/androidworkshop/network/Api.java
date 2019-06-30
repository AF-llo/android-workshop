package de.appsfactory.androidworkshop.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Api {

    String BASE_URL = "http://:8000/"; // TODO: 2019-06-30 IP-Adresse vom Django-Server anpassen

    String PATH_API = "api/";

    String PATH_POSTS = "posts/";

    String METHOD_POSTS = PATH_API + PATH_POSTS;

    String HEADER_AUTHORIZATION = "Authorization";

    String TOKEN = ""; // todo: 2019-06-30 mit gültigem token ersetzen

    String BEARER_TOKEN = "Bearer " + TOKEN;

    @GET(METHOD_POSTS)
    Call<List<ApiPost>> loadPosts(@Header(HEADER_AUTHORIZATION) String bearerToken);

}
