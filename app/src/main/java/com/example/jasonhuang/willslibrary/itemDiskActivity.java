package com.example.jasonhuang.willslibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class itemDiskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_disk_activity);
        setTitle("Disk Description");

        String title_string = getIntent().getExtras().getString("title_string");
        String datereleased_string = getIntent().getExtras().getString("datereleased_string");
        String genre_string = getIntent().getExtras().getString("genre_string");
        String disktype_string = getIntent().getExtras().getString("disktype_string");
        String description_string = getIntent().getExtras().getString("description_string");

        TextView titleNameBox = (TextView)findViewById(R.id.disktitleTextViewBox);
        TextView genreBox = (TextView)findViewById(R.id.genreTextViewBox);
        TextView datereleasedBox = (TextView)findViewById(R.id.date_releasedTextView);
        TextView disktypeBox = (TextView)findViewById(R.id.disktypeTextViewBox);
        TextView descriptionBox = (TextView)findViewById(R.id.ddescriptionTextViewBox);

        titleNameBox.setText(title_string);
        genreBox.setText(genre_string);
        disktypeBox.setText(disktype_string);
        datereleasedBox.setText(datereleased_string);
        descriptionBox.setText(description_string);
    }
}
