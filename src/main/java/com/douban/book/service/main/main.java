package com.douban.book.service.main;

import com.douban.book.dao.book.information.domain.Information;
import com.douban.book.service.CommentManager.CommentManager;
import com.douban.book.service.Information.InformationManager;
import org.springframework.beans.factory.annotation.Autowired;

public class main {

    public static void main(String[] args) {
      CommentManager commentManager = new CommentManager();
      InformationManager informationManager = new InformationManager();
      commentManager.getComment();
      informationManager.getInformation();
    }
}
