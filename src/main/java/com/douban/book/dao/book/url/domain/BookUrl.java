package com.douban.book.dao.book.url.domain;

import com.douban.book.base.domain.BaseEntity;
import com.douban.book.dao.book.type.domain.BookType;
import org.junit.Test;

import javax.persistence.*;

@Entity
@Table(name="book_url")
public class BookUrl extends BaseEntity{

    @Column(name="url")
    private String bookUrl;

    @Column(name="title")
    private String title;

    @Column(name="page")
    private int page;

    @Column(name="mark")
    private int mark;

    @ManyToOne
    @JoinColumn(name="booktype")
    private BookType bookType;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }


    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }
}
