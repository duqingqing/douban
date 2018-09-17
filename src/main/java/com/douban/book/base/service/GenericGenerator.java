package com.douban.book.base.service;

import com.douban.book.config.JpaConfiguration;
import com.douban.book.DoubanbookApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DoubanbookApplication.class)
@ContextConfiguration(classes = {JpaConfiguration.class})
public class GenericGenerator {

}