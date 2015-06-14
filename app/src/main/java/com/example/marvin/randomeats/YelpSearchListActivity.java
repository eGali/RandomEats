package com.example.marvin.randomeats;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;

public class YelpSearchListActivity extends ActionBarActivity {

    private ViewFlipper viewFlipper;
    private float lastX;
    private TextView name1, addr1, phone1, name2, addr2, phone2, name3, addr3, phone3, name4, addr4, phone4, name5, addr5, phone5;
    private ImageView image1, image2, image3, image4, image5;
    private Button share1, share2, share3, share4, share5, favorite1, favorite2, favorite3, favorite4, favorite5, nav1, nav2, nav3, nav4, nav5, call1, call2, call3, call4, call5;
    private double latitude, longitude;
    private GPS myGPS;
    private ArrayList<Business> results;
    private DataBaseHelper dataBaseHelper;
    private String searchTerm, searchLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Deliciously finding...");
        Intent intent = getIntent();
        searchTerm = intent.getStringExtra("term");
        searchLocation = intent.getStringExtra("location");

        if(intent.getStringExtra("term") == null || intent.getStringExtra("term") == ""){
            searchTerm = "restaurants";
        }
        dataBaseHelper = new DataBaseHelper(this);
        dataBaseHelper.getWritableDatabase();
        // INITIALIZE GUI OBJECTS
        initialize();

        //SET BUTTON EFFECTS
        set_button_effects();

