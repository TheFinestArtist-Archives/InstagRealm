package io.realm.recyclerview.example.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;
import io.realm.recyclerview.example.models.Post;

/**
 * Created by TheFinestArtist on 6/29/15.
 */
public class ListViewAdapter extends RealmBaseAdapter<Post> implements ListAdapter {

    public ListViewAdapter(Context context, RealmResults<Post> realmResults) {
        super(context, realmResults, true);
    }

    private static class ViewHolder {
        TextView title;
        TextView message;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.message = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Post post = realmResults.get(position);
        viewHolder.title.setText(post.getTitle());
        viewHolder.message.setText(post.getMessage());
        return convertView;
    }
}
