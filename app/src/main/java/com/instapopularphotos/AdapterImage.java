package com.instapopularphotos;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        TextView tvUsername = null;
        LinearLayout llComments = null;
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
            viewHolder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.llComments = (LinearLayout) convertView.findViewById(R.id.llComments);
            viewHolder.ivPhoto= (ImageView) convertView.findViewById(R.id.ivPhoto);
            viewHolder.ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvUsername.setText(image.getUsername());
        viewHolder.tvCaption.setText(image.getUsername() + "  " + image.getCaption());
        viewHolder.llComments.removeAllViews();
        for(String x: image.getComments()) {
            TextView tvComment = new TextView(getContext());
            tvComment.setText(x);
            viewHolder.llComments.addView(tvComment);
        }
        String formmatedLikes = "<font color='blue'>" + image.getLikes().toString() + " Likes" + "</font>";
        viewHolder.tvLikes.setText(Html.fromHtml(formmatedLikes));
        viewHolder.ivPhoto.setImageResource(0);
        viewHolder.ivProfile.setImageResource(0);

        Picasso.with(getContext()).load(image.getImgURL()).into(viewHolder.ivPhoto);
        Picasso.with(getContext()).load(image.getProfilePhotoURL()).transform(new CircleTransform()).into(viewHolder.ivProfile);
        return convertView;
    }
}
