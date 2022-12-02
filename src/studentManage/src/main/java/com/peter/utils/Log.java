package com.peter.utils;

import org.springframework.stereotype.Component;

import java.io.File;

/**
 * log日志实现类
 *
 * @author Li Wang
 * @version 1.0.0
 * @Date 2022-11-19
 */
@Component()
public class Log {

    final static String studentLogPath = "D:\\studentManage_log";
    final static String saveFileName = studentLogPath + "\\saveLog.txt";

    /**
     * 日志目录检查
     */
    private static void makeFile() {
        File f = new File(studentLogPath);
        if (f.mkdir() == true) {
            System.out.println("已成功创建目录：" + studentLogPath);
        } else {
            System.out.println("无法创建目录：" + studentLogPath);
        }
    }

    /**
     * 登录反馈方法
     */
    public void LoginLog(String message) {
        makeFile();
        LogFactory.write(saveFileName, message);
    }

}
