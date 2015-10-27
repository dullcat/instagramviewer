package com.example.towu.instagramviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {
    public static final String CLIENT_ID = "6acc74a3e8b84d559687d839e5c14da3";
    private ArrayList<Photo> photos;
    private PhotosAdapter photosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        photos = new ArrayList<>();
        photosAdapter = new PhotosAdapter(this, 0, photos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(photosAdapter);
        fetchInstagramPhotos();
    }

    public void fetchInstagramPhotos(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.instagram.com/v1/media/popular?client_id=6acc74a3e8b84d559687d839e5c14da3";

        client.get(url, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Log.i("DEBUG", response.toString());
                JSONArray photosJson = null;
                try {
                    photosJson = response.getJSONArray("data");
                    for (int i=0; i<photosJson.length(); i++){
                        JSONObject photoJson = photosJson.getJSONObject(i);
                        Photo photo = new Photo();
                        photo.user = photoJson.getJSONObject("user").getString("username");
                        photo.caption = photoJson.getJSONObject("caption").getString("text");
                        photo.imageUrl = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likeCount = photoJson.getJSONObject("likes").getInt("count");
                        photo.type = photoJson.getString("type");
                        photo.created_time = photoJson.getString("created_time");
                        photo.commentCount = photoJson.getJSONObject("comments").getInt("count");
                        photo.profile_picture = photoJson.getJSONObject("user").getString("profile_picture");

                        photos.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                photosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
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
