package com.peter.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.peter.pojo.Student;
import com.peter.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * 用户界面表示层 学生信息处理collection类
 *
 * @author Li Wang
 * @version 1.0.0
 * @Date 2022-11-19
 */
@Controller
public class StudentController {

    /**
     * 分页模式每页数据数量
     */
    public static Integer pageSize = 5;

    /**
     * 验证码对象
     */
    @Resource
    private DefaultKaptcha defaultKaptcha;

    @Resource
    private StudentService studentService;

    /**
     * 默认页面
     *
     * @param model 保存数据的模型对象
     * @return 跳转home页面，并携带集合（保存所有学生数据对象）
     */
    @RequestMapping("/")
    public String goHome(Model model) {
        List<Student> listAll = studentService.getList();
        model.addAttribute("studentsList", listAll);
        return "home";
    }

    /**
     * 验证码获取API
     */
    @GetMapping("/getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String text = defaultKaptcha.createText();
        //保存验证码
        request.getServletContext().setAttribute("vrifyCode", text);
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.flush();
        out.close();
    }

    /**
     * 学生信息添加API
     * 先将通过页面传递用户输入的的验证码进行判断，正确继续执行，错误直接返回“验证码错误”
     * 通过前端页面传递的参数信息创建成相关学生类对象，
     * 通过studentService的saveStudent()方法进行引用传递，操作数据插入，
     * 并将结果打包进入model里
     *
     * @param kaptcha 验证码
     * @param name    学生姓名
     * @param sex     学生性别
     * @param age     学生年龄
     * @param year    学生生日（年）
     * @param month   学生生日（月）
     * @param day     学生生日（日）
     * @param remarks 备注信息
     * @param request 请求对象
     * @return home页面
     */
    @PostMapping("/addStudent")
    public String addStudent(@RequestParam("kaptcha") String kaptcha,
                             @RequestParam("name") String name,
                             @RequestParam("sex") Integer sex,
                             @RequestParam("age") Integer age,
                             @RequestParam("year") Integer year,
                             @RequestParam("month") Integer month,
                             @RequestParam("day") Integer day,
                             @RequestParam("remarks") String remarks,
                             HttpServletRequest request, Model model) {

        List<Student> listAll = studentService.getList();
        model.addAttribute("studentsList", listAll);

        ServletContext servletContext = request.getServletContext(); //利用全局作用域传递登陆者信息或者注册结果信息
        String code = (String) servletContext.getAttribute("vrifyCode");
        System.out.println("系统验证码：" + code);
        request.getSession().removeAttribute("vrifyCode");
        if (!(code != null && code.equalsIgnoreCase(kaptcha))) {
            model.addAttribute("error", "验证码错误");
            System.out.println("验证码错误");
            return "noHTML";
        }

        Boolean sexNow = !(sex == 0);
        if (sex == 2) sexNow = null;
        LocalDate date = LocalDate.parse(year + "-" + month + "-" + day);
        Student student = new Student(null, name, sexNow, age, date, remarks);
        boolean result = studentService.saveStudent(student);
        model.addAttribute("result", result ? "添加成功" : "添加失败");
        return "home";
    }

    /**
     * 学生信息删除API（根据id）
     * 通过studentService的renameById()方法将id值进行值传递，操作数据删除，并处理删除数据结果
     *
     * @param id 需要删除用户id
     * @return 删除成功返回 "删除成功"；删除失败返回 "删除失败"；
     */
    @ResponseBody
    @PostMapping("/deleteStudentById/{id}")
    public String deleteStudentById(@PathVariable("id") Integer id) {
        boolean result = studentService.renameById(id);
        return result ? "删除成功" : "删除失败";
    }

