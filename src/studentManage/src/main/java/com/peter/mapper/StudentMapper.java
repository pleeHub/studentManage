package com.peter.mapper;

import com.peter.pojo.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据访问层 学生信息处理mapper接口
 *
 * @author Li Wang
 * @version 1.0.0
 * @Date 2022-11-18
 */
@Repository
public interface StudentMapper {

    /**
     * 学生数据插入
     *
     * @param student 学生信息包装类
     * @return 数据库添加结果，0表示添加失败，非0表示添加成功
     */
    int insert(Student student);

    /**
     * 学生数据删除（根据id）
     * 这个id映射mysql中的st_number
     *
     * @param id 需要删除的学生id
     * @return 数据库删除结果，0表示删除失败，非0表示删除成功
     */
    int deleteById(@Param("id") int id);

    /**
     * 学生数据删除（根据学生信息包装类的属性值）
     *
     * @param student 学生信息包装类（属性表示筛选信息，null不参与筛选）
     * @return 数据库删除结果，0表示删除失败，非0表示删除成功
     */
    int deleteByEntity(Student student);

    /**
     * 学生数据删除（根据map集合的val值）
     *
     * @param columnMap 筛选信息map集合
     *                  支持map的key值：number,name,sex,ageMax,ageMin,birthYear,birthMonth,birthDay
     * @return 数据库删除结果，0表示删除失败，非0表示删除成功
     */
    int deleteByMap(Map<String, Object> columnMap);

    /**
     * 数据信息修改
     *
     * @param student 属性包装类
     *                属性中的number为查询条件（不能为空）
     *                其他的属性为需要修改的目标（属性值表示修改后信息，null不参与修改）
     * @return 数据库修改结果，0表示修改失败，非0表示修改成功
     */
    int update(Student student);

    /**
     * 学生数据（全部查询）
     *
     * @return 数据库查询结果集
     */
    List<Student> selectList();

    /**
     * 查询学生数据（根据id）
     *
     * @param id 需要查询的学生id
     * @return 根据id返回的数据库查询结果集
     */
    Student selectById(@Param("id") int id);

    /**
     * 查询学生数据（根据id集合）
     *
     * @param idList 需要查询的学生id集合
     * @return 根据id集合返回的数据库查询结果集
     */
    List<Student> selectBatchList(List<Integer> idList);

    /**
     * 查询学生数据（根据学生信息包装类的属性值）
     *
     * @param student 学生信息包装类（属性表示筛选信息，null不参与筛选）
     * @return 根据包装类返回的数据库查询结果集
     */
    List<Student> selectByEntity(Student student);

    /**
     * 查询学生数据（根据map集合的val值）
     *
     * @param columnMap 筛选信息map集合
     *                  支持map的key值：number,name,sex,ageMax,ageMin,birthYear,birthMonth,birthDay
     * @return 根据map集合返回的数据库查询结果集
     */
    List<Student> selectByMap(Map<String, Object> columnMap);

    /**
     * 查询学生数据（根据姓名模糊查询）
     *
     * @param name 需要进行模糊查询的关键字
     * @return 根据姓名模糊查询返回的数据库查询结果集
     */
    List<Student> selectLikeByName(@Param("name") String name);

    /**
     * 查询学生数据（根据map集合模糊查询）
     *
     * @param columnMap 筛选信息map集合
     *                  支持map的key值：number,name,sex,ageMax,ageMin,birthYear,birthMonth,birthDay
     *                  其中name为模糊查询
     * @return 根据map集合模糊查询返回的数据库查询结果集
     */
    List<Student> selectLikeByMap(Map<String, Object> columnMap);

}

