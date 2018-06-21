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

public class DiskAdapter extends ArrayAdapter<Disk>  {

    private static class ViewHolder{
        public ImageView ivCover;
        public TextView tvTitle;
        //Item Author of the layout will be the Disk Type
        public TextView tvType;
    }


    public DiskAdapter(Context context, ArrayList<Disk> aDisks){
        super(context, 0, aDisks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final Disk disk = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_catalogue_layout, parent, false);
            viewHolder.ivCover = (ImageView)convertView.findViewById(R.id.ivItemCover);
            viewHolder.tvTitle = (TextView)convertView.findViewById(R.id.itemTitle);
            viewHolder.tvType = (TextView)convertView.findViewById(R.id.itemAuthor);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(disk.getDiskTitle());
        viewHolder.tvType.setText(disk.getDiskType());
        int itemNum = disk.getItemNum();
        switch(itemNum){
            case 3:
                viewHolder.ivCover.setImageResource(R.drawable.blackops3);
                break;
            case 4:
                viewHolder.ivCover.setImageResource(R.drawable.theincredibles);
                break;
            default:
                viewHolder.ivCover.setImageResource(R.drawable.diskimage);
                break;
        }
        //Do the same for image
        return convertView;
    }

}
