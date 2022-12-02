package com.peter.mapper;

import com.peter.pojo.Student;
import com.sun.xml.internal.bind.v2.TODO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据访问层 学生信息处理mapper测试
 *
 * @author Li Wang
 * @version 1.0.0
 * @Date 2022-11-18
 */
@SpringBootTest
public class StudentMapperTests {

    @Resource
    private StudentMapper studentMapper;

    @Test
    public void insert() {
        LocalDate localDate = LocalDate.now();
        Student student = new Student(null, "王五", true, 20, localDate, "备注信息3");
        int insert = studentMapper.insert(student);
        System.out.println(insert);
    }

    @Test
    public void deleteById() {
        int deleteById = studentMapper.deleteById(9);
        System.out.println(deleteById);
    }

    @Test
    public void deleteByEntity() {
        LocalDate localDate = LocalDate.now();
        Student student = new Student(null, "王五", true, 222, localDate, "备注信息3");
        int i = studentMapper.deleteByEntity(student);
        System.out.println(i);
    }

    @Test
    public void deleteByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("number", 14);
        map.put("name", "ll");
        map.put("sex", false);
        map.put("ageMin", 221);
        map.put("ageMax", 223);
        map.put("birthYear", 2022);
        map.put("birthMonth", 11);
        map.put("birthDay", 18);
        int i = studentMapper.deleteByMap(map);
        System.out.println(i);
    }

    @Test
    public void update() {
        LocalDate localDate = LocalDate.now();
        Student student = new Student(12, "王五", false, 222, localDate, null);
        int i = studentMapper.update(student);
        System.out.println(i);
    }

    @Test
    public void selectAll() {
        List<Student> selects = studentMapper.selectList();
        for (Student student : selects) {
            System.out.println(student);
        }
    }

    @Test
    public void selectById() {
        Student student = studentMapper.selectById(17);
        System.out.println(student);
    }

    @Test
    public void selectBatchList() {
        List list = new ArrayList();
        list.add(17);
        list.add(18);
        list.add(19);
        List<Student> selects = studentMapper.selectBatchList(list);
        for (Student student : selects) {
            System.out.println(student);
        }
    }

    @Test
    public void selectByEntity() {
        LocalDate localDate = LocalDate.now();
        Student student1 = new Student(null, "王五", true, 111, localDate, "备注信息3");
        List<Student> selects = studentMapper.selectByEntity(student1);
        for (Student student : selects) {
            System.out.println(student);
        }
    }

    @Test
    public void selectByMap() {
        Map<String, Object> map = new HashMap<>();
//        map.put("number",14);
        map.put("name", "l");
//        map.put("sex",false);
        map.put("ageMin", 221);
        map.put("ageMax", 223);
        map.put("birthYear", 2022);
        map.put("birthMonth", 11);
        map.put("birthDay", 18);
        List<Student> selects = studentMapper.selectByMap(map);
        for (Student student : selects) {
            System.out.println(student);
        }
    }

    @Test
    public void selectLikeByName() {
        List<Student> selects = studentMapper.selectLikeByName("张");
        for (Student student : selects) {
            System.out.println(student);
        }
    }

    @Test
    public void selectLikeByMap() {
        Map<String, Object> map = new HashMap<>();
//        map.put("number",14);
        map.put("name", "l");
//        map.put("sex",false);
        map.put("ageMin", 221);
        map.put("ageMax", 223);
        map.put("birthYear", 2022);
        map.put("birthMonth", 11);
        map.put("birthDay", 18);
        List<Student> selects = studentMapper.selectLikeByMap(map);
        for (Student student : selects) {
            System.out.println(student);
        }
    }

}
