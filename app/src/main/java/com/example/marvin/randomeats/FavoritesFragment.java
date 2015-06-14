package com.example.marvin.randomeats;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Edgar on 6/10/15.
 */

//This is the class that displays the database restaurants in a list
public class FavoritesFragment extends Fragment implements AdapterView.OnItemClickListener {
    Cursor cursor;
    ListView lv;
    FavoritesAdapter adapter;
    Button main;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        DataBaseHelper dbHelper = new DataBaseHelper(getActivity());
        cursor = dbHelper.getAllRestaurants();
        getActivity().startManagingCursor(cursor);
        adapter = new FavoritesAdapter(getActivity(), cursor);
        lv = (ListView)view.findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

        main = (Button)view.findViewById(R.id.mainMenu);
        main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), MainMenu.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        cursor.moveToFirst();
        cursor.move(i);
        cursor.moveToFirst();
        cursor.move(i);

        String name = cursor.getString(cursor.getColumnIndexOrThrow(Contract.favoriteRestaurant.NAME));
        String image_url = cursor.getString(cursor.getColumnIndexOrThrow(Contract.favoriteRestaurant.IMAGE));
        String phone = cursor.getString(cursor.getColumnIndexOrThrow(Contract.favoriteRestaurant.PHONE));
        String address = cursor.getString(cursor.getColumnIndexOrThrow(Contract.favoriteRestaurant.ADDRESS));
        String url = cursor.getString(cursor.getColumnIndexOrThrow(Contract.favoriteRestaurant.URL));

        PhotoFragment pf = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString("image_url", image_url);
        args.putString("url", url);
        args.putString("name", name);
        args.putString("phone", phone);
        args.putString("address", address);
        pf.setArguments(args);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, pf);
        ft.addToBackStack("Image");
        ft.commit();

    }
}
