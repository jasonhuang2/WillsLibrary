package com.example.jasonhuang.willslibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class itemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);



        String title_string = getIntent().getExtras().getString("title_string");
        String genre_string = getIntent().getExtras().getString("genre_string");
        String publisher_string = getIntent().getExtras().getString("publisher_string");
        String publishing_date_string = getIntent().getExtras().getString("publishing_date_string");
        String description_string = getIntent().getExtras().getString("description_string");

        TextView titleNameBox = (TextView)findViewById(R.id.titleTextViewBox);
        TextView genreBox = (TextView)findViewById(R.id.genreTextViewBox);
        TextView publisherBox = (TextView)findViewById(R.id.publisherTextViewBox);
        TextView publishing_date_Box = (TextView)findViewById(R.id.publishingDateTextView);
        TextView descriptionBox = (TextView)findViewById(R.id.descriptionTextViewBox);

        titleNameBox.setText(title_string);
        genreBox.setText(genre_string);
        publisherBox.setText(publisher_string);
        publishing_date_Box.setText(publishing_date_string);
        descriptionBox.setText(description_string);



    }
}
