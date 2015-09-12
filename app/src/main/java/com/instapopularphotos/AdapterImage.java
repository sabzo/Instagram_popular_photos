package com.instapopularphotos;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sabelo on 9/10/15.
 */
public class AdapterImage extends ArrayAdapter<ModelImage> {

    public static class ViewHolder {
        TextView tvCaption = null;
        TextView tvLikes = null;
        ImageView ivPhoto= null;
        ImageView ivProfile = null;
    }
    public AdapterImage(Context context,  List<ModelImage> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the an image at a certain position from the source data array
        ModelImage image = getItem(position);

        // If there aren't anymore recycled views
        ViewHolder viewHolder;
        if( convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from( getContext() ).inflate(R.layout.item_photo, parent, false);
            viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
            viewHolder.ivPhoto= (ImageView) convertView.findViewById(R.id.ivPhoto);
            viewHolder.ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvCaption.setText(image.getUsername() + image.getCaption());
        viewHolder.tvLikes.setText(image.getLikes().toString());
        viewHolder.ivPhoto.setImageResource(0);
        viewHolder.ivProfile.setImageResource(0);

        Picasso.with(getContext()).load(image.getImgURL()).into(viewHolder.ivPhoto);
        Picasso.with(getContext()).load(image.getProfilePhotoURL()).into(viewHolder.ivProfile);
        return convertView;
    }
}
