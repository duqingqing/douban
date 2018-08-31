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
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Optional;

public class CommentManager extends GenericGenerator {
    @Autowired
    BookUrlDao bookUrlDao;
    @Autowired
    BookTypeDao bookTypeDao;
    @Autowired
    CommentDao commentDao;
    @Autowired
    IpDao ipDao;

    public Ip getIpByRandom() {
        Ip ip = new Ip();
        List<Ip> ipList = ipDao.findAll();
        int length = ipList.size();
        int index = (int) (Math.random() * length);
        ip = ipList.get(index);
        while (ip.getMark() == 1) {
            index = (int) (Math.random() * length);
            ip = ipList.get(index);
        }
        return ip;
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
                if (!(content.trim().equals("")) && !(star.trim().equals(""))) {
                    Comment comment = new Comment();
                    comment.setContent(JudgeStringFormat.filterEmoji(content, ""));
                    comment.setLikes(likes);
                    comment.setStar(star);
                    comment.setBookUrl(bookUrl);
                    commentDao.save(comment);
                    System.out.println("comment saved successful !");
                    System.out.println("----------------------------------------------------");
                }
            }
        } catch (NullPointerException nullPointer) {
            nullPointer.printStackTrace();
            System.out.println("休眠1 ........-_- ........-_- ........-_- ........-_- ........-_- ........-_- ........-_- ........-_-");
            try {
                Thread.sleep(1000 * 60 * 10);
            } catch (InterruptedException interrupted) {
                interrupted.printStackTrace();
            }
        }
    }

    @Test
    public void getComment() {
        BookUrl bookUrl = null;
        for (int i = 1386; i <= 1400; i++) {
//            Optional<BookUrl> bookUrlOptional = bookUrlDao.findById((long) i);
//            if (bookUrlOptional.isPresent()) {
//                bookUrl = bookUrlOptional.get();
//            }
            bookUrl = bookUrlDao.findByBookUrlId((long)i);
            String url = bookUrl.getBookUrl();
            System.out.println("【书本地址】" + url);
            String commentUrl = url + "comments/";
            try {
                Document document = GetDocument.connect(commentUrl);
                int pageSize = (((GetNumberFromString.getNumber(document.select("#total-comments").text()) / 20) + 1) > 5) ? 5 : 1;
                System.out.println(pageSize);
                for (int j = 1; j < pageSize; j++) {
                    String goalUrl = commentUrl + "hot?p=" + j;
                    System.out.println(goalUrl);
                    try {
                        getoneComment(goalUrl, bookUrl);
                    } catch (NullPointerException n) {
                        n.printStackTrace();
                        System.out.println("此条评论为空....................................");
                        continue;
                    }
                }
            } catch (NullPointerException ne) {
                ne.printStackTrace();
                SendMesage sendMesage = new SendMesage();
                try {
                    sendMesage.sendCommentError("dulovefighting@sina.com");
                    System.out.println("休眠2 ........-_- ........-_- ........-_- ........-_- ........-_- ........-_- ........-_- ........-_-");
                    Thread.sleep(1000 * 60 * 10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
