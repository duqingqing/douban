package com.douban.book.dao.book.information.domain;

import com.douban.book.base.domain.BaseEntity;
import com.douban.book.dao.book.type.domain.BookType;
import com.douban.book.dao.book.url.domain.BookUrl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.persistence.*;

@Entity
@Table(name = "book_information")
@Getter
@Setter
@Slf4j
public class Information extends BaseEntity {

    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "isbn")
    private String ISBN;
    @Column(name = "score")
    private double score;
    @Column(name = "bookReviewUrl")
    private String bookReviewUrl;
    @ManyToOne
    @JoinColumn(name = "booktype")
    private BookType bookType;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_url_id", nullable = true, unique = true)
    private BookUrl bookUrl;
    @Column(name = "mark")
    private int mark;
    @Column(name = "url")
    private String url;
}
