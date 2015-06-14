package com.example.marvin.randomeats;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends ActionBarActivity {
    private Button random, favorites, search;
    private GPS myGPS;
    private double latitude, longitude;
    private DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        dataBaseHelper = new DataBaseHelper(this);
        dataBaseHelper.getWritableDatabase();

        myGPS = new GPS(MainMenu.this);
        if (myGPS.canGetLocation()) {
            latitude = myGPS.getLatitude();
            longitude = myGPS.getLongitude();
        } else {
            myGPS.showSettingsAlert();
        }

        random = (Button) findViewById(R.id.Random);
        favorites = (Button) findViewById(R.id.Favorites);
        search = (Button) findViewById(R.id.Search);
        buttonEffect(random);
        buttonEffect(favorites);
        buttonEffect(search);
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent list_of_results = new Intent(MainMenu.this, ListOfResults.class);
                list_of_results.putExtra("image_url", "http://s3-media3.fl.yelpcdn.com/bphoto/R_-688fc7XCqcFsOvb0KAg/l.jpg");
                startActivity(list_of_results);


            }
        });
        favorites.setOnClickListener(new View.OnClickListener() {
            //            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, Favorites.class);
                startActivity(intent);

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent list_view = new Intent(MainMenu.this, SearchBarActivity.class);
                list_view.putExtra("lati", latitude);
                list_view.putExtra("long", longitude);
                list_view.putExtra("image_url", "http://s3-media3.fl.yelpcdn.com/bphoto/R_-688fc7XCqcFsOvb0KAg/l.jpg");
                startActivity(list_view);
            }
        });

    }

    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe080FF00, PorterDuff.Mode.SRC_IN);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_HOVER_ENTER: {
                        v.getBackground().setColorFilter(0xe080FF00, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_HOVER_EXIT: {
                        v.getBackground().setColorFilter(0xe080FF00, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
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
}
