package com.scs.web.blog.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @author zhao
 * @className GraphicHelper
 * @Description TODO
 * @Date 2019/11/19
 * @Version 1.0
 **/
public class GraphicHelper {
    private static OutputStream outputStream;

    public static void setOutputStream(OutputStream outputStream) {
        GraphicHelper.outputStream = outputStream;
    }

    public static String create(final int width, final int height, final String imgType) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = image.getGraphics();
        graphic.setColor(Color.getColor("F8F8F8"));
        graphic.fillRect(0, 0, width, height);
        Color[] colors = new Color[]{Color.BLUE, Color.GRAY, Color.GREEN, Color.RED, Color.BLACK, Color.ORANGE,
                Color.CYAN};
        for (int i = 0; i < 50; i++) {
            graphic.setColor(colors[random.nextInt(colors.length)]);
            final int x = random.nextInt(width);
            final int y = random.nextInt(height);
            final int w = random.nextInt(20);
            final int h = random.nextInt(20);
            final int signA = random.nextBoolean() ? 1 : -1;
            final int signB = random.nextBoolean() ? 1 : -1;
            graphic.drawLine(x, y, x + w * signA, y + h * signB);
        }

        graphic.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        for (int i = 0; i < 6; i++) {
            final int temp = random.nextInt(26) + 97;
            String s = String.valueOf((char) temp);
            sb.append(s);
            graphic.setColor(colors[random.nextInt(colors.length)]);
            /* 绘制一段文本，其中 (x, y) 坐标指的是文本序列的 左下角 的位置*/
            graphic.drawString(s, i * (width / 6), height - (height / 3));
        }
        graphic.dispose();
        try {
            File file = new File("D:\\code.jpg");
//            OutputStream out = new FileOutputStream(file);
            ImageIO.write(image, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
