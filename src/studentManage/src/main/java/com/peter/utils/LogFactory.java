package com.peter.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * log日志工厂类
 *
 * @author Li Wang
 * @version 1.0.0
 * @Date 2022-11-19
 */
public class LogFactory {

    /**
     * 修改标准输出流进行日志存入
     *
     * @param path    存入的位置
     * @param message 存入的内容
     */
    public static void write(String path, String message) {
        BufferedWriter writer = null;
        SimpleDateFormat Time1 = new SimpleDateFormat("yyyy-MM-dd,hh:mm:ss");
        String time = Time1.format(new Date());  //2020-05-02,05:03:18
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true), "gb2312"));
            String context = time + ":  " + message + "\n\n";
            char[] chars = context.toCharArray();
            writer.write(chars, 0, chars.length);
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
