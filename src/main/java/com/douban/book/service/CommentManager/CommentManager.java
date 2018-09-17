package com.douban.book.service.CommentManager;

import com.douban.book.base.service.GenericGenerator;
import com.douban.book.common.GetDocument;
import com.douban.book.common.GetNumberFromString;
import com.douban.book.common.JudgeStringFormat;
import com.douban.book.common.SendMesage;
import com.douban.book.dao.book.information.dao.InformationDao;
import com.douban.book.dao.book.information.domain.Information;
import com.douban.book.dao.book.type.dao.BookTypeDao;
import com.douban.book.dao.book.type.domain.BookType;
import com.douban.book.dao.book.url.dao.BookUrlDao;
import com.douban.book.dao.book.url.domain.BookUrl;
import com.douban.book.dao.comment.dao.CommentDao;
import com.douban.book.dao.comment.domain.Comment;
import com.douban.book.dao.ip.dao.IpDao;
import com.douban.book.dao.ip.domain.Ip;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.management.Query;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CommentManager extends GenericGenerator {
    @Autowired
    BookUrlDao bookUrlDao;
    @Autowired
    BookTypeDao bookTypeDao;
    @Autowired
    CommentDao commentDao;
    @Autowired
    IpDao ipDao;

    /**
     * 返回当前要获取的book_url_id的值
     */
    public Long idFactory() {
        Long bookUrlId = commentDao.findLastOne();
        int number = commentDao.getCommentsNumber(bookUrlId);
        log.info("book_url_id "+bookUrlId+"总共 "+number+" 条数据");
        if(number>=180){
            return ++bookUrlId;
        }else{
            commentDao.deleteCommentsByBookUrl(bookUrlId);
            log.info("不合理数据...删除book_url_id="+bookUrlId+"的数据");
            return  bookUrlId;
        }
    }

    @Test
    public void testIdFactory(){
        Long number = idFactory();
        System.out.println(number.intValue());
    }

    public void getoneComment(String url, BookUrl bookUrl) {
        String content = null;
        String star = null;
        Document document = null;
        int likes = 0;
        try {
            document = GetDocument.connect(url);
            Elements elements = document.select("#comments > ul > li");
            for (Element element : elements) {
                star = element.select("div > h3 > span.comment-info > span").attr("title");
                content = element.select("div.comment > p > span").text();
                likes = GetNumberFromString.getNumber(element.select("span[id^=\"c-\"]").text());
                System.out.println("【星级】" + star);
                System.out.println("【内容】" + content);
                System.out.println("【投票】" + likes);
                if (!(content.trim().equals(""))) {
                    Comment comment = new Comment();
                    comment.setContent(JudgeStringFormat.filterEmoji(content, ""));
                    comment.setLikes(likes);
                    comment.setStar(star);
                    comment.setBookUrl(bookUrl);
                    commentDao.save(comment);
                    System.out.println("comment saved successful !");
                    System.out.println("----------------------------------------------------");
                } else {
                    log.info("评论信息抓空");
                    try {
                        Thread.sleep(1000 * 60 * 1);
                    } catch (InterruptedException interrupted) {
                        interrupted.printStackTrace();
                    }
                }
            }
        } catch (NullPointerException nullPointer) {
            nullPointer.printStackTrace();
            log.info("无法访问当前评论页面");
            try {
                Thread.sleep(1000 * 60 * 1);
            } catch (InterruptedException interrupted) {
                interrupted.printStackTrace();
            }
        }
    }

    @Test
    public void getComment() {
        BookUrl bookUrl = null;
        int start = idFactory().intValue();
        for ( int i = start; i <= 1200; i++) {
            bookUrl = bookUrlDao.findByBookUrlId((long) i);
            String url = bookUrl.getBookUrl();
            System.out.println("【书本地址】" + url);
            String commentUrl = url + "comments/";
            try {
                Document document = GetDocument.connect(commentUrl);
                String body = document.select("body").text();
                if (body.equals("")) {
                    i--;
                    try {
                        log.info("Document的信息抓空");
                        Thread.sleep(1000 * 60 * 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                int pageSize = (((GetNumberFromString.getNumber(document.select("#total-comments").text()) / 20) + 1) > 10) ? 10 : 1;
                System.out.println(pageSize);
                for (int j = 1; j < pageSize; j++) {
                    String goalUrl = commentUrl + "hot?p=" + j;
                    System.out.println(goalUrl);
                    try {
                        getoneComment(goalUrl, bookUrl);
                    } catch (NullPointerException n) {
                        j--;
                        n.printStackTrace();
                        System.out.println("此条评论为空....................................");
                        continue;
                    }
                }
            } catch (NullPointerException ne) {
                ne.printStackTrace();
                try {
                    i--;
                    log.info("无法访问评论首页面");
                    Thread.sleep(1000 * 60 * 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
