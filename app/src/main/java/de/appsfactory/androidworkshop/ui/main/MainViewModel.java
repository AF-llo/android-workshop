package de.appsfactory.androidworkshop.ui.main;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Entity;

import java.util.ArrayList;
import java.util.List;

import de.appsfactory.androidworkshop.app.WorkshopApplication;
import de.appsfactory.androidworkshop.domain.Post;
import de.appsfactory.androidworkshop.network.Api;
import de.appsfactory.androidworkshop.network.ApiPost;
import de.appsfactory.androidworkshop.network.ApiProvider;
import de.appsfactory.androidworkshop.network.NetworkUtil;
import de.appsfactory.androidworkshop.persistance.PostDao;
import de.appsfactory.androidworkshop.persistance.PostEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private MutableLiveData<List<Post>> posts = new MutableLiveData<>();

    private MutableLiveData<String> error = new MutableLiveData<>();

    private Api api = ApiProvider.createApi();

    private PostDao postDao = WorkshopApplication.getAppDatabase().postDao();

    public MainViewModel(Application app) {
        super(app);
        loadPosts();
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

    private void loadPosts() {
        if (NetworkUtil.isInternetAvailable(getApplication())) {
            loadFromApi();
        } else {
            loadFromDb();
        }
    }

    private void loadFromApi() {
        loading.postValue(true);
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

    private void loadFromDb() {
        loading.postValue(true);
        new LoadLocalPostTask(this, postDao).execute();
    }

    private void handleResponse(Response<List<ApiPost>> response) {
        if (response.isSuccessful() && response.body() != null) {
            List<Post> mappedPosts = mapApiPosts(response.body());
            posts.postValue(mappedPosts);
            updateLocalPosts(mappedPosts);
        } else {
            error.postValue("HTTP-Code: " + response.code());
        }
    }

    private void updateLocalPosts(List<Post> posts) {
        new UpdateLocalPostsTask(postDao).execute(posts.toArray(new Post[0]));
    }

    private List<Post> mapApiPosts(List<ApiPost> apiPosts) {
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < apiPosts.size(); i++) {
            ApiPost apiPost = apiPosts.get(i);
            posts.add(new Post(apiPost.getId().toString(), apiPost.getAuthor().toString(), apiPost.getTitle(), apiPost.getBody()));
        }
        return posts;
    }


    static class LoadLocalPostTask extends AsyncTask<Void, Void, List<PostEntity>> {

        private MainViewModel mainViewModel;

        private PostDao postDao;

        LoadLocalPostTask(MainViewModel mainViewModel, PostDao postDao) {
            this.mainViewModel = mainViewModel;
            this.postDao = postDao;
        }

        @Override
        protected List<PostEntity> doInBackground(Void... voids) {
            return postDao.getAll();
        }

        @Override
        protected void onPostExecute(List<PostEntity> postEntities) {
            mainViewModel.posts.postValue(mapDbPosts(postEntities));
            mainViewModel.loading.postValue(false);
        }

        private List<Post> mapDbPosts(List<PostEntity> localPosts) {
            List<Post> posts = new ArrayList<>();
            for (int i = 0; i < localPosts.size(); i++) {
                PostEntity postEntity = localPosts.get(i);
                posts.add(new Post(postEntity.id, postEntity.userId, postEntity.title, postEntity.body));
            }
            return posts;
        }
    }

    static class UpdateLocalPostsTask extends AsyncTask<Post, Void, Void> {

        private PostDao postDao;

        UpdateLocalPostsTask(PostDao postDao) {
            this.postDao = postDao;
        }

        @Override
        protected final Void doInBackground(Post... postItems) {
            if (postItems.length > 0) {
                List<PostEntity> entities = new ArrayList<>();
                for (int i = 0; i < postItems.length; i++) {
                    Post post = postItems[i];
                    PostEntity postEntity = new PostEntity();
                    postEntity.id = post.getId();
                    postEntity.userId = post.getUserId();
                    postEntity.title = post.getTitle();
                    postEntity.body = post.getBody();
                    entities.add(postEntity);
                }
                postDao.insertAll(entities);
            }
            return null;
        }
    }

}
