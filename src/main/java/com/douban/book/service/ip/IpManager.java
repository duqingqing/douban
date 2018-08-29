package com.douban.book.service.ip;

import com.douban.book.base.service.GenericGenerator;
import com.douban.book.common.GetDocument;
import com.douban.book.common.GetNumberFromString;
import com.douban.book.dao.ip.dao.IpDao;
import com.douban.book.dao.ip.domain.Ip;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class IpManager extends GenericGenerator {
    @Autowired
    IpDao ipDao;
    
    @Test
    public void addIp() {
        Document doc = null;
        Document doc2 = null;
        String address = null;
        List<Ip> ipList = new ArrayList<Ip>();
        int port = -1;
        try {
            for(int h=1;h<=10;h++) {
                String url = "http://www.xicidaili.com/wt/" + h;
                doc = GetDocument.connect(url);
                Elements elements = doc.select("#ip_list > tbody > tr");
                for (Element element : elements) {
                    String webAddress = element.select("td:nth-child(2)").text();
                    String webPort = element.select("td:nth-child(3)").text();
                    if (!webAddress.isEmpty()) {
                        address = webAddress;
                        port = GetNumberFromString.getNumber(webPort);
                        System.out.println(address);
                        System.out.println(port);

                        if (port != -1) {
                            doc2 = GetDocument.getDocumentByIP("https://book.douban.com/subject/30185326/", address, port);
                            if (doc2 != null) {
                                Ip ip = new Ip();
                                ip.setAddress(address);
                                ip.setPort(port);
                                ip.setUserful(1);
                                ipDao.save(ip);
                                System.out.println("SUCCESS !!!");
                            }else{
                                System.out.println("ERROR");
                            }
                            System.out.println("-----------------------------");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
