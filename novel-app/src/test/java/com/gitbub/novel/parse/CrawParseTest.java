package com.gitbub.novel.parse;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.github.biz.parse.Parser;

/**
 * @author:chyl2005
 * @date:17/12/2
 * @time:16:52
 * @desc:描述该类的作用
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationcontext.xml"})
public class CrawParseTest {


    @Autowired
    private Parser parser;

    @Test
    public void novelParse(){
        String url="http://www.7kankan.com/files/article/info/64/64362.htm";
        String updateurl="http://www.7kankan.com/files/article/toplastupdate/0/1.htm";
        boolean matches = url.matches("http\\://www.7kankan.com/files/article/info/\\d+/\\d+.htm");
        parser.parse(updateurl);
    }
}
