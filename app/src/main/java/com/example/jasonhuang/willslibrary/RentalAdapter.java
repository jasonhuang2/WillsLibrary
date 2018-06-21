package com.example.jasonhuang.willslibrary;

import android.content.Context;
import android.renderscript.Double2;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

public class RentalAdapter extends ArrayAdapter<Rental>{

    private static class ViewHolder{
        public TextView itemID;
        public TextView itemTitle;
        public TextView dueDate;
        public TextView overDue;
    }

    public RentalAdapter(Context context, ArrayList<Rental> items){ super(context, 0, items);}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final Rental rental = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.my_rentals_lv_layout, parent, false);
            viewHolder.itemID = (TextView)convertView.findViewById(R.id.itemID);
            viewHolder.itemTitle = (TextView)convertView.findViewById(R.id.itemtitle);
            viewHolder.dueDate = (TextView)convertView.findViewById(R.id.duedate);
            viewHolder.overDue = (TextView)convertView.findViewById(R.id.overduecost);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.itemID.setText(Double.toString(rental.getItemID()));
        viewHolder.itemTitle.setText(rental.getItemTitle());
        viewHolder.dueDate.setText(rental.getDueDate());
        viewHolder.overDue.setText("$" + Double.toString(rental.getOverDue()));

        return convertView;
    }
}
