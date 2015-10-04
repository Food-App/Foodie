package com.app.food.foodie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.support.v4.app.NavUtils;

public class MainActivity extends AppCompatActivity {
    ImageView image;
    int windowwidth;
    int windowheight;
<<<<<<< HEAD
    int currentIndex = 0;
    String currentType = "breakfast";
    int numChoices = 0 ;
    String currentUrl;
    String sourceUrl;
    JSONArray current1;
    JSONArray current2;
    JSONArray current3;

    ArrayList <Integer> keepBreakfast = new ArrayList<Integer>();
    ArrayList <Integer> keepLunch = new ArrayList<Integer>();
    ArrayList <Integer> keepDinner = new ArrayList<Integer>();
=======
    int initX;
    int initY;
>>>>>>> origin/master

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        windowheight = getWindowManager().getDefaultDisplay().getHeight();
        image = (ImageView) findViewById(R.id.imageView);





        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
*/
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("lunch.json"));
            JSONArray m_jArry = obj.getJSONArray("recipes");


            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("title"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
/*
        WebView web = (WebView) findViewById(R.id.webView);
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);
        web.loadUrl("http://static.food2fork.com/an_ideal_lunch_saladd9cf.jpg");


*/

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try{
            JSONObject ObjB = new JSONObject(loadJSONFromAsset("breakfast.json"));
            current1 = ObjB.getJSONArray("recipes");
            JSONObject jo_inside = current1.getJSONObject(currentIndex);
            inputImage(jo_inside.getString("image_url"));
            }
        catch(Exception e){}

        try{
            JSONObject ObjL = new JSONObject(loadJSONFromAsset("lunch.json"));
            current2 = ObjL.getJSONArray("recipes");
            //JSONObject jo_inside = current2.getJSONObject(currentIndex);
            //inputImage(jo_inside.getString("image_url"));
            }
        catch(Exception e){}

        try{
            JSONObject ObjD = new JSONObject(loadJSONFromAsset("dinner.json"));
            current3 = ObjD.getJSONArray("recipes");
            //JSONObject jo_inside = current3.getJSONObject(currentIndex);
            //inputImage(jo_inside.getString("image_url"));
            }
        catch(Exception e){}


        image.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LayoutParams layoutParams = (LayoutParams) image.getLayoutParams();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initX = (int) event.getRawX();
                        initY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x_cord = (int) event.getRawX();
                        int y_cord = (int) event.getRawY();

                        if (x_cord > windowwidth) {
                            x_cord = windowwidth;
                            if ( x_cord + ((float)1/4)* windowwidth > windowwidth){
                                onRightSwipe(currentType, currentIndex);
                                getNextImage();
                            }
                            if ( x_cord < ((float)1/4)*windowwidth){
                                getNextImage();
                            }

                        }
                        if (y_cord > windowheight) {
                            y_cord = windowheight;
                        }

                       // layoutParams. = x_cord - 25;
                       // layoutParams.topMargin = y_cord - 75;

                        image.setX(x_cord-(image.getWidth()/2));
                        image.setLayoutParams(layoutParams);
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("Details-->", "ACTION UP" + initX + " "+ (int) event.getRawX() + " "+initY +" "+ (int) event.getRawY());

                        if(initX == (int) event.getRawX() && initY == (int) event.getRawY())
                        {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                            startActivity(browserIntent);
                            Log.d("Details-->", "IMAGE CLICKED");
                            //return false;
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
/*
        image.setClickable(true);
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
                Log.d("Details-->", "IMAGE CLICKED");
            }
        });*/







    }

    public void inputImage ( String urlLink) {
        try {
            URL url = new URL(urlLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            image.setImageBitmap(myBitmap);
        }
        catch (IOException e)
        {

        }
    }




    public void getNextImage(){
        if ( numChoices < 5 ){
            currentType = "breakfast";
            numChoices++;
            currentIndex = numChoices;
            try {
                currentUrl = current1.getJSONObject(currentIndex).getString("image_url");
                sourceUrl = current1.getJSONObject(currentIndex).getString("source_url");
                inputImage(currentUrl);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if (numChoices < 10){
            currentType = "lunch";
            numChoices++;
            currentIndex = numChoices - 5;
            try {
                current2.getJSONObject(currentIndex);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                currentUrl = current2.getJSONObject(currentIndex).getString("image_url");
                sourceUrl = current2.getJSONObject(currentIndex).getString("source_url");
                inputImage(currentUrl);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
           currentType = "dinner";
            numChoices++;
            currentIndex = numChoices - 10;
            try {
                current3.getJSONObject(currentIndex);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                currentUrl = current3.getJSONObject(currentIndex).getString("image_url");
                sourceUrl = current3.getJSONObject(currentIndex).getString("source_url");
                inputImage(currentUrl);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onRightSwipe(String currentType, Integer currentIndex) {
        Log.d("SWIPE: ","Right Swipe");
        switch(currentType){
            case "breakfast":  keepBreakfast.add(currentIndex);
                                break;
            case "lunch":      keepLunch.add(currentIndex);
                                break;
            case "dinner":     keepDinner.add(currentIndex);
                                break;
            default:           break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {

            InputStream is = getAssets().open(filename);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
