package com.example.towu.instagramviewer;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by towu on 10/25/15.
 */
public class PhotosAdapter extends ArrayAdapter<Photo> {
    public PhotosAdapter(Context context, int resource, List<Photo> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Photo photo = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvComments = (TextView) convertView.findViewById(R.id.tvComments);
        TextView tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
        TextView tvCreated = (TextView) convertView.findViewById(R.id.tvCreatedTime);

        tvCaption.setText(photo.caption);
        ivPhoto.setImageResource(0);
        tvAuthor.setText(photo.user);
        tvComments.setText(photo.commentCount + " Comments  ");
        tvLikes.setText(photo.likeCount+" Likes  ");
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
        Picasso.with(getContext()).load(photo.profile_picture).resize(20,20).into(ivProfile);
        tvCreated.setText(DateUtils.getRelativeTimeSpanString(new Long(photo.created_time).longValue()*1000,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));
        return convertView;
    }
}
