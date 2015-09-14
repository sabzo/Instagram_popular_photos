package com.instapopularphotos.ui.mainscreen;

import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.instapopularphotos.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityPopularPhotos extends AppCompatActivity {
    // ClientID currently viewable in Git, need a better solution Or remotely fetch it
    // If CLIENT_ID is still in Git, just know I've already disabled it (for security reasons)
    public static final String CLIENT_ID = "";
    ArrayList<ModelImage> images = null;
    AdapterImage adapterImage = null;
    SwipeRefreshLayout refreshLayout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_photos);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Popular Photos!");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.ic_action_name);
        actionBar.setDisplayUseLogoEnabled(true);

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
                                ModelImage image = new ModelImage( photosJSON.getJSONObject(i) );
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

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
