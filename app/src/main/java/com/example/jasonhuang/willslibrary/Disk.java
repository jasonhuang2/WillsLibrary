package com.example.jasonhuang.willslibrary;

import java.io.Serializable;

public class Disk implements Serializable {
    private String disktitle;
    private String diskdatereleased;
    private String diskgenre;
    private String diskcover;
    private String disktype;
    private String diskdescription;
    private String status;
    private int itemNum;

    public String getDiskTitle(){ return disktitle;}
    public String getDiskDateReleased(){ return diskdatereleased;}
    public String getDiskGenre(){ return diskgenre;}
    public String getDiskCover(){ return diskcover;}
    public String getDiskType(){ return disktype;}
    public String getDiskDescription(){ return diskdescription;}
    public String getStatus(){ return status;}
    public int getItemNum(){ return itemNum;}

    public void setDiskTitle(String disktitle){ this.disktitle = disktitle;}
    public void setDiskdatereleased(String diskdatereleased){ this.diskdatereleased = diskdatereleased;}
    public void setDiskGenre(String diskgenre){ this.diskgenre = diskgenre;}
    public void setDiskCover(String diskcover){ this.diskcover = diskcover;}
    public void setDiskType(String disktype){ this.disktype = disktype;}
    public void setDiskDescription(String diskdescription){ this.diskdescription = diskdescription;}
    public void setStatus(String status){ this.status = status;}
    public void setItemNum(int itemNum){ this.itemNum = itemNum;}



}
