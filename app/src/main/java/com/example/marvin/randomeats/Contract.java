package com.example.marvin.randomeats;

import android.provider.BaseColumns;

/**
 * Created by Edgar on 5/28/15.
 */
public class Contract {
    public static final String DATABASE_NAME = "randomEats.db";

    public static final class favoriteRestaurant implements BaseColumns {

        public static final int DATABASE_VERSION = 1;
        public static final String TABLE_NAME = "restaurant_entry";
        public static final String _ID = "_id";
        public static final String NAME = "name";
        public static final String IMAGE = "image_url";
        public static final String URL = "url";
        public static final String PHONE = "display_phone";
        public static final String ADDRESS = "location";


    }
}