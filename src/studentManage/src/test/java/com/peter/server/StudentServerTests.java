package com.peter.server;

import com.github.pagehelper.PageInfo;
import com.peter.pojo.Student;
import com.peter.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;

/**
 * 数据访问层 学生信息处理server测试
 *
 * @author Li Wang
 * @version 1.0.0
 * @Date 2022-11-19
 */
@SpringBootTest
public class StudentServerTests {

    @Resource
    private StudentService studentService;

    @Test
    public void insert() {
        LocalDate localDate = LocalDate.now();
        Boolean sexNow = !(0 == 0);
        Student student1 = new Student(null, "李四6", true, 31, localDate, "备注信息3");
        Student student2 = new Student(null, "李四7", false, 32, localDate, "备注信息3");
        Student student3 = new Student(null, "李四8", false, 33, localDate, "备注信息3");
        Student student4 = new Student(null, "李四9", true, 34, localDate, "备注信息3");
        Student student5 = new Student(null, "李四10", true, 35, localDate, "备注信息3");

        boolean result1 = studentService.saveStudent(student1);
        System.out.println("单插入：" + (result1 ? "成功" : "失败"));

        List<Student> students = new ArrayList<>();
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        boolean result2 = studentService.saveStudent(students);
        System.out.println("批量插入：" + (result2 ? "成功" : "失败"));
    }

    @Test
    public void delete() {
        boolean result1 = studentService.renameById(104);
        System.out.println("单删除：" + (result1 ? "成功" : "失败"));

        List ids = Arrays.asList(106, 107, 108);
        boolean result2 = studentService.renameById(ids);
        System.out.println("批量删除：" + (result2 ? "成功" : "失败"));
    }

    @Test
    public void deleteCondition() {
        LocalDate localDate = LocalDate.now();
        Student student1 = new Student(null, "李四111", true, 40, localDate, null);
        boolean result1 = studentService.renameByEntity(student1);
        System.out.println("对象条件删除：" + (result1 ? "成功" : "失败"));

        Map<String, Object> map = new HashMap<>();
        map.put("number", 111);
        map.put("name", "李四10");
        map.put("sex", false);
        map.put("ageMin", 39);
        map.put("ageMax", 41);
        map.put("birthYear", 2022);
        map.put("birthMonth", 11);
        map.put("birthDay", 19);
        boolean result2 = studentService.renameByMap(map);
        System.out.println("map条件删除：" + (result2 ? "成功" : "失败"));
    }

    @Test
    public void update() {
        LocalDate localDate = LocalDate.now();
        Student student1 = new Student(113, "李四111", true, 49, localDate, "备注信息修改");
        boolean result1 = studentService.updateById(student1);
        System.out.println("信息修改：" + (result1 ? "成功" : "失败"));
    }

    @Test
    public void select() {
        System.out.println("全部查询===");
        List<Student> list1 = studentService.getList();
        list1.forEach(System.out::println);

        System.out.println("id查询===");
        Student student = studentService.getById(13);
        System.out.println(student);

        System.out.println("批量id查询===");
        List list2 = Arrays.asList(113, 114, 115);
        List<Student> list3 = studentService.getList(list2);
        list3.forEach(System.out::println);

        System.out.println("对象属性查询===");
        LocalDate localDate = LocalDate.now();
        Student student1 = new Student(113, "李四111", true, 49, localDate, "备注信息修改");
        List<Student> list4 = studentService.getList(student1);
        list4.forEach(System.out::println);

        System.out.println("map的val查询===");
        Map<String, Object> map = new HashMap<>();
        map.put("number", 111);
        map.put("name", "李四10");
        map.put("sex", false);
        map.put("ageMin", 39);
        map.put("ageMax", 41);
        map.put("birthYear", 2022);
        map.put("birthMonth", 11);
        map.put("birthDay", 19);
        List<Student> list5 = studentService.getList(map);
        System.out.println(list5);
    }

    @Test
    public void selectLike() {
//        List<Student> list1 = studentService.getLikeByName("1");
//        list1.forEach(System.out::println);

        Map<String, Object> map = new HashMap<>();
//        map.put("number", 111);
//        map.put("name", "李四10");
//        map.put("sex", false);
        map.put("ageMin", 80);
        map.put("ageMax", 20);
//        map.put("birthYear", 2022);
//        map.put("birthMonth", 11);
//        map.put("birthDay", 19);
        List<Student> list2 = studentService.getLikeByName(map);
        list2.forEach(System.out::println);
    }

    @Test
    public void selectPage() {
        PageInfo pageList1 = studentService.getPageList(3, 3);
        System.out.println(pageList1);

        List list = Arrays.asList(113, 114, 115);
        PageInfo pageList2 = studentService.getPageList(2, 3, list);
        System.out.println(pageList2);

        LocalDate localDate = LocalDate.now();
        Student student = new Student(113, "李四111", true, 49, localDate, "备注信息修改");
        PageInfo pageList3 = studentService.getPageList(2, 3, student);
        System.out.println(pageList3);

        Map<String, Object> map = new HashMap<>();
        map.put("number", 111);
        map.put("name", "李四10");
        map.put("sex", false);
        map.put("ageMin", 39);
        map.put("ageMax", 41);
        map.put("birthYear", 2022);
        map.put("birthMonth", 11);
        map.put("birthDay", 19);
        PageInfo pageList4 = studentService.getPageList(2, 3, map);
        System.out.println(pageList4);
    }
}
