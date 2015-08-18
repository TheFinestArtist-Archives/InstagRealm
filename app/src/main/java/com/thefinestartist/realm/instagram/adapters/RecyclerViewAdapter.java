package com.thefinestartist.realm.instagram.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thefinestartist.realm.instagram.R;
import com.thefinestartist.realm.instagram.realm.Post;
import com.thefinestartist.royal.Royal;
import com.thefinestartist.royal.RoyalDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by TheFinestArtist on 6/29/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    RealmResults<Post> realmResults;
    int layoutRes;

    public RecyclerViewAdapter(Class<? extends RoyalDatabase> clazz, int layoutRes) {
        this.realmResults = Royal.getRealmOf(clazz).where(Post.class).findAll();
        this.layoutRes = layoutRes;
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.
                from(parent.getContext()).
                inflate(layoutRes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = realmResults.get(position);
        holder.setPost(post);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.profile)
        SimpleDraweeView profile;
        @Bind(R.id.username)
        TextView username;
        @Bind(R.id.photo)
        SimpleDraweeView photo;
        @Bind(R.id.message)
        TextView message;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setPost(Post post) {
            profile.setImageURI(Uri.parse(post.getUser().getProfile_picture()));
            username.setText(post.getUser().getUsername());
            photo.setImageURI(Uri.parse(post.getImages().getStandard_resolution().getUrl()));
            message.setText(post.getCaption().getText().trim());
        }
    }
}