        //GET RESTAURANTS
        myGPS = new GPS(YelpSearchListActivity.this);
        Log.i("location", "=" + searchLocation + "=");
        if(searchLocation == null || searchLocation.length() <= 1) {
            if (myGPS.canGetLocation()) {
                latitude = myGPS.getLatitude();
                longitude = myGPS.getLongitude();
                setSupportProgressBarIndeterminateVisibility(true);
                new AsyncTask<Void, Void, ArrayList<Business>>() {
                    @Override
                    public ArrayList<Business> doInBackground(Void... params) {
                        String businesses = Yelp.getYelp(YelpSearchListActivity.this).search(searchTerm, latitude, longitude);
                        try {
                            return processJson(businesses);
                        } catch (Exception e) {
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(ArrayList<Business> result) {
                        Collections.shuffle(result);
                        results = result;
                        for (int i = 0; i <= 5; i++) {
                            switch (i) {
                                case 0:
                                    name1.setText(result.get(i).name);
                                    try {
                                        String address = result.get(i).location.get("display_address").toString();
                                        address = address.replace("\"", "");
                                        address = address.replace("[", "");
                                        address = address.replace("]", "");
                                        address = address.replace(",", ", ");
                                        addr1.setText(address);
                                    } catch (Exception e) {
                                        addr1.setText("Not Found");
                                    }
                                    phone1.setText(result.get(i).display_phone);
                                    new DownloadImageTask((ImageView) findViewById(R.id.image1))
                                            .execute(results.get(i).image_url);
                                    break;
                                case 1:
                                    name2.setText(result.get(i).name);
                                    try {
                                        String address = result.get(i).location.get("display_address").toString();
                                        address = address.replace("\"", "");
                                        address = address.replace("[", "");
                                        address = address.replace("]", "");
                                        address = address.replace(",", ", ");
                                        addr2.setText(address);
                                    } catch (Exception e) {
                                        addr2.setText("Not Found");
                                    }
                                    phone2.setText(result.get(i).display_phone);
                                    new DownloadImageTask((ImageView) findViewById(R.id.image2))
                                            .execute(results.get(i).image_url);
                                    break;
                                case 2:
                                    name3.setText(result.get(i).name);
                                    try {
                                        String address = result.get(i).location.get("display_address").toString();
                                        address = address.replace("\"", "");
                                        address = address.replace("[", "");
                                        address = address.replace("]", "");
                                        address = address.replace(",", ", ");
                                        addr3.setText(address);
                                    } catch (Exception e) {
                                        addr3.setText("Not Found");
                                    }
                                    phone3.setText(result.get(i).display_phone);
                                    new DownloadImageTask((ImageView) findViewById(R.id.image3))
                                            .execute(results.get(i).image_url);
                                    break;
                                case 3:
                                    name4.setText(result.get(i).name);
                                    try {
                                        String address = result.get(i).location.get("display_address").toString();
                                        address = address.replace("\"", "");
                                        address = address.replace("[", "");
                                        address = address.replace("]", "");
                                        address = address.replace(",", ", ");
                                        addr4.setText(address);
                                    } catch (Exception e) {
                                        addr4.setText("Not Found");
                                    }
                                    phone4.setText(result.get(i).display_phone);
                                    new DownloadImageTask((ImageView) findViewById(R.id.image4))
                                            .execute(results.get(i).image_url);
                                    break;
                                case 4:
                                    name5.setText(result.get(i).name);
                                    try {
                                        String address = result.get(i).location.get("display_address").toString();
                                        address = address.replace("\"", "");
                                        address = address.replace("[", "");
                                        address = address.replace("]", "");
                                        address = address.replace(",", ", ");
                                        addr5.setText(address);
                                    } catch (Exception e) {
                                        addr5.setText("Not Found");
                                    }
                                    phone5.setText(result.get(i).display_phone);
                                    new DownloadImageTask((ImageView) findViewById(R.id.image5))
                                            .execute(results.get(i).image_url);
                                    break;
                                default:
                                    break;
                            }
                        }
                        setSupportProgressBarIndeterminateVisibility(false);

                    }
                }.execute();
            } else {
                setSupportProgressBarIndeterminateVisibility(true);
                new AsyncTask<Void, Void, ArrayList<Business>>() {
                    @Override
                    public ArrayList<Business> doInBackground(Void... params) {
                        String businesses = Yelp.getYelp(YelpSearchListActivity.this).search(searchTerm, "Los Angeles, CA");
                        try {
                            return processJson(businesses);
                        } catch (Exception e) {
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(ArrayList<Business> result) {
                        Collections.shuffle(result);
                        results = result;
                        for (int i = 0; i <= 5; i++) {
                            switch (i) {
                                case 0:
                                    name1.setText(result.get(i).name);
                                    try {
                                        String address = result.get(i).location.get("display_address").toString();
                                        address = address.replace("\"", "");
                                        address = address.replace("[", "");
                                        address = address.replace("]", "");
                                        address = address.replace(",", ", ");
                                        addr1.setText(address);
                                    } catch (Exception e) {
                                        addr1.setText("Not Found");
                                    }
                                    phone1.setText(result.get(i).display_phone);
                                    new DownloadImageTask((ImageView) findViewById(R.id.image1))
                                            .execute(results.get(i).image_url);
                                    break;
                                case 1:
                                    name2.setText(result.get(i).name);
                                    try {
                                        String address = result.get(i).location.get("display_address").toString();
                                        address = address.replace("\"", "");
                                        address = address.replace("[", "");
                                        address = address.replace("]", "");
                                        address = address.replace(",", ", ");
                                        addr2.setText(address);
                                    } catch (Exception e) {
                                        addr2.setText("Not Found");
                                    }
                                    phone2.setText(result.get(i).display_phone);
                                    new DownloadImageTask((ImageView) findViewById(R.id.image2))
                                            .execute(results.get(i).image_url);
                                    break;
                                case 2:
                                    name3.setText(result.get(i).name);
                                    try {
                                        String address = result.get(i).location.get("display_address").toString();
                                        address = address.replace("\"", "");
                                        address = address.replace("[", "");
                                        address = address.replace("]", "");
                                        address = address.replace(",", ", ");
                                        addr3.setText(address);
                                    } catch (Exception e) {
                                        addr3.setText("Not Found");
                                    }
                                    phone3.setText(result.get(i).display_phone);
                                    new DownloadImageTask((ImageView) findViewById(R.id.image3))
                                            .execute(results.get(i).image_url);
                                    break;
                                case 3:
                                    name4.setText(result.get(i).name);
                                    try {
                                        String address = result.get(i).location.get("display_address").toString();
                                        address = address.replace("\"", "");
                                        address = address.replace("[", "");
                                        address = address.replace("]", "");
                                        address = address.replace(",", ", ");
                                        addr4.setText(address);
                                    } catch (Exception e) {
                                        addr4.setText("Not Found");
                                    }
                                    phone4.setText(result.get(i).display_phone);
                                    new DownloadImageTask((ImageView) findViewById(R.id.image4))
                                            .execute(results.get(i).image_url);
                                    break;
                                case 4:
                                    name5.setText(result.get(i).name);
                                    try {
                                        String address = result.get(i).location.get("display_address").toString();
                                        address = address.replace("\"", "");
                                        address = address.replace("[", "");
                                        address = address.replace("]", "");
                                        address = address.replace(",", ", ");
                                        addr5.setText(address);
                                    } catch (Exception e) {
                                        addr5.setText("Not Found");
                                    }
                                    phone5.setText(result.get(i).display_phone);
                                    new DownloadImageTask((ImageView) findViewById(R.id.image5))
                                            .execute(results.get(i).image_url);
                                    break;
                                default:
                                    break;
                            }
                        }
                        setSupportProgressBarIndeterminateVisibility(false);
                    }
                }.execute();
            }
        }else{
            setSupportProgressBarIndeterminateVisibility(true);
            new AsyncTask<Void, Void, ArrayList<Business>>() {
                @Override
                public ArrayList<Business> doInBackground(Void... params) {
                    String businesses = Yelp.getYelp(YelpSearchListActivity.this).search(searchTerm, searchLocation);
                    try {
                        return processJson(businesses);
                    } catch (Exception e) {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(ArrayList<Business> result) {
                    Collections.shuffle(result);
                    results = result;
                    for (int i = 0; i <= 5; i++) {
                        switch (i) {
                            case 0:
                                name1.setText(result.get(i).name);
                                try {
                                    String address = result.get(i).location.get("display_address").toString();
                                    address = address.replace("\"", "");
                                    address = address.replace("[", "");
                                    address = address.replace("]", "");
                                    address = address.replace(",", ", ");
                                    addr1.setText(address);
                                } catch (Exception e) {
                                    addr1.setText("Not Found");
                                }
                                phone1.setText(result.get(i).display_phone);
                                new DownloadImageTask((ImageView) findViewById(R.id.image1))
                                        .execute(results.get(i).image_url);
                                break;
                            case 1:
                                name2.setText(result.get(i).name);
                                try {
                                    String address = result.get(i).location.get("display_address").toString();
                                    address = address.replace("\"", "");
                                    address = address.replace("[", "");
                                    address = address.replace("]", "");
                                    address = address.replace(",", ", ");
                                    addr2.setText(address);
                                } catch (Exception e) {
                                    addr2.setText("Not Found");
                                }
                                phone2.setText(result.get(i).display_phone);
                                new DownloadImageTask((ImageView) findViewById(R.id.image2))
                                        .execute(results.get(i).image_url);
                                break;
                            case 2:
                                name3.setText(result.get(i).name);
                                try {
                                    String address = result.get(i).location.get("display_address").toString();
                                    address = address.replace("\"", "");
                                    address = address.replace("[", "");
                                    address = address.replace("]", "");
                                    address = address.replace(",", ", ");
                                    addr3.setText(address);
                                } catch (Exception e) {
                                    addr3.setText("Not Found");
                                }
                                phone3.setText(result.get(i).display_phone);
                                new DownloadImageTask((ImageView) findViewById(R.id.image3))
                                        .execute(results.get(i).image_url);
                                break;
                            case 3:
                                name4.setText(result.get(i).name);
                                try {
                                    String address = result.get(i).location.get("display_address").toString();
                                    address = address.replace("\"", "");
                                    address = address.replace("[", "");
                                    address = address.replace("]", "");
                                    address = address.replace(",", ", ");
                                    addr4.setText(address);
                                } catch (Exception e) {
                                    addr4.setText("Not Found");
                                }
                                phone4.setText(result.get(i).display_phone);
                                new DownloadImageTask((ImageView) findViewById(R.id.image4))
                                        .execute(results.get(i).image_url);
                                break;
                            case 4:
                                name5.setText(result.get(i).name);
                                try {
                                    String address = result.get(i).location.get("display_address").toString();
                                    address = address.replace("\"", "");
                                    address = address.replace("[", "");
                                    address = address.replace("]", "");
                                    address = address.replace(",", ", ");
                                    addr5.setText(address);
                                } catch (Exception e) {
                                    addr5.setText("Not Found");
                                }
                                phone5.setText(result.get(i).display_phone);
                                new DownloadImageTask((ImageView) findViewById(R.id.image5))
                                        .execute(results.get(i).image_url);
                                break;
                            default:
                                break;
                        }
                    }
                    setSupportProgressBarIndeterminateVisibility(false);
                }
            }.execute();
        }

        set_share_buttons();
        set_call_buttons();
        set_nav_buttons();
        set_images();
        set_open_yelp();
    }


    //INITIALIZE VARIABLES
    public void initialize(){
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        name1 = (TextView) findViewById(R.id.name1);
        addr1 = (TextView) findViewById(R.id.address1);
        phone1 = (TextView) findViewById(R.id.phone1);
        image1 = (ImageView) findViewById(R.id.image1);
        name2 = (TextView) findViewById(R.id.name2);
        addr2 = (TextView) findViewById(R.id.address2);
        phone2 = (TextView) findViewById(R.id.phone2);
        image2 = (ImageView) findViewById(R.id.image2);
        name3 = (TextView) findViewById(R.id.name3);
        addr3 = (TextView) findViewById(R.id.address3);
        phone3 = (TextView) findViewById(R.id.phone3);
        image3 = (ImageView) findViewById(R.id.image3);
        name4 = (TextView) findViewById(R.id.name4);
        addr4 = (TextView) findViewById(R.id.address4);
        phone4 = (TextView) findViewById(R.id.phone4);
        image4 = (ImageView) findViewById(R.id.image4);
        name5 = (TextView) findViewById(R.id.name5);
        addr5 = (TextView) findViewById(R.id.address5);
        phone5 = (TextView) findViewById(R.id.phone5);
        image5 = (ImageView) findViewById(R.id.image5);
        share1 = (Button) findViewById(R.id.share1);
        share2 = (Button) findViewById(R.id.share2);
        share3 = (Button) findViewById(R.id.share3);
        share4 = (Button) findViewById(R.id.share4);
        share5 = (Button) findViewById(R.id.share5);
        favorite1 = (Button) findViewById(R.id.favorite1);
        favorite2 = (Button) findViewById(R.id.favorite2);
        favorite3 = (Button) findViewById(R.id.favorite3);
        favorite4 = (Button) findViewById(R.id.favorite4);
        favorite5 = (Button) findViewById(R.id.favorite5);
        nav1 = (Button) findViewById(R.id.nav1);
        nav2 = (Button) findViewById(R.id.nav2);
        nav3 = (Button) findViewById(R.id.nav3);
        nav4 = (Button) findViewById(R.id.nav4);
        nav5 = (Button) findViewById(R.id.nav5);
        call1 = (Button) findViewById(R.id.call1);
        call2 = (Button) findViewById(R.id.call2);
        call3 = (Button) findViewById(R.id.call3);
        call4 = (Button) findViewById(R.id.call4);
        call5 = (Button) findViewById(R.id.call5);
    }

    //BUTTON EFFECTS
    public void set_button_effects(){
        Button [] button_list = {share1, share2, share3, share4, share5, favorite1, favorite2,
                favorite3, favorite4, favorite5, nav1, nav2, nav3, nav4, nav5, call1, call2, call3,
                call4, call5};
        for(int i = 0; i < button_list.length; i++){
            buttonEffect(button_list[i]);
        }
    }

    public void set_images(){
        favorite1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show the location in the intent
                Intent intent = new Intent(YelpSearchListActivity.this, Favorites.class);
                dataBaseHelper.insertRestaurant(results.get(0));

            }
        });
        favorite2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show the location in the intent
                Intent intent = new Intent(YelpSearchListActivity.this, Favorites.class);
                dataBaseHelper.insertRestaurant(results.get(1));

            }
        });
        favorite3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show the location in the intent
                Intent intent = new Intent(YelpSearchListActivity.this, Favorites.class);
                dataBaseHelper.insertRestaurant(results.get(2));

            }
        });
        favorite4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show the location in the intent
                Intent intent = new Intent(YelpSearchListActivity.this, Favorites.class);
                dataBaseHelper.insertRestaurant(results.get(3));

            }
        });
        favorite5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show the location in the intent
                Intent intent = new Intent(YelpSearchListActivity.this, Favorites.class);
                dataBaseHelper.insertRestaurant(results.get(4));

            }
        });

    }

    public void set_open_yelp(){
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(results.get(0).url)));
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(results.get(1).url)));
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(results.get(2).url)));
            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(results.get(3).url)));
            }
        });
        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(results.get(4).url)));
            }
        });
    }

    public void set_nav_buttons(){
        nav1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show the location in the intent
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + addr1.getText().toString()));
                startActivity(intent);

            }
        });
        nav2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show the location in the intent
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + addr2.getText().toString()));
                startActivity(intent);

            }
        });
        nav3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show the location in the intent
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + addr3.getText().toString()));
                startActivity(intent);
            }
        });
        nav4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show the location in the intent
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + addr4.getText().toString()));
                startActivity(intent);
            }
        });
        nav5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show the location in the intent
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + addr5.getText().toString()));
                startActivity(intent);
            }
        });
    }

    //SET THE ON CLICK FOR TEXTING RESTAURANTS
    public void set_share_buttons(){
        share1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                sendIntent.putExtra("sms_body", "Wanna get a bite? Join me at: \n" + name1.getText().toString() + "\n" + addr1.getText().toString() + "\n" + phone1.getText().toString());
                startActivity(sendIntent);
            }
        });
        share2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                sendIntent.putExtra("sms_body", "Wanna get a bite? Join me at: \n" + name2.getText().toString() + "\n" + addr2.getText().toString() + "\n" + phone2.getText().toString());
                startActivity(sendIntent);
            }
        });
        share3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                sendIntent.putExtra("sms_body", "Wanna get a bite? Join me at: \n" + name3.getText().toString() + "\n" + addr3.getText().toString() + "\n" + phone3.getText().toString());
                startActivity(sendIntent);
            }
        });
        share4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                sendIntent.putExtra("sms_body", "Wanna get a bite? Join me at: \n" + name4.getText().toString() + "\n" + addr4.getText().toString() + "\n" + phone4.getText().toString());
                startActivity(sendIntent);
            }
        });
        share5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                sendIntent.putExtra("sms_body", "Wanna get a bite? Join me at: \n" + name5.getText().toString() + "\n" + addr5.getText().toString() + "\n" + phone5.getText().toString());
                startActivity(sendIntent);
            }
        });

    }

    //SET THE ON CLICK FOR CALLING RESTAURANTS
    public void set_call_buttons(){
        call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = phone1.getText().toString();
                String number = uri.substring(uri.indexOf("+"), uri.length());
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });
        call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = phone2.getText().toString();
                String number = uri.substring(uri.indexOf("+"), uri.length());
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });
        call3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = phone3.getText().toString();
                String number = uri.substring(uri.indexOf("+"), uri.length());
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });
        call4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = phone4.getText().toString();
                String number = uri.substring(uri.indexOf("+"), uri.length());
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });
        call5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = phone5.getText().toString();
                String number = uri.substring(uri.indexOf("+"), uri.length());
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });
    }

    //This will handle the browsers
    // Using the following method, we will handle all screen swaps.
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX = touchevent.getX();

                // Handling left to right screen swap.
                if (lastX < currentX) {

                    // If there aren't any other children, just break.
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;

                    // Next screen comes in from left.
                    viewFlipper.setInAnimation(this, R.anim.left_in);
                    // Current screen goes out from right.
                    viewFlipper.setOutAnimation(this, R.anim.right_out);

                    // Display next screen.
                    viewFlipper.showNext();
                }

                // Handling right to left screen swap.
                if (lastX > currentX) {

                    // If there is a child (to the left), kust break.
                    if (viewFlipper.getDisplayedChild() == 1)
                        break;

                    // Next screen comes in from right.
                    viewFlipper.setInAnimation(this, R.anim.right_in);
                    // Current screen goes out from left.
                    viewFlipper.setOutAnimation(this, R.anim.left_out);

                    // Display previous screen.
                    viewFlipper.showPrevious();
                }
                break;
        }
        return false;
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
        //getMenuInflater().inflate(R.menu.menu_list_of_results, menu);
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

    public void open_yelp1(){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(results.get(0).url)));
    }
    public void open_yelp2(){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(results.get(1).url)));
    }
    public void open_yelp3(){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(results.get(2).url)));
    }
    public void open_yelp4(){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(results.get(3).url)));
    }
    public void open_yelp5(){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(results.get(4).url)));
    }


    public ArrayList<Business> processJson(String jsonStuff) throws JSONException {
        JSONObject json = new JSONObject(jsonStuff);
        JSONArray businesses = json.getJSONArray("businesses");
        ArrayList<Business> businessObjs = new ArrayList<Business>(businesses.length());
        for (int i = 0; i < businesses.length(); i++) {
            JSONObject business = businesses.getJSONObject(i);
            //image_url, name, display_phone, rating, menu_provider, location.display_address
            businessObjs.add(new Business(business.optString("name"), business.optString("mobile_url"), business.optString("image_url"),
                    business.optString("display_phone"), business.optString("rating"), business.optString("menu_proivder"),
                    business.optJSONObject("location")));
        }
        return businessObjs;
    }
}


