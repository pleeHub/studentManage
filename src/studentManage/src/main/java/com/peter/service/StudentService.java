package com.peter.service;

import com.github.pagehelper.PageInfo;
import com.peter.pojo.Student;

import java.util.List;
import java.util.Map;

/**
 * 业务逻辑层 学生信息处理service接口
 *
 * @author Li Wang
 * @version 1.0.0
 * @Date 2022-11-19
 */
public interface StudentService {

    /**
     * 学生数据插入
     *
     * @param entity 学生信息包装类
     * @return 数据添加结果
     */
    boolean saveStudent(Student entity);

    /**
     * 学生数据插入（批量）
     *
     * @param entityList 学生信息包装类集合
     * @return 数据添加结果
     */
    boolean saveStudent(List<Student> entityList);

    /**
     * 学生数据删除（根据id）
     *
     * @param id 需要删除的学生id
     * @return 数据删除结果
     */
    boolean renameById(int id);

    /**
     * 学生数据删除（根据id集合批量）
     *
     * @param idList 需要删除的学生id集合
     * @return 数据删除结果
     */
    boolean renameById(List<Integer> idList);

    /**
     * 学生数据删除（根据学生信息包装类的属性值）
     *
     * @param entity 学生信息包装类（属性表示筛选信息，null不参与筛选）
     * @return 数据删除结果
     */
    boolean renameByEntity(Student entity);

    /**
     * 学生数据删除（根据map集合的val值）
     *
     * @param columnMap 筛选信息map集合
     *                  支持map的key值：number,name,sex,ageMax,ageMin,birthYear,birthMonth,birthDay
     * @return 数据删除结果
     */
    boolean renameByMap(Map<String, Object> columnMap);

    /**
     * 学生信息修改
     *
     * @param entity 属性包装类
     *               属性中的number为查询条件（不能为空）
     *               其他的属性为需要修改的目标（属性值表示修改后信息，null不参与修改）
     * @return 信息修改结果
     */
    boolean updateById(Student entity);

    /**
     * 学生数据全部获取
     *
     * @return 数据查询结果集
     */
    List<Student> getList();

    /**
     * 获取学生数据（根据id）
     *
     * @param id 需要获取的学生id
     * @return 根据id返回的对应学生数据对象
     */
    Student getById(int id);

    /**
     * 获取学生数据（根据id集合）
     *
     * @param idList 需要获取的学生id集合
     * @return 根据id返回的对应学生数据对象集合
     */
    List<Student> getList(List<Integer> idList);

    /**
     * 获取学生数据（根据学生信息包装类的属性值）
     *
     * @param entity 学生信息包装类（属性表示筛选信息，null不参与筛选）
     * @return 根据包装类返回的对应学生数据集合
     */
    List<Student> getList(Student entity);

    /**
     * 获取学生数据（根据map集合的val值）
     *
     * @param columnMap 筛选信息map集合
     *                  支持map的key值：number,name,sex,ageMax,ageMin,birthYear,birthMonth,birthDay
     * @return 根据map集合返回的对应学生数据集合
     */
    List<Student> getList(Map<String, Object> columnMap);

    /**
     * 获取学生数据（根据姓名模糊查询）
     *
     * @param name 需要进行模糊查询的关键字
     * @return 根据姓名模糊查询返回的对应学生数据集合
     */
    List<Student> getLikeByName(String name);

    /**
     * 获取学生数据（根据姓名模糊查询）
     *
     * @param columnMap 筛选信息map集合
     *                  支持map的key值：number,name,sex,ageMax,ageMin,birthYear,birthMonth,birthDay
     *                  其中name为模糊查询
     * @return 根据map集合模糊查询返回的对应学生数据集合
     */
    List<Student> getLikeByName(Map<String, Object> columnMap);

    /**
     * 学生数据（分页）
     *
     * @param pageSize 每页数据数量
     * @param pageNum  页码
     * @return 分页包装类
     */
    PageInfo getPageList(int pageNum, int pageSize);

    /**
     * 获取学生数据（根据id集合）（分页）
     *
     * @param pageSize 每页数据数量
     * @param pageNum  页码
     * @param idList   需要获取的学生id集合
     * @return 根据id返回对应的分页包装类
     */
    PageInfo getPageList(int pageNum, int pageSize, List<Integer> idList);

    /**
     * 获取学生数据（根据学生信息包装类的属性值）（分页）
     *
     * @param pageSize 每页数据数量
     * @param pageNum  页码
     * @param entity   学生信息包装类（属性表示筛选信息，null不参与筛选）
     * @return 根据包装类返回对应的分页包装类
     */
    PageInfo getPageList(int pageNum, int pageSize, Student entity);

    /**
     * 获取学生数据（根据map集合的val值）（分页）
     *
     * @param pageSize  每页数据数量
     * @param pageNum   页码
     * @param columnMap 筛选信息map集合
     *                  支持map的key值：number,name,sex,ageMax,ageMin,birthYear,birthMonth,birthDay
     * @return 根据map集合返回对应的分页包装类
     */
    PageInfo getPageList(int pageNum, int pageSize, Map<String, Object> columnMap);

    /**
     * 获取学生数据（根据姓名模糊查询）（分页）
     *
     * @param columnMap 筛选信息map集合
     *                  支持map的key值：number,name,sex,ageMax,ageMin,birthYear,birthMonth,birthDay
     *                  其中name为模糊查询
     * @return 根据map集合模糊查询返回的对应的分页包装类
     */
    PageInfo getPageLikeByName(int pageNum, int pageSize, Map<String, Object> columnMap);
}
