package com.peter.pojo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * 学生基本信息实体类
 * 对应MySQL基本属性：
 * 1、序号(number) 自增
 * 2、姓名(name)（字符串）
 * 3、性别(sex)（位类型）
 * 4、年龄(age)（数字）
 * 5、出生年月(birth)（日期）
 * 6、备注(remarks)（长文本）
 *
 * @author Li Wang
 * @version 1.0.0
 * @Date 2022-11-18
 */
public class Student implements Serializable {

    //学生序号
    private Integer number;

    //学生姓名
    private String name;

    //学生性别（false对应0, 即表示女性; true对应非0, 即表示男性）
    private Boolean sex;

    //学生年龄
    private Integer age;

    //学生出生日期
    private LocalDate birth;

    //学生备份信息
    private String remarks;

    public Student() {
    }

    public Student(Integer number, String name, Boolean sex, Integer age, LocalDate birth, String remarks) {
        this.number = number;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.birth = birth;
        this.remarks = remarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(number, student.number) &&
                Objects.equals(name, student.name) &&
                Objects.equals(sex, student.sex) &&
                Objects.equals(age, student.age) &&
                Objects.equals(birth, student.birth) &&
                Objects.equals(remarks, student.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, name, sex, age, birth, remarks);
    }

    @Override
    public String toString() {
        String sex_srt = "女";
        if (sex) {
            sex_srt = "男";
        }
        return "学生信息{" +
                "序号=" + number +
                ", 姓名='" + name + '\'' +
                ", 性别=" + sex_srt +
                ", 年龄=" + age +
                ", 出生日期=" + birth +
                ", 备注信息='" + remarks + '\'' +
                '}';
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