    /**
     * 学生信息删除API（根据id字符串）
     * 通过","对字符串分割成数组，并将结果数组遍历添加进list
     * 通过studentService的renameById()方法将list进行引用传递，操作数据批量删除，并处理删除数据结果
     *
     * @param ids id字符串，中间由","进行拼接
     * @return 删除成功返回 "批删除成功"；删除失败返回 "批删除失败"；
     */
    @ResponseBody
    @PostMapping("/deleteStudentByIdBatch/{ids}")
    public String deleteStudentByIdBatch(@PathVariable("ids") String ids) {
        String[] split = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            idList.add(Integer.parseInt(split[i]));
        }
        boolean result = studentService.renameById(idList);
        return result ? "批删除成功" : "批删除失败";
    }

    /**
     * 学生信息删除API（根据实体类）
     * 通过前端页面传递的参数信息创建成相关学生类对象，
     * 通过studentService的renameByEntity()方法将对象进行引用传递，操作数据删除，并处理删除数据结果
     *
     * @param name  学生姓名
     * @param sex   学生性别
     * @param age   学生年龄
     * @param birth 学生生日
     * @return 删除成功返回 "删除成功"；删除失败返回 "删除失败"；
     */
    @ResponseBody
    @PostMapping("/renameByEntity")
    public String renameByEntity(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "sex", required = false) Integer sex,
                                 @RequestParam(value = "age", required = false) Integer age,
                                 @RequestParam(value = "birth", required = false) String birth) {
        if (name == null & sex == null & age == null & birth == null) return "非法删除";
        Boolean sexNow = null;
        if (sex != null) {
            sexNow = !(sex == 0);
            if (sex == 2) sexNow = null;
        }
        LocalDate date = null;
        if (birth != null) {
            date = LocalDate.parse(birth);
        }
        Student student = new Student(null, name, sexNow, age, date, null);
        boolean result = studentService.renameByEntity(student);
        return result ? "删除成功" : "删除失败";
    }

    /**
     * 学生信息删除API（根据map集合）
     * 通过前端页面传递的参数信息创建成符合key值的map集合，
     * 通过studentService的renameByMap()方法将map集合进行引用传递，操作数据删除，并处理删除数据结果
     *
     * @param name       学生姓名
     * @param sex        学生性别
     * @param ageMin     学生年龄（最小）
     * @param ageMax     学生年龄（最大）
     * @param birthYear  学生生日（年）
     * @param birthMonth 学生生日（月）
     * @param birthDay   学生生日（日）
     * @return 删除成功返回 "删除成功"；删除失败返回 "删除失败"；
     */
    @ResponseBody
    @PostMapping("/renameByMap")
    public String renameByMap(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "sex", required = false) Integer sex,
                              @RequestParam(value = "ageMin", required = false) Integer ageMin,
                              @RequestParam(value = "ageMax", required = false) Integer ageMax,
                              @RequestParam(value = "birthYear", required = false) Integer birthYear,
                              @RequestParam(value = "birthMonth", required = false) Integer birthMonth,
                              @RequestParam(value = "birthDay", required = false) Integer birthDay) {
        if (name == null & sex == null & ageMin == null & ageMax == null
                & birthYear == null & birthMonth == null & birthDay == null) return "非法删除";
        Boolean sexNow = null;
        if (sex != null) {
            sexNow = !(sex == 0);
            if (sex == 2) sexNow = null;
        }
        if (ageMin == null) {
            ageMin = 1;
        }
        if (ageMax == null) {
            ageMax = 200;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("sex", sexNow);
        map.put("ageMin", ageMin);
        map.put("ageMax", ageMax);
        map.put("birthYear", birthYear);
        map.put("birthMonth", birthMonth);
        map.put("birthDay", birthDay);
        boolean result = studentService.renameByMap(map);
        return result ? "删除成功" : "删除失败";
    }

    /**
     * 学生信息修改API
     * 通过前端页面传递的参数信息创建成相关学生类对象，
     * 通过studentService的updateById()方法进行引用传递，
     * 其中id为查询条件，其他内容为修改内容
     *
     * @param number  学生id
     * @param name    学生姓名
     * @param sex     学生性别
     * @param age     学生年龄
     * @param birth   学生生日
     * @param remarks 备注信息
     * @param model   Model模型对象
     * @return home页面
     */
    @PostMapping("/updateStudent")
    public String updateStudent(@RequestParam("number") Integer number,
                                @RequestParam("name") String name,
                                @RequestParam("sex") Integer sex,
                                @RequestParam("age") Integer age,
                                @RequestParam("birth") String birth,
                                @RequestParam("remarks") String remarks,
                                Model model) {
        Boolean sexNow = !(sex == 0);
        if (sex == 2) sexNow = null;
        LocalDate date = LocalDate.parse(birth);
        Student student = new Student(number, name, sexNow, age, date, remarks);
        boolean result = studentService.updateById(student);
        List<Student> listAll = studentService.getList();
        model.addAttribute("studentsList", listAll);
        model.addAttribute("result", result ? "修改成功" : "修改失败");
        return "home";
    }

    /**
     * 所有学生数据获取API
     * pageNum为null则是正常模式，pageNum不为null则是分页模式，每页数量为常量
     * 通过studentService的getList()方法查找所有学生数据并映射成集合，
     * 并打包进model中
     *
     * @param pageNum 分页模式的分页页码
     * @param model   Model模型对象
     * @return home页面
     */
    @GetMapping("/getStudentsAll")
    public String getStudentsAll(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                 Model model) {
        List<Student> listAll;
        if (pageNum == null) {
            listAll = studentService.getList();
        } else {
            listAll = studentService.getPageList(pageNum, pageSize).getList();
        }
        model.addAttribute("studentsList", listAll);
        return "home";
    }

    /**
     * 学生信息获取API（根据id）
     * pageNum为null则是正常模式，pageNum不为null则是分页模式，每页数量为常量
     * 通过studentService的getById()或getPageList()方法将id值进行值传递，操作数据获取
     *
     * @param id 需要查找用户id
     * @return 对应学生对象
     */
    @ResponseBody
    @GetMapping("/getStudentById/{id}")
    public Student getStudentById(@PathVariable("id") Integer id) {
        Student serviceById = studentService.getById(id);
        return serviceById;
    }

    /**
     * 学生信息获取API（根据id字符串）
     * pageNum为null则是正常模式，pageNum不为null则是分页模式，每页数量为常量
     * 通过","对字符串分割成数组，并将结果数组遍历添加进list
     * 通过studentService的getList()或getPageList()方法将list进行引用传递，操作数据获取
     *
     * @param pageNum 分页模式的分页页码
     * @param ids     id字符串，中间由","进行拼接
     * @param model   Model模型对象
     * @return home页面
     */
    @GetMapping("/getStudentByIdBatch/{ids}")
    public String getStudentByIdBatch(@PathVariable("ids") String ids, @RequestParam(value = "pageNum", required = false) Integer pageNum, Model model) {
        String[] split = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            idList.add(Integer.parseInt(split[i]));
        }
        List<Student> result;
        if (pageNum == null) {
            result = studentService.getList(idList);
        } else {
            result = studentService.getPageList(pageNum, pageSize, idList).getList();
        }
        model.addAttribute("studentsList", result);
        return "home";
    }

    /**
     * 学生信息获取API（根据实体类）
     * pageNum为null则是正常模式，pageNum不为null则是分页模式，每页数量为常量
     * 通过前端页面传递的参数信息创建成相关学生类对象，
     * 通过studentService的getList()或getPageList()方法将对象进行引用传递，操作数据获取
     *
     * @param pageNum 分页模式的分页页码
     * @param name    学生姓名
     * @param sex     学生性别
     * @param age     学生年龄
     * @param birth   学生生日
     * @param model   Model模型对象
     * @return 主页面
     */
    @GetMapping("/getStudentByEntity")
    public String getStudentByEntity(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "sex", required = false) Integer sex,
                                     @RequestParam(value = "age", required = false) Integer age,
                                     @RequestParam(value = "birth", required = false) String birth,
                                     Model model) {
        Boolean sexNow = null;
        if (sex != null) {
            sexNow = !(sex == 0);
            if (sex == 2) sexNow = null;
        }
        LocalDate date = null;
        if (birth != null) {
            date = LocalDate.parse(birth);
        }
        Student student = new Student(null, name, sexNow, age, date, null);
        List<Student> result;
        if (pageNum == null) {
            result = studentService.getList(student);
        } else {
            result = studentService.getPageList(pageNum, pageSize, student).getList();
        }
        model.addAttribute("studentsList", result);
        return "home";
    }

    /**
     * 学生信息获取API（根据map集合）
     * pageNum为null则是正常模式，pageNum不为null则是分页模式，每页数量为常量
     * 通过前端页面传递的参数信息创建成符合key值的map集合，
     * 通过studentService的getList()或getPageList()方法将map集合进行引用传递，操作数据获取
     *
     * @param pageNum    分页模式的分页页码
     * @param isLike     模糊查询标志 0：非模糊擦汗寻；1：模糊查询
     * @param name       学生姓名
     * @param sex        学生性别
     * @param ageMin     学生年龄（最小）
     * @param ageMax     学生年龄（最大）
     * @param birthYear  学生生日（年）
     * @param birthMonth 学生生日（月）
     * @param birthDay   学生生日（日）
     * @param model      Model模型对象
     * @return home页面
     */
    @GetMapping("/getStudentByMap")
    public String getStudentByMap(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                  @RequestParam(value = "isLike", required = false) Integer isLike,
                                  @RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "sex", required = false) Integer sex,
                                  @RequestParam(value = "ageMin", required = false) Integer ageMin,
                                  @RequestParam(value = "ageMax", required = false) Integer ageMax,
                                  @RequestParam(value = "birthYear", required = false) Integer birthYear,
                                  @RequestParam(value = "birthMonth", required = false) Integer birthMonth,
                                  @RequestParam(value = "birthDay", required = false) Integer birthDay,
                                  Model model) {
        Boolean sexNow = null;
        if (sex != null) {
            sexNow = !(sex == 0);
            if (sex == 2) sexNow = null;
        }
        Boolean likeNow = false;
        if (isLike != null) {
            likeNow = !(isLike == 0);
        }
        if (ageMin == null) {
            ageMin = 1;
        }
        if (ageMax == null) {
            ageMax = 200;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("sex", sexNow);
        map.put("ageMin", ageMin);
        map.put("ageMax", ageMax);
        map.put("birthYear", birthYear);
        map.put("birthMonth", birthMonth);
        map.put("birthDay", birthDay);
        List<Student> result;
        if (pageNum == null) {
            if (likeNow) {
                result = studentService.getList(map);
            } else {
                result = studentService.getLikeByName(map);
            }
        } else {
            if (!likeNow) {
                result = studentService.getPageList(pageNum, pageSize, map).getList();
            } else {
                result = studentService.getPageLikeByName(pageNum, pageSize, map).getList();
            }
        }
        model.addAttribute("studentsList", result);
        return "home";
    }

    /**
     * 跳转主页面
     */
    @GetMapping(value = "/toHome")
    public String toHome(Model model) {
        List<Student> listAll = studentService.getList();
        model.addAttribute("studentsList", listAll);
        return "home";
    }

    /**
     * 添加页面跳转
     */
    @GetMapping(value = "/toAdd")
    public String toAdd() {
        return "add";
    }

    /**
     * 修改页面跳转
     *
     * @return
     */
    @GetMapping(value = "/toUpdate/{id}")
    public String toUpdate(@PathVariable("id") Integer id, Model model) {
        Student serviceById = studentService.getById(id);
        if (serviceById == null) {
            model.addAttribute("error", "学生序号有误");
            return "noHTML";
        }
        model.addAttribute("student", serviceById);
        return "update";
    }

}
