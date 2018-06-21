package com.example.jasonhuang.willslibrary;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Locale;

public class Rental implements Serializable{
    private int itemID;
    private String itemTitle;
    private String dueDate;
    private double overDue;

    public int getItemID(){ return itemID;}
    public String getItemTitle(){ return itemTitle;}
    public String getDueDate(){ return dueDate;}
    public double getOverDue(){ return overDue;}

    public void setItemID(int itemID){ this.itemID = itemID;}
    public void setItemTitle(String itemTitle){ this.itemTitle = itemTitle;}
    public void setDueDate(String dueDate){ this.dueDate = dueDate;}
    public void setOverDue(double overDue){ this.overDue = overDue;}
}
