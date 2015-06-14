package com.example.marvin.randomeats;

/**
 * Created by Edgar on 6/8/15.
 */
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

public class PhotoFragment extends Fragment {

    private String name, phone, address, image_url, url;
    private Button unfave, call, directions, text;
    private DataBaseHelper db;
    private TextView rName, rPhone, rAddress;
    private ImageView food_image;

    //This is the class that displays the favorite restaurant in detail

    //    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        db = new DataBaseHelper(getActivity());
        db.getWritableDatabase();

        Bundle bundle = getArguments();

        name = bundle.getString("name", "");
        phone = bundle.getString("phone", "");
        address = bundle.getString("address", "");
        image_url = bundle.getString("image_url", "");
        url = bundle.getString("url", "");
//        final String theAddress = address.substring(address.indexOf("display_address") + 19, address.indexOf("CA") + 3);

        View view = inflater.inflate(R.layout.photo_fragment, container, false);
        rName = (TextView) view.findViewById(R.id.name);
        rName.setText(name);
        rAddress = (TextView) view.findViewById(R.id.address);
        food_image = (ImageView) view.findViewById(R.id.image);
        try {
            JSONObject addr = new JSONObject(address);
            address = addr.get("display_address").toString();
            address = address.replace("\"", "");
            address = address.replace("[", "");
            address = address.replace("]", "");
            address = address.replace(",", ", ");
            rAddress.setText(address);
        } catch (JSONException e) {
            rAddress.setText("Not Found");
        }
        rPhone = (TextView) view.findViewById(R.id.phone);
        rPhone.setText(phone);
        new DownloadImageTask((ImageView) view.findViewById(R.id.image))
                .execute(image_url);

        //create the button and their effects
        unfave = (Button) view.findViewById(R.id.favorite);
        text = (Button) view.findViewById(R.id.share);
        call = (Button) view.findViewById(R.id.call);
        directions = (Button) view.findViewById(R.id.nav);
        buttonEffect(unfave);
        buttonEffect(call);
        buttonEffect(directions);
        buttonEffect(text);
        unfave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteRestaurant(name);

                Intent intent = new Intent(getActivity(), Favorites.class);
                startActivity(intent);

            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                sendIntent.putExtra("sms_body", "Wanna get a bite? Join me at: " + name + "\n" + address);
                startActivity(sendIntent);

            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });

        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + address));
                startActivity(intent);

            }
        });
        food_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        return view;
    }

    public static void buttonEffect(View button) {
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
}
