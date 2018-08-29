package com.douban.book.dao.book.information.domain;

import com.douban.book.base.domain.BaseEntity;
import com.douban.book.dao.book.type.domain.BookType;
import org.junit.Test;

import javax.persistence.*;

@Entity
@Table(name = "book_information")
public class Information extends BaseEntity {

    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name="isbn")
    private String ISBN;
    @Column(name="score")
    private double score;
    @Column(name="bookReviewUrl")
    private String bookReviewUrl;
    @ManyToOne
    @JoinColumn(name="booktype")
    private BookType bookType;
    @Column(name="mark")
    private int mark;

    @Column(name="url")
    private String url;



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getBookReviewUrl() {
        return bookReviewUrl;
    }

    public void setBookReviewUrl(String bookReviewUrl) {
        this.bookReviewUrl = bookReviewUrl;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
