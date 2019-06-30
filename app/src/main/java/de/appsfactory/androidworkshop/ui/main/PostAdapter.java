package de.appsfactory.androidworkshop.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.appsfactory.androidworkshop.R;
import de.appsfactory.androidworkshop.domain.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> posts = new ArrayList<>();

    public void updatePosts(List<Post> newPosts) {
        posts.clear();
        posts.addAll(newPosts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.updatePost(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView title = null;

        private TextView body = null;

        PostViewHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            title = itemView.findViewById(R.id.tv_title);
            body = itemView.findViewById(R.id.tv_body);
        }

        void updatePost(Post post) {
            if (title != null) {
                title.setText(post.getTitle());
            }
            if (body != null) {
                body.setText(post.getBody());
            }
        }
    }

}
