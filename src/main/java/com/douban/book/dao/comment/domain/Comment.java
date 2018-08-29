package com.douban.book.dao.comment.domain;

import com.douban.book.base.domain.BaseEntity;
import com.douban.book.dao.book.information.domain.Information;
import com.douban.book.dao.book.url.domain.BookUrl;
import org.hibernate.tool.schema.extract.spi.InformationExtractor;

import javax.persistence.*;

@Entity
@Table(name = "book_comment")
public class Comment extends BaseEntity {
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "star")
    private String star;

    @ManyToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "information_id", nullable = false)
    private Information information;

    @Column(name = "likes")
    private int likes;

    @Column(name = "mark")
    private int mark;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public Information getInformation() { return information; }

    public void setInformation(Information information) { this.information = information; }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
