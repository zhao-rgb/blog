package com.scs.web.blog.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author zhao
 * @className 图形工具类
 * @Description TODO
 * @Date 2019/11/20
 * @Version 1.0
 **/
public class ImageUtil {
    public static BufferedImage getImage(String content,int width,int height){
        BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        Color foreColor = new Color(26,160,52);

        Color bgColor = new Color(255, 245, 253);
        g.setColor(bgColor);
        g.fillRect(0,0,width,height);
        g.setPaint(foreColor);
        g.drawString(content,50,25);
        return  img;
    }

    public static void main(String[] args) throws Exception{
        //生成验证码
        String  code = StringUtil.getRandomCode();
        //生成图片
        BufferedImage img = ImageUtil.getImage(code,200,100);
        //通过输出流输出到指定目录
        File file = new File("D:/code.jpg");
        ImageIO.write(img,"jpg",file);

    }
}
