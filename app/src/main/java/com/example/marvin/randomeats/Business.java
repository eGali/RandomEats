package com.example.marvin.randomeats;

import org.json.JSONObject;
/**
 * Created by Dustin on 6/5/2015.
 */
class Business {
    final String name;
    final String url;
    final String image_url;
    final String display_phone;
    final String rating;
    final String menu_provider;
    final JSONObject location;

    public Business(String name, String url, String image_url, String display_phone, String rating, String menu_provider, JSONObject location ) {
        this.name = name;
        this.url = url;
        this.image_url = image_url;
        this.display_phone = display_phone;
        this.rating = rating;
        this.menu_provider = menu_provider;
        this.location = location;



    }

    public String getLocation(){
        return location.optString("display_address");
    }
    @Override
    public String toString() {
        return name;
    }
}