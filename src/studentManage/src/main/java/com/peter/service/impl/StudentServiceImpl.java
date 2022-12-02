package com.peter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peter.mapper.StudentMapper;
import com.peter.pojo.Student;
import com.peter.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑层 学生信息处理service接口实现类
 *
 * @author Li Wang
 * @version 1.0.0
 * @Date 2022-11-19
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    /**
     * 通过StudentMapper调用insert()通过对象传递进行数据插入
     *
     * @param entity 学生信息包装类
     * @return true添加成功，false添加失败
     */
    @Override
    public boolean saveStudent(Student entity) {
        int result = studentMapper.insert(entity);
        if (result == 0) {
            System.out.println("saveStudent() ---- 学生数据插入失败");
            return false;
        }
        System.out.println("saveStudent() ---- 学生数据插入成功");
        return true;
    }

    /**
     * 对集合进行循环遍历，中间通过StudentMapper调用insert()通过对象传递进行数据插入
     * 若有数据添加失败，会进行手动回滚，并最终返回false
     * 整个过程属于事务行为
     *
     * @param entityList 学生信息包装类集合
     * @return true批添加成功，false批添加失败
     */
    @Override
    @Transactional
    public boolean saveStudent(List<Student> entityList) {
        System.out.println("saveStudent() ---- 总共需要添加" + entityList.size() + "条数据");
        try {
            for (Student student : entityList) {
                studentMapper.insert(student);
            }
        } catch (Exception e) {
            System.out.println("saveStudent() ---- 学生数据批量添加失败");
            e.printStackTrace();
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        System.out.println("saveStudent() ---- 学生数据批量添加成功");
        return true;
    }

    /**
     * 通过StudentMapper调用deleteById()通过id搜索对应数据并进行删除
     *
     * @param id 需要删除的学生id
     * @return true删除成功，false删除失败
     */
    @Override
    public boolean renameById(int id) {
        int result = studentMapper.deleteById(id);
        if (result == 0) {
            System.out.println("renameById() ---- 学生数据删除失败");
            return false;
        }
        System.out.println("renameById() ---- 学生数据删除成功");
        return true;
    }

    /**
     * 对集合进行循环遍历，中间通过StudentMapper调用deleteById()通过id搜索对应数据并进行删除
     * 若有数据删除失败，会进行手动回滚，并最终返回false
     * 整个过程属于事务行为
     *
     * @param idList 需要删除的id集合
     * @return true批删除成功，false批删除失败
     */
    @Override
    @Transactional
    public boolean renameById(List<Integer> idList) {
        System.out.println("renameById() ---- 总共需要删除" + idList.size() + "条数据");
        try {
            for (Integer id : idList) {
                studentMapper.deleteById(id);
            }
        } catch (Exception e) {
            System.out.println("renameById() ---- 学生数据批量删除失败");
            e.printStackTrace();
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        System.out.println("renameById() ---- 学生数据批量删除成功");
        return true;
    }

    /**
     * 通过StudentMapper调用deleteByEntity()通过对象属性值传递进行数据删除
     *
     * @param entity 学生信息包装类（属性表示筛选信息，null不参与筛选）
     * @return true删除成功，false删除失败
     */
    @Override
    public boolean renameByEntity(Student entity) {
        int result = studentMapper.deleteByEntity(entity);
        if (result == 0) {
            System.out.println("renameByEntity() ---- 学生数据删除失败");
            return false;
        }
        System.out.println("renameByEntity() ---- 学生数据删除成功");
        return true;
    }

    /**
     * 通过StudentMapper调用deleteByMap()通过map中value值传递进行数据删除
     *
     * @param columnMap 筛选信息map集合
     *                  支持map的key值：number,name,sex,ageMax,ageMin,birthYear,birthMonth,birthDay
     * @return true删除成功，false删除失败
     */
    @Override
    public boolean renameByMap(Map<String, Object> columnMap) {
        int result = studentMapper.deleteByMap(columnMap);
        if (result == 0) {
            System.out.println("renameByMap() ---- 学生数据删除失败");
            return false;
        }
        System.out.println("renameByMap() ---- 学生数据删除成功");
        return true;
    }

    /**
     * 通过StudentMapper调用update()属性中的number为作为查询条件
     * 其他的属性为需要修改的目标，属性值表示修改后信息，若属性为null则不参与本属性数据的修改
     *
     * @param entity 属性包装类
     * @return true修改成功，false修改失败
     */
    @Override
    public boolean updateById(Student entity) {
        int result = studentMapper.update(entity);
        if (result == 0) {
            System.out.println("updateById() ---- 学生信息修改失败");
            return false;
        }
        System.out.println("updateById() ---- 学生信息修改成功");
        return true;
    }

    /**
     * 通过StudentMapper调用selectList()对所有数据进行查询
     *
     * @return 关于所有关于学生信息的List返回结果集
     */
    @Override
    public List<Student> getList() {
        List<Student> studentsList = null;
        try {
            studentsList = studentMapper.selectList();
        } catch (Exception e) {
            System.out.println("getList() ---- 学生信息查询失败");
            e.printStackTrace();
        }
        System.out.println("查询到" + studentsList.size() + "条数据");
        return studentsList;
    }

    /**
     * 通过StudentMapper调用selectById()通过id传值对数据进行查询
     *
     * @param id 需要获取的学生id
     * @return 对应id数据的学生对象
     */
    @Override
    public Student getById(int id) {
        Student student = null;
        try {
            student = studentMapper.selectById(id);
        } catch (Exception e) {
            System.out.println("getById() ---- 学生信息查询失败");
            e.printStackTrace();
        }
        if (student == null) System.out.println("未找到指定数据");
        return student;
    }

    /**
     * 通过StudentMapper调用selectBatchList()通过id集合对数据进行查询
     *
     * @param idList 需要获取的学生id集合
     * @return 对应集合中id对应数据的学生对象集合
     */
    @Override
    public List<Student> getList(List<Integer> idList) {
        List<Student> students = null;
        try {
            students = studentMapper.selectBatchList(idList);
        } catch (Exception e) {
            System.out.println("getList() ---- 学生信息查询失败");
            e.printStackTrace();
        }
        System.out.println("通过id集合查询到" + students.size() + "条数据");
        return students;
    }

    /**
     * 通过StudentMapper调用selectByEntity()通过对象传递进行数据查询
     * 属性代表要筛选哪些信息，属性值表示筛选传入信息值
     * 其中null不参与筛选
     *
     * @param entity 学生信息包装类
     * @return 对象属性值对应数据的学生对象集合
     */
    @Override
    public List<Student> getList(Student entity) {
        List<Student> students = null;
        try {
            students = studentMapper.selectByEntity(entity);
        } catch (Exception e) {
            System.out.println("getList() ---- 学生信息查询失败");
            e.printStackTrace();
        }
        System.out.println("通过对象属性查询到" + students.size() + "条数据");
        return students;
    }

    /**
     * 通过StudentMapper调用selectByMap()通过map中value值传递进行数据查询
     * 支持map的key值：number,name,sex,ageMax,ageMin,birthYear,birthMonth,birthDay
     * 其中null不参与筛选
     *
     * @param columnMap 筛选信息map集合
     * @return map的value对应数据的学生对象集合
     */
    @Override
    public List<Student> getList(Map<String, Object> columnMap) {
        List<Student> students = studentMapper.selectByMap(columnMap);
        try {
            students = studentMapper.selectByMap(columnMap);
        } catch (Exception e) {
            System.out.println("getList() ---- 学生信息查询失败");
            e.printStackTrace();
        }
        System.out.println("通过map查询到" + students.size() + "条数据");
        return students;
    }

    /**
     * 通过StudentMapper调用selectLikeByName()通过name传值进行模糊查询
     *
     * @param name 需要进行模糊查询的关键字
     * @return 模糊查询学生对象集合
     */
    @Override
    public List<Student> getLikeByName(String name) {
        List<Student> students = null;
        try {
            students = studentMapper.selectLikeByName(name);
        } catch (Exception e) {
            System.out.println("selectLikeByName() ---- 学生信息模糊查询失败");
            e.printStackTrace();
        }
        System.out.println("模糊查询到" + students.size() + "条数据");
        return students;
    }

    /**
     * 通过StudentMapper调用selectLikeByMap()进行模糊查询
     * 支持map的key值：number,name,sex,ageMax,ageMin,birthYear,birthMonth,birthDay
     * name值为模糊查找
     *
     * @param columnMap 筛选信息map集合
     * @return 模糊查询学生对象集合
     */
    @Override
    public List<Student> getLikeByName(Map<String, Object> columnMap) {
        List<Student> students = null;
        try {
            students = studentMapper.selectLikeByMap(columnMap);
        } catch (Exception e) {
            System.out.println("selectLikeByMap() ---- 学生信息模糊查询失败");
            e.printStackTrace();
        }
        System.out.println("模糊查询到" + students.size() + "条数据");
        return students;
    }

    /**
     * PageHelper是mybatis插件，可用于分页，startPage会开启分页，进行查询语句拦截，
     * 后面紧跟调用本类中获取“全部数据”的查询语句，得到分页结果集，
     * 并将结果集封装进PageInfo得到最终分页结果类
     *
     * @param pageNum  页码
     * @param pageSize 每页数据数量
     * @return 分页包装类
     */
    @Override
    public PageInfo getPageList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Student> allList = getList();
        PageInfo pageInfo = new PageInfo(allList);
        return pageInfo;
    }

    /**
     * PageHelper是mybatis插件，可用于分页，startPage会开启分页，进行查询语句拦截，
     * 后面紧跟调用本类中获取根据“所需id”的查询语句，得到分页结果集，
     * 并将结果集封装进PageInfo得到最终分页结果类
     *
     * @param pageNum  页码
     * @param pageSize 每页数据数量
     * @param idList   需要获取的学生id集合
     * @return 根据id返回对应的分页包装类
     */
    @Override
    public PageInfo getPageList(int pageNum, int pageSize, List<Integer> idList) {
        PageHelper.startPage(pageNum, pageSize);
        List<Student> idsList = getList(idList);
        PageInfo pageInfo = new PageInfo(idsList);
        return pageInfo;
    }

    /**
     * PageHelper是mybatis插件，可用于分页，startPage会开启分页，进行查询语句拦截，
     * 后面紧跟调用本类中获取根据“学生信息包装类”的查询语句，得到分页结果集，
     * 并将结果集封装进PageInfo得到最终分页结果类
     *
     * @param pageNum  页码
     * @param pageSize 每页数据数量
     * @param entity   学生信息包装类（属性表示筛选信息，null不参与筛选）
     * @return 根据包装类返回对应的分页包装类
     */
    @Override
    public PageInfo getPageList(int pageNum, int pageSize, Student entity) {
        PageHelper.startPage(pageNum, pageSize);
        List<Student> entityList = getList(entity);
        PageInfo pageInfo = new PageInfo(entityList);
        return pageInfo;
    }

    /**
     * PageHelper是mybatis插件，可用于分页，startPage会开启分页，进行查询语句拦截，
     * 后面紧跟调用本类中获取根据“map集合”的查询语句，得到分页结果集，
     * 并将结果集封装进PageInfo得到最终分页结果类
     *
     * @param pageNum   页码
     * @param pageSize  每页数据数量
     * @param columnMap 筛选信息map集合
     *                  支持map的key值：number,name,sex,ageMax,ageMin,birthYear,birthMonth,birthDay
     * @return 根据map集合返回对应的分页包装类
     */
    @Override
    public PageInfo getPageList(int pageNum, int pageSize, Map<String, Object> columnMap) {
        PageHelper.startPage(pageNum, pageSize);
        List<Student> mapList = getList(columnMap);
        PageInfo pageInfo = new PageInfo(mapList);
        return pageInfo;
    }

    /**
     * PageHelper是mybatis插件，可用于分页，startPage会开启分页，进行查询语句拦截，
     * 后面紧跟调用本类中获取根据“map集合”的查询语句，得到分页结果集，
     * 其中map集合中name为模糊查找
     * 并将结果集封装进PageInfo得到最终分页结果类
     *
     * @param pageNum   页码
     * @param pageSize  每页数据数量
     * @param columnMap 筛选信息map集合
     *                  支持map的key值：number,name,sex,ageMax,ageMin,birthYear,birthMonth,birthDay
     * @return 根据map集合返回对应的模糊查询分页包装类
     */
    @Override
    public PageInfo getPageLikeByName(int pageNum, int pageSize, Map<String, Object> columnMap) {
        PageHelper.startPage(pageNum, pageSize);
        List<Student> mapList = getLikeByName(columnMap);
        PageInfo pageInfo = new PageInfo(mapList);
        return pageInfo;
    }

}
