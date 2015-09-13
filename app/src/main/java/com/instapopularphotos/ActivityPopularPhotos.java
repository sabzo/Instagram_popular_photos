package com.instapopularphotos;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityPopularPhotos extends Activity {
    // ClientID currently viewable in Git, need a better solution
    // Or remotely fetch it
    public static final String CLIENT_ID = "client-id";
    // ArrayListImage
    ArrayList<ModelImage> images = null;
    // Adapter
    AdapterImage adapterImage = null;
    //swr
    SwipeRefreshLayout refreshLayout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_photos);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.srfPhotos);
        SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) findViewById(R.id.srfPhotos);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularPhotos();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
                android.R.color.holo_green_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        images = new ArrayList<ModelImage>();
        ListView lvImages = (ListView) findViewById(R.id.lvImages);
        adapterImage = new AdapterImage(this, images);
        lvImages.setAdapter(adapterImage);
        getPopularPhotos();
    }

    private void getPopularPhotos(){
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null,
                new JsonHttpResponseHandler() {
                    // On Success
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.i("Debug", response.toString());
                        adapterImage.clear();
                        JSONArray photosJSON = null;
                        try {

                            photosJSON = response.getJSONArray("data");
                            for (int i = 0; i < photosJSON.length(); i++) {
                                JSONObject photo = photosJSON.getJSONObject(i);
                                String caption = "";
                                if (photo.optJSONObject("caption") != null) {
                                    caption = photo.getJSONObject("caption").getString("text");
                                }
                                String imgURL = photo.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                                String profileImageURL = photo.getJSONObject("user").getString("profile_picture");
                                String username = photo.getJSONObject("user").getString("username");
                                Integer imgHeight = photo.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                                Integer likes = photo.getJSONObject("likes").getInt("count");
                                JSONArray jsonComments = photo.getJSONObject("comments").getJSONArray("data");
                                Integer count = photo.getJSONObject("comments").getInt("count");
                                ArrayList<String> comments = new ArrayList<String>();


                                if( count >= 2) {
                                    for(int j = 1; j <= 2; j++) {
                                        comments.add(jsonComments.getJSONObject(j).getString("text") );
                                    }
                                } else if(count == 1) {
                                    comments.add(jsonComments.getJSONObject(0).getString("text") );
                                }
                                ModelImage image = new ModelImage(username, profileImageURL, imgURL, caption, likes, imgHeight, comments);
                                    images.add(image);
                            }
                            refreshLayout.setRefreshing(false);

                        } catch (JSONException e) {
                            Log.i("Debug", e.toString());
                        }

                        adapterImage.notifyDataSetChanged();
                    }

                    // On Failure

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.i("Info", "getPopularPhotos() failed");
                        //super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popular_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
