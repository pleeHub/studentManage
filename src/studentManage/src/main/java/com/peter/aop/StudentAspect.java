package com.peter.aop;

import com.peter.pojo.Student;
import com.peter.utils.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * student层的aop切面类
 *
 * @author Li Wang
 * @version 1.0.0
 * @Date 2022-11-19
 */
@Aspect
@Component
public class StudentAspect {

    @Autowired
    private Log log;

    @Pointcut(value = "execution(* com.peter.service.impl.StudentServiceImpl.save*(..))")
    public void saveStudent() {
        System.out.println("学生信息“添加”方法执行。。。");
    }

    /**
     * 对学生信息“添加时”产生对应日志操作
     * 通过JoinPoint对象获取到“目标对象”的参数信息
     * 并对参数信息进行类型判断，和res的插入结果判断
     * 如果是对象：则会直接相应应信息进行日志写入
     * 如果是集合：则会直接相对应信息进行stringBuilder和列表循环进行字符串拼接，最后日志写入
     *
     * @param jp  JoinPoint对象
     * @param res ”目标对象“返回值（true or false）
     */
    @AfterReturning(value = "saveStudent()", returning = "res")
    public void saveAfterReturning(JoinPoint jp, Boolean res) {

        Object args[] = jp.getArgs();
        Object arg = args[0];

        if (arg instanceof Student) {
            System.out.println("进入saveStudent(Student entity)方法的切面日志处理。。。");
            if (res) {
                log.LoginLog("学生信息添加成功，学生信息如下：\n        " + arg.toString());
            } else {
                log.LoginLog("姓名为 " + ((Student) arg).getName() + " 学生的信息添加失败");
            }
        }
        if (arg instanceof List) {
            System.out.println("进入saveStudent(List<Student> entityList)方法的切面日志处理。。。");
            if (res) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("批量学生信息添加成功，学生信息如下：\n");
                for (Student student : (List<Student>) arg) {
                    stringBuilder.append("        " + student.toString() + "\n");
                }
                log.LoginLog(stringBuilder.toString());
            } else {
                log.LoginLog("批量学生的信息添加失败");
            }
        }
    }

}


