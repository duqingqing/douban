package com.douban.book.dao.ip.domain;



import com.douban.book.base.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="ip")
public class Ip extends BaseEntity {
    private String address;
    private int port;
    private int mark;
    private int userful;

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getUserful() {
        return userful;
    }

    public void setUserful(int userful) {
        this.userful = userful;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
