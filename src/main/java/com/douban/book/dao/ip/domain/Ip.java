package com.douban.book.dao.ip.domain;



import com.douban.book.base.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="ip")
@Getter
@Setter
@ToString
public class Ip extends BaseEntity {
    private String address;
    private int port;
    private int mark;
    private int userful;
}
