package com.douban.book.dao.book.type.domain;

import com.douban.book.base.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="book_type")
@Getter
@Setter
@Slf4j
public class BookType extends BaseEntity {
    @Column(name="url")
    private String url;
    @Column(name="type")
    private String type;
    @Column(name="count")
    private int count;
    @Column(name="mark")
    private int mark;
}
