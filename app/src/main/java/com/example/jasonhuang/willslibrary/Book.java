package com.example.jasonhuang.willslibrary;

import java.io.Serializable;

public class Book implements Serializable {
    private String bookISBN;
    private String booktitle;
    private String bookauthor;
    private String bookgenre;
    private String bookcover;
    private String publisher;
    private String publishingdate;
    private String description;
    private String status;


    public String getBookISBN(){ return bookISBN; }
    public String getBookTitle(){ return booktitle; }
    public String getBookAuthor(){ return bookauthor; }
    public String getBookGenre(){ return bookgenre; }
    public String getBookCover(){ return bookcover; }
    public String getPublisher(){ return publisher; }
    public String getPublishingDate(){ return publishingdate; }
    public String getDescription(){ return description; }
    public String getStatus() {return status; }


    public void setBookISBN(String bookISBN){ this.bookISBN = bookISBN; }
    public void setBookTitle(String booktitle){this.booktitle = booktitle; }
    public void setBookAuthor(String bookauthor){this.bookauthor = bookauthor; }
    public void setBookGenre(String bookgenre){this.bookgenre = bookgenre; }
    public void setBookCover(String bookcover){this.bookcover = bookcover; }
    public void setPublisher(String publisher) {this.publisher = publisher; }
    public void setPublishingDate(String publishingDate) {this.publishingdate = publishingDate; }
    public void setDescription(String description) {this.description = description; }
    public void setStatus(String status) {this.status = status; }

}
