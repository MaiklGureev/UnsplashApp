package com.gureev.unsplashapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gureev.unsplashapp.R;

public class PhotoPropertiesActivity extends AppCompatActivity {

    TextView text_width, text_height, text_photo_link, text_description;
    String width, height, description, photo_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Photo properties");
        setContentView(R.layout.activity_photo_properties);


        text_height = findViewById(R.id.text_height);
        text_width = findViewById(R.id.text_width);
        text_photo_link = findViewById(R.id.text_photo_link);
        text_description = findViewById(R.id.text_description);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            width = arguments.getString("width");
            height = arguments.getString("height");
            description = (arguments.getString("description") != null) ? arguments.getString("description") : "empty";
            photo_link = arguments.getString("photo_link");

            text_width.append(width);
            text_height.append(height);
            text_description.append(description);
            text_photo_link.append(photo_link);

            text_photo_link.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) PhotoPropertiesActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Copy!", photo_link);
                    clipboard.setPrimaryClip(clip);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Link copied!", Toast.LENGTH_SHORT);
                    toast.show();
                    return true;
                }
            });
        }


    }
}
