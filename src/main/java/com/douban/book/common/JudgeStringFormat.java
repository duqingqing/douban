package com.douban.book.common;


import org.apache.commons.lang3.StringUtils;

public class JudgeStringFormat {
    //过滤表情
    public static String filterEmoji(String source,String slipStr) {
        if(StringUtils.isNotBlank(source)){
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
        }else{
            return source;
        }
    }

    public static void main(String[] args) {
         String afterFilterEmoji = JudgeStringFormat.filterEmoji("好文，已收藏谢谢肯定\uD83D\uDE1C要向你学习","");
        System.out.println(afterFilterEmoji);
    }
}
