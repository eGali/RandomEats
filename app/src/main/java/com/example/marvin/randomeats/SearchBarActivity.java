package com.example.marvin.randomeats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SearchBarActivity extends Activity {

    private EditText itemSearch;
    private EditText foodLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        setTitle("Yelp's Search");
        itemSearch = (EditText) findViewById(R.id.searchTerm);
        foodLocation = (EditText) findViewById(R.id.searchLocation);
    }

    public void search(View v) {
        String term = itemSearch.getText().toString();
        String location = foodLocation.getText().toString();
        Intent intent = new Intent(SearchBarActivity.this, YelpSearchListActivity.class);
        intent.putExtra("term", term);
        intent.putExtra("location", location);
        startActivity(intent);
    }

}
