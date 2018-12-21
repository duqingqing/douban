package com.douban.book.common;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Auther: duqingqing
 * @Date: 18-12-21 11:01
 * @Description:
 */
public class NetStateTest {

    public boolean isConnect(){
        boolean connect = false;
        Runtime runtime = Runtime.getRuntime();
        Process process;
        try {
            process = runtime.exec("ping -c 3 www.baidu.com");
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            System.out.println("返回值为:"+sb);
            is.close();
            isr.close();
            br.close();
            if (!sb.toString().equals("")) {
                String logString = "";
                if (sb.toString().indexOf("ttl") > 0) {
                    // 网络畅通
                    connect = true;
                } else {
                    // 网络不畅通
                    connect = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connect;
    }

    public static void main(String[] args) {
        NetStateTest netState = new NetStateTest();
        while (!netState.isConnect()){
            try {
                System.out.println("网络不好停止一会");
                Thread.sleep(6 * 1000);
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
        System.out.println("网络正常开始执行");

    }

}

