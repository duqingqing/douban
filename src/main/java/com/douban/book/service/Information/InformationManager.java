package com.douban.book.service.Information;

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
import com.douban.book.dao.ip.dao.IpDao;
import com.douban.book.dao.ip.domain.Ip;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class InformationManager extends GenericGenerator {

    @Autowired
    InformationDao informationDao;
    @Autowired
    BookUrlDao bookUrlDao;
    @Autowired
    BookTypeDao bookTypeDao;
    @Autowired
    IpDao ipDao;

    @Test
    public void findByBookType() {
        BookType bookType = this.bookTypeDao.getByBookTypeById((long) 2);
        System.out.println(bookType.getType());
        List<BookUrl> bookUrlList = this.bookUrlDao.findByType(bookType);
        System.out.println(bookUrlList.size());
    }

    public Ip getIpByRandom() {
        Ip ip = null;
        List<Ip> ipList = this.ipDao.findAll();
        int index = (int) (Math.random() * ipList.size());
        ip = ipList.get(index);
        while (ip.getMark() == 1) {
            index = (int) (Math.random() * ipList.size());
            ip = ipList.get(index);
        }
        return ip;
    }

    @Test
    public void testIP() {
        Ip ip = null;
        ip = getIpByRandom();
        System.out.println(ip.getAddress());
        System.out.println(ip.getPort());
    }
    @Test
    public void getInformation() {
        Document document = null;
        outer:
        for (int k = 11; k <= 20; k++) {
            BookType bookType = this.bookTypeDao.getByBookTypeById((long) k);
            List<BookUrl> bookUrlList = this.bookUrlDao.findByType(bookType);
            for (int i = 0; i < bookUrlList.size(); i++) {
                BookUrl bookUrl = bookUrlList.get(i);
                if (bookUrl.getMark() == 0) {
                    String url = bookUrl.getBookUrl();
                    System.out.println("【书本地址】" + url);
                    String title = null;
                    String author = null;
                    String ISBN = null;
                    double score = 0;
                    String bookReviewUrl = null;
                    try {
                        document = GetDocument.connect(url);
                        title = document.select("#wrapper > h1 > span").text();
                        author = document.select("#info > a:nth-child(2)").text();
                        if (author.isEmpty()) {
                            author = document.select("#info > span:nth-child(1) > a").text();
                        }
                        String[] context = document.select("#info").text().split(" ");
                        ISBN = context[context.length - 1];
                        score = GetNumberFromString.getNumber(document.select("#interest_sectl > div > div.rating_self.clearfix > strong").text());
                        score = score / 10;
                        bookReviewUrl = document.select("#content > div > div.article > div.related_info > div.mod-hd > h2 > span.pl > a").attr("href");
                        System.out.println("************************************");
                        System.out.println("【标题】 " + title);
                        System.out.println("【作者】 " + author);
                        System.out.println("【豆瓣评分】 " + score);
                        System.out.println("【ISBN】 " + ISBN);
                        System.out.println("【短评链接】 " + bookReviewUrl);
                        Information information = new Information();
                        information.setTitle(title);
                        information.setAuthor(author);
                        information.setISBN(ISBN);
                        information.setUrl(url);
                        information.setScore(score);
                        information.setBookReviewUrl(bookReviewUrl);
                        information.setBookType(bookType);
                        information.setBookUrl(bookUrl);
                        if (!((ISBN.trim()).equals("")) && !((title.trim().equals("")))) {
                            bookUrlDao.updateBookUrlMark(1, bookUrl.getId());
                            informationDao.save(information);
                            System.out.println("Information saved successful !");
                            System.out.println("----------------------------------------------------");
                        }else{
                            try {
                                log.info("信息抓空");
                                Thread.sleep(1000 * 60 * 1);

                            }catch (InterruptedException interrupted){
                                interrupted.printStackTrace();
                            }
                        }
                    }catch(NullPointerException n){
                        System.err.println("ERROR ERROR ERROR ERROR ERROR ERROR ");
                        SendMesage sendMesage = new SendMesage();
                        try {
                            log.info("无法访问信息页面");
                            Thread.sleep(1000 * 60 * 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        n.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
        }
    }
}
