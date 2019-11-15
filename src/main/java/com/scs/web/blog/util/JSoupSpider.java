package com.scs.web.blog.util;

import com.scs.web.blog.entity.Article;
import com.scs.web.blog.entity.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.Doc;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author zhao
 * @className JSoupSpider
 * @Description TODO
 * @Date 2019/11/7
 * @Version 1.0
 **/
public class JSoupSpider {
//    //静态的公有无惨方法，方法名自定，返回List<Student>
//    public static List<Student> getStudent(){
//        //声明文档变量
//        Document document = null;
//        //通过JSoup连接目标页面
//        try {
//            document = Jsoup.connect("https://www.jianshu.com/recommendations/users?utm_source=desktop&utm_medium=index-users").get();
//        } catch (IOException e) {
//            System.out.println("连接失败");
//        }
//        //选取所有class为col-xs-8的元素集合
//        Elements divs = document.getElementsByClass("col-xs-8");
//        //创建集合，建议给定初始化大小
//        List<Student> studentList = new ArrayList<>(divs.size());
//        //对div遍历
//        divs.forEach(div->{
//            //取出class为wrap的节点
////            Element wrapDiv = div.child(0);
////            Element link = wrapDiv.child(0);
////            Elements linkChildren = link.children();
//            Element wrapDiv = div.child(0);
//            Element avatar = wrapDiv.child(0).child(0);
//            Element name = wrapDiv.child(0).child(1);
//            Element description = wrapDiv.child(0).child(2);
//
//            Student student = new Student();
//            student.setUsername(name.text());
//            student.setAvatar("http:" + avatar.attr("src"));
//            student.setCreateTime(LocalDateTime.now());
//            student.setDescription(description.text());
//            studentList.add(student);
//        });
//        return studentList;
//    }


    private static Logger logger = LoggerFactory.getLogger(JSoupSpider.class);

    public static List<User> getUsers() {
        Document document = null;
        List<User> userList = new ArrayList<>(100);
        for (int i = 1; i <= 3; i++) {
            try {
                document = Jsoup.connect("https://www.jianshu.com/recommendations/users?utm_source=desktop&utm_medium=index-users&page=" + i).get();
            } catch (IOException e) {
                logger.error("连接失败");
            }
            Elements divs = document.getElementsByClass("col-xs-8");
            divs.forEach(div -> {
                Element wrapDiv = div.child(0);
                Element link = wrapDiv.child(0);
                Elements linkChildren = link.children();
                User user = new User();
                user.setMobile(DataUtil.getMobile());
                user.setPassword(DataUtil.getPassword());
                user.setGender(DataUtil.getGender());
                user.setAvatar("https:" + linkChildren.get(0).attr("src"));
                user.setNickname(linkChildren.get(1).text());
                user.setIntroduction(linkChildren.get(2).text());
                user.setBirthday(DataUtil.getBirthday());
                user.setCreateTime(LocalDateTime.now());
                userList.add(user);
            });
        }
        return userList;
    }


    public static List<Article> getArticle() {
        Document document = null;
        List<Article> articleList = new ArrayList<>(100);
        int j = 0;
        for (int i = 0; i <= 180; ) {
            try {
                document = Jsoup.connect("https://book.douban.com/review/best/?start=" + i).get();
            } catch (IOException e) {
                logger.error("连接失败");
            }
            Elements mainList = document.getElementsByClass("main review-item");
            mainList.forEach(item -> {
                String cover = item.child(0).child(0).attr("src");
                String nickName = item.child(1).child(1).text();
                String publishTime = null;
                if (item.child(1).children().size() == 4) {
                    publishTime = item.child(1).child(3).text();
                } else {
                    publishTime = item.child(1).child(2).text();
                }

                String title = item.child(2).child(0).child(0).text();
                String content = item.child(2).child(1).text().substring(14);
                String likes = item.child(2).child(3).child(0).text();
                String co = item.child(2).child(3).child(2).text();
                String comments = co.substring(0, co.length() - 2);

                Article article = new Article();
                article.setTitle(title);
                article.setContent(content);
                article.setCover(cover);
                article.setDiamond(new Random().nextInt(100));

                article.setComments(Integer.valueOf(comments));
                article.setLikes(Integer.valueOf(likes));
                article.setPublishTime(Timestamp.valueOf(publishTime).toLocalDateTime());
                articleList.add(article);
            });
            j++;
            i = 2 * j * 10;
        }
        return articleList;
    }

}



