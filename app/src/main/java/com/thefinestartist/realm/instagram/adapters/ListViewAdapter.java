package com.thefinestartist.realm.instagram.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thefinestartist.realm.instagram.R;
import com.thefinestartist.realm.instagram.realm.Post;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by TheFinestArtist on 6/29/15.
 */
public class ListViewAdapter extends RealmBaseAdapter<Post> implements ListAdapter {

    public ListViewAdapter(Context context, RealmResults<Post> realmResults) {
        super(context, realmResults, true);
    }

    static class ViewHolder {
        @Bind(R.id.profile)
        SimpleDraweeView profile;
        @Bind(R.id.username)
        TextView username;
        @Bind(R.id.photo)
        SimpleDraweeView photo;
        @Bind(R.id.message)
        TextView message;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setPost(Post post) {
            profile.setImageURI(Uri.parse(post.getUser().getProfile_picture()));
            username.setText(post.getUser().getUsername());
            photo.setImageURI(Uri.parse(post.getImages().getStandard_resolution().getUrl()));
            message.setText(post.getCaption().getText().trim());
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_post, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Post post = realmResults.get(position);
        viewHolder.setPost(post);
        return convertView;
    }
}
