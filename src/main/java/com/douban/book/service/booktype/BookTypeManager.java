package com.douban.book.service.booktype;

import com.douban.book.base.service.GenericGenerator;
import com.douban.book.common.GetDocument;
import com.douban.book.common.GetNumberFromString;
import com.douban.book.dao.book.type.dao.BookTypeDao;
import com.douban.book.dao.book.type.domain.BookType;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookTypeManager extends GenericGenerator {

    @Autowired
    BookTypeDao bookTypeDao;
    @Test
    public void updateMark()
    {
        for(int i=1;i<=145;i++) {
            this.bookTypeDao.updateBookTypeUrlMark(0,(long)i);
        }
    }
    @Test
    public void findAllType()
    {
        List<BookType> bookTypes = bookTypeDao.findAll();
        System.out.println("所有分类共"+bookTypes.size());
    }
    @Test
    public void getType()
    {
        String typeHome = "https://book.douban.com";
        String url = "https://book.douban.com/tag/?view=type&icn=index-sorttags-hot#%E6%96%87%E5%AD%A6";
        Document document = GetDocument.connect(url);
        Elements elements = document.select("#content > div > div.article > div:nth-child(2) > div > table.tagCol >tbody > tr > td");
        for(Element element :elements)
        {
            String typrUrl = element.select("a").attr("href");
            int count = GetNumberFromString.getNumber(element.select("b").text());
            String type = element.select("a").text();
            if(!type.isEmpty()) {
                System.out.println(type);
                System.out.println(count);
                System.out.println(typeHome+typrUrl);

            BookType bookType = new BookType() ;
            bookType.setUrl(typeHome+typrUrl);
            bookType.setCount(count);
            bookType.setType(type);
            bookTypeDao.save(bookType);
            }
        }
    }
}
