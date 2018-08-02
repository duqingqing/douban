package com.douban.book.dao.book.type.domain;

import com.douban.book.base.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="book_type")
public class BookType extends BaseEntity {
    @Column(name="url")
    private String url;
    @Column(name="type")
    private String type;
    @Column(name="count")
    private int count;
    @Column(name="mark")
    private int mark;

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
