package com.scs.web.blog.util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author zhao
 * @className 验证码生成
 * @Description TODO
 * @Date 2019/11/19
 * @Version 1.0
 **/
public class VerifyCode {
    public static BufferedImage createImage(int width, int height, String s) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) img.getGraphics();
        graphics.setBackground(Color.GRAY);
        graphics.fillRect(0, 0, width, height);
        graphics.setPaint(Color.CYAN);
        Font font = new Font("Serif", Font.BOLD, 10);
        graphics.setFont(font);
        graphics.drawString(s, width / 6, height / 3);
        return img;
    }
}
