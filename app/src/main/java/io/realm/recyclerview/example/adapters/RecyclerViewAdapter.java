package io.realm.recyclerview.example.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmResults;
import io.realm.recyclerview.example.models.Post;

/**
 * Created by TheFinestArtist on 6/29/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.SimpleViewHolder> {

    RealmResults<Post> realmResults;
    int layoutRes;

    public RecyclerViewAdapter(RealmResults<Post> realmResults, int layoutRes) {
        this.realmResults = realmResults;
        this.layoutRes = layoutRes;
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(layoutRes, parent, false);
        return new SimpleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        Post post = realmResults.get(position);
        holder.setTitle(post.getTitle());
        holder.setMessage(post.getMessage());
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView message;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(android.R.id.text1);
            message = (TextView) itemView.findViewById(android.R.id.text2);
        }

        public void setTitle(String text) {
            title.setText(text);
        }

        public void setMessage(String text) {
            message.setText(text);
        }
    }
}
