package com.douban.book.dao.comment.domain;

import com.douban.book.base.domain.BaseEntity;
import com.douban.book.dao.book.information.domain.Information;
import com.douban.book.dao.book.url.domain.BookUrl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.tool.schema.extract.spi.InformationExtractor;

import javax.persistence.*;

@Entity
@Table(name = "book_comment")
@Getter
@Setter
@ToString
public class Comment extends BaseEntity {
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;
    @Column(name = "star")
    private String star;
    @ManyToOne(cascade=CascadeType.REFRESH,optional=false)
    @JoinColumn(name="book_url_id",  nullable = false)
    private BookUrl bookUrl;
    @Column(name = "likes")
    private int likes;
    @Column(name = "mark")
    private int mark;
}
