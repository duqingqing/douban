package com.douban.book.dao.book.url.domain;

import com.douban.book.base.domain.BaseEntity;
import com.douban.book.dao.book.type.domain.BookType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.persistence.*;
@Getter
@Setter
@Slf4j
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
}
