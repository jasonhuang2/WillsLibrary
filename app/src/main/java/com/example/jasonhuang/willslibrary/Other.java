package com.example.jasonhuang.willslibrary;

import java.io.Serializable;
public class Other implements Serializable{
    private String otherType;
    private int otherID;
    private String otherDescription;
    private String otherCover;
    private String status;
    private int itemNum;

    public String getOtherType(){ return otherType;}
    public int getOtherID(){ return otherID;}
    public String getOtherDescription(){ return otherDescription;}
    public String getOtherCover(){ return otherCover;}
    public String getStatus(){ return status;}
    public int getItemNum(){ return itemNum;}

    public void setOtherType(String otherType){ this.otherType = otherType;}
    public void setOtherID(int otherID){ this.otherID = otherID;}
    public void setOtherDescription(String otherDescription){ this.otherDescription = otherDescription;}
    public void setOtherCover(String otherCover){ this.otherCover = otherCover;}
    public void setStatus(String status){ this.status = status;}
    public void setItemNum(int itemNum){this.itemNum = itemNum;}

}
