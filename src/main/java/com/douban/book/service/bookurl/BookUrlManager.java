package com.douban.book.service.bookurl;

import com.douban.book.base.service.GenericGenerator;
import com.douban.book.common.GetDocument;
import com.douban.book.dao.book.type.dao.BookTypeDao;
import com.douban.book.dao.book.type.domain.BookType;
import com.douban.book.dao.book.url.dao.BookUrlDao;
import com.douban.book.dao.book.url.domain.BookUrl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.Column;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

public class BookUrlManager extends GenericGenerator {
    @Autowired
    BookUrlDao bookUrlDao;
    @Autowired
    BookTypeDao bookTypeDao;
    @Test
    public void getBookUrlByType(){
        List<BookUrl> bookUrlList = new ArrayList<BookUrl>();
        List<BookType> bookTypeList  = bookTypeDao.findAll();
        for(int j=0;j<bookTypeList.size();j++) {
            String typeUrl = bookTypeList.get(j).getUrl();
            Document firstDocument = GetDocument.connect(typeUrl);
            int totalPage = Integer.parseInt(firstDocument.select("#subject_list > div.paginator > a").last().text());
            for (int i = 0; i <= (totalPage * 20); i += 20) {
                String bookInformationUrl = typeUrl + "?start=" + i + "&type=T";
                Document secondDocument = GetDocument.connect(bookInformationUrl);
                try {
                    Elements elements = secondDocument.select("#subject_list > ul > li");
                    for (Element element : elements) {
                        String bookurl = element.select("div.info > h2 >a").attr("href");
                        String title = element.select("div.info > h2 >a").text();
                        System.out.println(title);
                        System.out.println(bookurl);
                        BookUrl bookUrl = new BookUrl();
                        bookUrl.setBookType(bookTypeList.get(j));
                        bookUrl.setMark(0);
                        bookUrl.setBookUrl(bookurl);
                        bookUrl.setTitle(title);
                        bookUrl.setPage((i / 20) + 1);
                        bookUrlList.add(bookUrl);
                        if(bookUrlList.size()>49) {
                            this.bookUrlDao.saveAll(bookUrlList);
                            bookUrlList.clear();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
