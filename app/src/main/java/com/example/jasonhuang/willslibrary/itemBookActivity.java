package com.example.jasonhuang.willslibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class itemBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_book_activity);
        setTitle("Book Description");

        /*
        String title_string = getIntent().getExtras().getString("title_string");
        String author_string = getIntent().getExtras().getString("author_string");
        String genre_string = getIntent().getExtras().getString("genre_string");
        String publisher_string = getIntent().getExtras().getString("publisher_string");
        String publishing_date_string = getIntent().getExtras().getString("publishing_date_string");
        String description_string = getIntent().getExtras().getString("description_string");
        String status_string = getIntent().getExtras().getString("status_string");
        String author_name_string = getIntent().getExtras().getString("author_name_string");*/
        String status_string="In_Store";
        Book book = (Book) getIntent().getSerializableExtra("book");



        TextView titleNameBox = (TextView)findViewById(R.id.titleTextViewBox);
        TextView authorBox = (TextView) findViewById(R.id.authorTextViewBox);
        TextView genreBox = (TextView)findViewById(R.id.genreTextViewBox);
        TextView publisherBox = (TextView)findViewById(R.id.publisherTextViewBox);
        TextView publishing_date_Box = (TextView)findViewById(R.id.publishingDateTextView);
        TextView descriptionBox = (TextView)findViewById(R.id.descriptionTextViewBox);
        //TextView authorTextBox = (TextView)findViewById(R.id.authorTextViewBox);

        titleNameBox.setText(book.getBookTitle());
        authorBox.setText(book.getBookAuthor());
        genreBox.setText(book.getBookGenre());
        publisherBox.setText("");
        publishing_date_Box.setText("");
        descriptionBox.setText("");
        //authorTextBox.setText(author_name_string);


        TextView availableForRentText = (TextView)findViewById(R.id.availableForRentText);
        TextView notAvailableForRentText = (TextView)findViewById(R.id.notAvailableForRent);
        TextView inStockText = (TextView)findViewById(R.id.inStockText);

        Button rentButton = (Button)findViewById(R.id.rentButton);

        //To display "available" or "rented" for the user to see
        //"In_Store" means item is available for rent.
        //"Rented" means it's rented
        //"In_Stock" means it's available but owner does not want it rented
        if(status_string.equals("In_Store")){
            availableForRentText.setVisibility(View.VISIBLE);

        }else if (status_string.equals("Rented")){
            notAvailableForRentText.setVisibility(View.VISIBLE);
            rentButton.setEnabled(false);
        }else{
            //status is not "in_store" or "rented" then this part of the code is "In_stock"
            inStockText.setVisibility(View.VISIBLE);
            rentButton.setEnabled(false);
        }


        //END OF OnCreate();
    }
}
