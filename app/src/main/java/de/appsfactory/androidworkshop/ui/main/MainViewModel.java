package de.appsfactory.androidworkshop.ui.main;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.appsfactory.androidworkshop.domain.Post;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private MutableLiveData<List<Post>> posts = new MutableLiveData<>();

    public MainViewModel() {
        loadPosts();
    }

    private void loadPosts() {
        // TODO: 2019-06-30 laden der Posts von der API mit Retrofit
        new Handler().postDelayed(() -> {
            List<Post> posts = new ArrayList<>();
            posts.add(new Post("1", "1", "Post 1", "Body 1"));
            posts.add(new Post("2", "1", "Post 1", "Body 1"));
            loading.postValue(false);
            this.posts.postValue(posts);
        }, 1000);
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    LiveData<List<Post>> getPosts() {
        return posts;
    }

}
