package com.example.jasonhuang.willslibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class adminAddItemActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_item_activity);

        setTitle("Add Item");

        Spinner typeOfItemSpinner = (Spinner)findViewById(R.id.typeOfItemSpinner);

        //All of this crap is for BOOK
        final EditText bookNameInput = (EditText)findViewById(R.id.bookNameInput);
        final TextView bookNameText = (TextView)findViewById(R.id.bookNameText);
        final TextView authorNameText = (TextView) findViewById(R.id.authorNameText);
        final EditText authorNameInput = (EditText) findViewById(R.id.authorNameInput);
        final TextView publisherNameText = (TextView)findViewById(R.id.publisherNameText);
        final EditText publisherNameInput = (EditText)findViewById(R.id.publisherNameInput);
        final TextView publisherDateText = (TextView) findViewById(R.id.publishingDateText);
        final EditText publisherDateInput = (EditText)findViewById(R.id.publishingDateInput);
        final TextView descriptionText = (TextView)findViewById(R.id.descriptionText);
        final EditText descriptionInput = (EditText)findViewById(R.id.descriptionInput);



        typeOfItemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String itemSelected = parentView.getItemAtPosition(position).toString();

                if(itemSelected.equals("Book")){
                    bookNameInput.setVisibility(View.VISIBLE);
                    bookNameText.setVisibility(View.VISIBLE);
                    authorNameText.setVisibility(View.VISIBLE);
                    authorNameInput.setVisibility(View.VISIBLE);
                    publisherNameText.setVisibility(View.VISIBLE);
                    publisherNameInput.setVisibility(View.VISIBLE);
                    publisherDateText.setVisibility(View.VISIBLE);
                    publisherDateInput.setVisibility(View.VISIBLE);
                    descriptionText.setVisibility(View.VISIBLE);
                    descriptionInput.setVisibility(View.VISIBLE);


                }else if(itemSelected.equals("Disk")){
                    //WHEN USER SELECTS "Disk"
                    bookNameInput.setVisibility(View.INVISIBLE);
                    bookNameText.setVisibility(View.INVISIBLE);
                    authorNameText.setVisibility(View.INVISIBLE);
                    authorNameInput.setVisibility(View.INVISIBLE);
                    publisherNameText.setVisibility(View.INVISIBLE);
                    publisherNameInput.setVisibility(View.INVISIBLE);
                    publisherDateText.setVisibility(View.INVISIBLE);
                    publisherDateInput.setVisibility(View.INVISIBLE);
                    descriptionText.setVisibility(View.INVISIBLE);
                    descriptionInput.setVisibility(View.INVISIBLE);



                }else{
                    //IF USER SELECTED "OTHER"
                    bookNameInput.setVisibility(View.INVISIBLE);
                    bookNameText.setVisibility(View.INVISIBLE);
                    authorNameText.setVisibility(View.INVISIBLE);
                    authorNameInput.setVisibility(View.INVISIBLE);
                    publisherNameText.setVisibility(View.INVISIBLE);
                    publisherNameInput.setVisibility(View.INVISIBLE);
                    publisherDateText.setVisibility(View.INVISIBLE);
                    publisherDateInput.setVisibility(View.INVISIBLE);
                    descriptionText.setVisibility(View.INVISIBLE);
                    descriptionInput.setVisibility(View.INVISIBLE);                }


            }

            //Ignore this for now
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }

    public void addButtonListener(View v){
        EditText bookNameInput = (EditText)findViewById(R.id.bookNameInput);
        TextView bookNameText = (TextView)findViewById(R.id.bookNameText);


        bookNameText.setText(bookNameInput.getText().toString());

    }
}
