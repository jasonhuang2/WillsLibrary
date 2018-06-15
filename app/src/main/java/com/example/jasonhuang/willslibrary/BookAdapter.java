package com.example.jasonhuang.willslibrary;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book>  {

    private static class ViewHolder{
        public ImageView ivCover;
        public TextView tvTitle;
        public TextView tvAuthor;
    }


    public BookAdapter(Context context, ArrayList<Book> aBooks){
        super(context, 0, aBooks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final Book book = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_catalogue_layout, parent, false);
            viewHolder.ivCover = (ImageView)convertView.findViewById(R.id.ivItemCover);
            viewHolder.tvTitle = (TextView)convertView.findViewById(R.id.itemTitle);
            viewHolder.tvAuthor = (TextView)convertView.findViewById(R.id.itemAuthor);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(book.getBookTitle());
        viewHolder.tvAuthor.setText(book.getBookAuthor());
        //Do the same for image
        return convertView;
    }

}
