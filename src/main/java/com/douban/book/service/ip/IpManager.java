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

import java.io.*;
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
            for(int h=1;h<=50;h++) {
                String url = "http://www.xicidaili.com/nt/" + h;
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
                            Ip ip = new Ip();
                            ip.setAddress(address);
                            ip.setPort(port);
                            ipList.add(ip);
                            System.out.println("-----------------------------");
                        }
                    }
                }
                for (int i = 0; i < ipList.size(); i++) {
                    String address2 = ipList.get(i).getAddress();
                    int port2 = ipList.get(i).getPort();
                    doc2 = GetDocument.getDocumentByIP("https://book.douban.com/subject/30185326/", address2, port2);
                    if (doc2 != null) {
                        ipList.get(i).setUserful(1);
                        ipDao.save(ipList.get(i));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
