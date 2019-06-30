package de.appsfactory.androidworkshop.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import de.appsfactory.androidworkshop.R;
import de.appsfactory.androidworkshop.domain.Post;

public class MainActivity extends AppCompatActivity {

    private ProgressBar loading = null;

    private RecyclerView postList = null;

    private MainViewModel mainViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        observeLoading();
        observePosts();
        observeError();
    }

    private void initViews() {
        loading = findViewById(R.id.pb_loading);
        postList = findViewById(R.id.rv_posts);
    }

    private void observeLoading() {
        if (mainViewModel != null) {
            mainViewModel.getLoading().observe(this, this::updateLoading);
        }
    }

    private void updateLoading(Boolean isLoading) {
        if (loading != null) {
            loading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        }
    }

    private void observePosts() {
        if (mainViewModel != null) {
            mainViewModel.getPosts().observe(this, this::updatePosts);
        }
    }

    private void updatePosts(List<Post> loadedPosts) {
        if (postList != null) {
            postList.setVisibility(View.VISIBLE);
            RecyclerView.Adapter adapter = postList.getAdapter();
            if (adapter == null) {
                adapter = new PostAdapter();
                postList.setAdapter(adapter);
                postList.setLayoutManager(new LinearLayoutManager(this));
            }
            ((PostAdapter)adapter).updatePosts(loadedPosts);
        }
    }

    private void observeError() {
        if (mainViewModel != null) {
            mainViewModel.getError().observe(this, this::showError);
        }
    }

    private void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }


}
