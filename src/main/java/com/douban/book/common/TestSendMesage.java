package com.douban.book.common;

public class TestSendMesage {
    public static void main(String[] args) {
        SendMesage sendMesage = new SendMesage();
        try {
            sendMesage.sendInformationError("dulovefighting@sina.com");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
