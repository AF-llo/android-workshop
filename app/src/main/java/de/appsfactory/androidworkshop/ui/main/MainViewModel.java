package de.appsfactory.androidworkshop.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.appsfactory.androidworkshop.domain.Post;
import de.appsfactory.androidworkshop.network.Api;
import de.appsfactory.androidworkshop.network.ApiPost;
import de.appsfactory.androidworkshop.network.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private MutableLiveData<List<Post>> posts = new MutableLiveData<>();

    private MutableLiveData<String> error = new MutableLiveData<>();

    private Api api = ApiProvider.createApi();

    public MainViewModel() {
        loadPosts();
    }

    private void loadPosts() {
        Call<List<ApiPost>> postCall = api.loadPosts(Api.BEARER_TOKEN);
        postCall.enqueue(new Callback<List<ApiPost>>() {
            @Override
            public void onResponse(@NotNull Call<List<ApiPost>> call, @NotNull Response<List<ApiPost>> response) {
                loading.postValue(false);
                handleResponse(response);
            }

            @Override
            public void onFailure(@NotNull Call<List<ApiPost>> call, @NotNull Throwable t) {
                loading.postValue(false);
                error.postValue(t.getMessage());
            }
        });
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    LiveData<List<Post>> getPosts() {
        return posts;
    }

    LiveData<String> getError() {
        return error;
    }

    private void handleResponse(Response<List<ApiPost>> response) {
        if (response.isSuccessful() && response.body() != null) {
            posts.postValue(mapApiPosts(response.body()));
        } else {
            error.postValue("HTTP-Code: " + response.code());
        }
    }

    private List<Post> mapApiPosts(List<ApiPost> apiPosts) {
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < apiPosts.size(); i++) {
            ApiPost apiPost = apiPosts.get(i);
            posts.add(new Post(apiPost.getId().toString(), apiPost.getAuthor().toString(), apiPost.getTitle(), apiPost.getBody()));
        }
        return posts;
    }

}
