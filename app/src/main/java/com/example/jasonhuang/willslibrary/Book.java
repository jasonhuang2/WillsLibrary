package com.example.jasonhuang.willslibrary;

public class Book {
    private String bookid;
    private String booktitle;
    private String bookauthor;
    private String bookgenre;
    private String bookcover;

    public String getBookID(){ return bookid; }
    public String getBookTitle(){ return booktitle; }
    public String getBookAuthor(){ return bookauthor; }
    public String getBookGenre(){ return bookgenre; }
    public String getBookCover(){ return bookcover; }
    public void setBookid(String bookid){ this.bookid = bookid; }
    public void setBookTitle(String booktitle){this.booktitle = booktitle; }
    public void setBookAuthor(String bookauthor){this.bookauthor = bookauthor; }
    public void setBookGenre(String bookgenre){this.bookgenre = bookgenre; }
    public void setBookCover(String bookcover){this.bookcover = bookcover; }
}
