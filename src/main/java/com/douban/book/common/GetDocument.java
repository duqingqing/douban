package com.douban.book.common;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class GetDocument {
    public static Document connect(String url) {
        Document document = null;
        int count = 1;
        while (count <= 2) {
            try {
                document = Jsoup
                        .connect(url)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("Accept-Encoding", "gzip, deflate, br")
                        .header("Accept-Language", "zh-CN,zh;q=0.9")
                        .header("Cache-Control", "max-age=0")
                        .header("Connection", "keep-alive")
                        .header("Upgrade-Insecure-Requests", "1")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36")
                        .timeout(3000)
                        .get();
                return document;
            } catch (IOException e1) {
                count++;
            }
        }
        return null;
    }

    public static Document getDocumentByIP(String href,String address,int port) throws SocketTimeoutException {
        Document doc =null;
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(address, port));
            URL url = new URL(href);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection(proxy);
            urlcon.setConnectTimeout(3000);
            urlcon.setReadTimeout(3000);
            urlcon.connect();         //获取连接
            InputStream is = urlcon.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
            StringBuffer bs = new StringBuffer();
            String line = null;
            while ((line = buffer.readLine()) != null) {
                bs.append(line);
            }
             doc = Jsoup.parse(bs.toString());
        } catch (SocketTimeoutException e) {
            throw new RuntimeException(e);
        }finally {
            return doc;
        }
    }
}
