package com.douban.book.service.CommentManager;

import com.douban.book.base.service.GenericGenerator;
import com.douban.book.common.GetDocument;
import com.douban.book.common.GetNumberFromString;
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
    public void getoneComment(String url,BookUrl bookUrl) throws NullPointerException{
        String content=null;
        String star=null;
        Document document = null;
        int likes=0;
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
                    Comment comment = new Comment();
                    comment.setContent(content);
                    comment.setLikes(likes);
                    comment.setStar(star);
                    comment.setBookUrl(bookUrl);
                    commentDao.save(comment);
                    System.out.println("comment saved successful !");
                    System.out.println("----------------------------------------------------");
                }
            }catch(NullPointerException e){
            System.err.println("Comment error");
            throw new RuntimeException(e);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    @Test
    public void getComment() {
        outer:
        for (int k = 2; k <= 146; k++) {
            BookType bookType = this.bookTypeDao.getByBookTypeById((long) k);
            List<BookUrl> bookUrlList = this.bookUrlDao.findByType(bookType);
            for (int i = 0; i < bookUrlList.size(); i++) {
                BookUrl bookUrl = bookUrlList.get(i);
                String url = bookUrl.getBookUrl();
                System.out.println("【书本地址】" + url);
                String commentUrl = url + "comments/";
                try {
                    Document document = GetDocument.connect(commentUrl);
                    int pageSize = (GetNumberFromString.getNumber(document.select("#total-comments").text()) / 20) + 1;
                    for (int j = 1; j < pageSize; j++) {
                        String goalUrl = commentUrl + "hot?p=" + j;
                        System.out.println(goalUrl);
                        try {
                            getoneComment(goalUrl, bookUrl);
                        } catch (NullPointerException n) {
                            n.printStackTrace();
                            SendMesage sendMesage = new SendMesage();
                            try {
                                sendMesage.sendCommentError("dulovefighting@sina.com");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break outer;
                        }
                    }
                }catch (NullPointerException ne){
                    ne.printStackTrace();
                    SendMesage sendMesage = new SendMesage();
                    try {
                        sendMesage.sendCommentError("dulovefighting@sina.com");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break outer;
                }
            }
        }
    }
}
