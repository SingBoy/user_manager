package cn.net.ibingo.client.controller;

import cn.net.ibingo.common.controller.BaseController;
import cn.net.ibingo.common.utils.*;
import cn.net.ibingo.core.model.LoginLog;
import cn.net.ibingo.core.model.User;
import cn.net.ibingo.core.redis.VerificCodeRedisFactory;
import cn.net.ibingo.core.redis.model.VerificCodeRedisModel;
import cn.net.ibingo.core.service.LoginLogService;
import cn.net.ibingo.core.service.UserService;
import net.sf.json.JSONArray;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private VerificCodeRedisFactory verificCodeRedisFactory;

    /**
     * 用户注册接口
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/userRegistered", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray userRegistered(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
       // User user1 = jsonToUser(request);
        //判断上传数据是否为空
        if (user != null && !StringUtils.isEmpty(user.getUserName()) && !StringUtils.isEmpty(user.getPassWord()) &&
                !StringUtils.isEmpty(user.getCallerType()) && !StringUtils.isEmpty(user.getImsi()) && !StringUtils.isEmpty(user.getDeviceNo()) && !StringUtils.isEmpty(user.getImei())) {
            //当调用者类别为菲律宾时，跳转软件名称必须不为空
            if (ConstantConfig.CALLTYPE_PHILIP.equals(user.getCallerType())) {
                if (StringUtils.isEmpty(user.getSoftName())) {
                    resultMap.put("status", ConstantConfig.HTTP_CODE_1002);
                    return JSONArray.fromObject(resultMap);
                }
            }
            //校验字符长度
            if (CheckUtil.stringLength(user.getUserName(), 255) && CheckUtil.stringLength(user.getPassWord(), 100) && CheckUtil.stringLength(user.getImsi(), 100)
                    && CheckUtil.stringLength(user.getDeviceNo(), 100) && CheckUtil.stringLength(user.getImei(), 100) && CheckUtil.stringLength(user.getSoftName(), 255)) {

                boolean flg = true;
                //判断用户账号是手机号码还是邮箱地址 并进行校验
                if (user.getUserName().indexOf("@") > -1) {
                    flg = CheckUtil.checkEmail(user.getUserName());
                    user.setUserNameType(ConstantConfig.USENAME_EMAIL);
                } else {
                    flg = CheckUtil.checkMobileNumber(user.getUserName());
                    user.setUserNameType(ConstantConfig.USERNAME_MOBILE);
                }
                if (flg) {
                    //判断用户是否已存在
                    User oldUser = userService.findUserByUserName(user.getUserName());
                    if (oldUser != null) {
                        resultMap.put("status", ConstantConfig.HTTP_CODE_1005);
                        return JSONArray.fromObject(resultMap);
                    }
                    //将密码MD5加密
                    String password = user.getPassWord();
                    user.setPassWord(MD5Util.MD5(password));
                    user.setUserId("user_" + UUID.randomUUID());
                    //设置默认头像
                    user.setImageUrl("default_image.jpg");
                    user.setCreateDate(new Date());
                    //注册
                    int count = userService.insertUser(user);
                    if (count > 0) {
                        //注册成功后直接登录用户
                        user.setPassWord(password);
                        JSONArray jsonArray = commonUserLogin(user, request, response);
                        return jsonArray;
                    } else {
                        resultMap.put("status", ConstantConfig.HTTP_CODE_1003);
                    }
                }else{
                    resultMap.put("status", ConstantConfig.HTTP_CODE_1001);
                }
            } else {
                resultMap.put("status", ConstantConfig.HTTP_CODE_1004);
            }
        } else {
            resultMap.put("status", ConstantConfig.HTTP_CODE_1002);
        }
        return JSONArray.fromObject(resultMap);
    }


    /**
     * 用户登录接口
     *
     * @param user
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/commonUserLogin", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray commonUserLogin(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //判断上传数据是否为空
        if (user != null && !StringUtils.isEmpty(user.getUserName()) && !StringUtils.isEmpty(user.getPassWord())) {
            //判断用户账号是手机号码还是邮箱地址 并进行校验
            boolean flg = true;
            if (user.getUserName().indexOf("@") > -1) {
                flg = CheckUtil.checkEmail(user.getUserName());
            } else {
                flg = CheckUtil.checkMobileNumber(user.getUserName());
            }
            if (flg) {
                User oldUser = userService.findUserByNameAndPass(user.getUserName(),MD5Util.MD5(user.getPassWord()));
                if (oldUser != null) {
                    //创建session
                    createSession(oldUser, request, response, resultMap);
                    //保存登录日志
                    saveLoginLog(oldUser.getUserName(), ConstantConfig.LOGIN_COMMON);

                    resultMap.put("userId", oldUser.getUserId());
                    resultMap.put("userName", oldUser.getUserName());
                    resultMap.put("nickName", oldUser.getNickName()==null?"":oldUser.getNickName());
                    resultMap.put("sex", oldUser.getSex()==null?"":oldUser.getSex());
                    resultMap.put("birthDate", oldUser.getBirthDate()==null?"":oldUser.getBirthDate());
                    resultMap.put("country", oldUser.getCountry()==null?"":oldUser.getCountry());
                    resultMap.put("imageUrl", "");
                    resultMap.put("status", ConstantConfig.HTTP_CODE_1000);
                } else {
                    resultMap.put("status", ConstantConfig.HTTP_CODE_1006);
                }
            }else {
                resultMap.put("status", ConstantConfig.HTTP_CODE_1001);
            }
        }else {
            resultMap.put("status", ConstantConfig.HTTP_CODE_1002);
        }
        return JSONArray.fromObject(resultMap);
    }


    /**
     * 第三方账号登录
     *
     * @param user
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/thirdUserLogin", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray thirdUserLogin(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //判断参数是否为空
        if (user != null && !StringUtils.isEmpty(user.getUserId())) {
            //根据用户名查询该用户是否已经注册
            User oldUser = userService.findUserByUserId(user.getUserId());
            if (oldUser == null) {
                //注册用户  为第三方账号生成随机账号
                user.setUserName(UUID.randomUUID().toString());
                //将密码MD5加密
                user.setPassWord(MD5Util.MD5("123456"));
                user.setUserNameType(ConstantConfig.USENAME_THIRD);
                //设置默认头像
                user.setImageUrl("default_image.jpg");
                user.setCreateDate(new Date());
                //注册
                int count = userService.insertUser(user);
                if (count > 0) {
                    oldUser = userService.findUserByUserId(user.getUserId());
                    //创建session
                    createSession(user, request, response, resultMap);
                    //保存登录日志
                    saveLoginLog(user.getUserName(), ConstantConfig.LOGIN_THIRD);
                    //注册成功后直接登录用户
                    resultMap.put("userId", user.getUserId());
                    resultMap.put("userName", oldUser.getUserName());
                    resultMap.put("nickName", oldUser.getNickName()==null?"":oldUser.getNickName());
                    resultMap.put("sex", oldUser.getSex()==null?"":oldUser.getSex());
                    resultMap.put("birthDate", oldUser.getBirthDate()==null?"":oldUser.getBirthDate());
                    resultMap.put("country", oldUser.getCountry()==null?"":oldUser.getCountry());
                    resultMap.put("imageUrl", "");
                    resultMap.put("status", ConstantConfig.HTTP_CODE_1000);
                } else {
                    resultMap.put("status", ConstantConfig.HTTP_CODE_1003);
                }
            } else {
                //登录用户
                //创建session
                createSession(oldUser, request, response, resultMap);
                //保存登录日志
                saveLoginLog(oldUser.getUserName(), ConstantConfig.LOGIN_THIRD);

                resultMap.put("userId", user.getUserId());
                resultMap.put("userName", oldUser.getUserName());
                resultMap.put("nickName", oldUser.getNickName()==null?"":oldUser.getNickName());
                resultMap.put("sex", oldUser.getSex()==null?"":oldUser.getSex());
                resultMap.put("birthDate", oldUser.getBirthDate()==null?"":oldUser.getBirthDate());
                resultMap.put("country", oldUser.getCountry()==null?"":oldUser.getCountry());
                resultMap.put("imageUrl", "");
                resultMap.put("status", ConstantConfig.HTTP_CODE_1000);
            }
        } else {
            resultMap.put("status", ConstantConfig.HTTP_CODE_1002);
        }
        return JSONArray.fromObject(resultMap);
    }


    /**
     * 给用户发送验证码
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "sendCode", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray sendCode(@RequestBody User user,HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        String username = user.getUserName();
        boolean flg = true;
        //判断用户名是否为空
        if (!StringUtils.isEmpty(username)) {
            //判断用户名是手机号码还是邮箱
            if (username.indexOf("@") > -1) {
                //校验邮箱格式
                flg = CheckUtil.checkEmail(username);
                if (flg) {
                    try {
                        //生成6位随机数
                        String ramNum = CodeUtils.getSixNum();
                        VerificCodeRedisModel vr = new VerificCodeRedisModel();
                        vr.setUsername(username);
                        vr.setRamNum(ramNum);
                        //将随机数存储到redis
                        verificCodeRedisFactory.add(vr);
                        //session.setAttribute(username,ramNum);
                        EmailUtil.sendMessage(username, ConstantConfig.EMAIL_SUBJECT, "["+ramNum +"]:"+ ConstantConfig.EMAIL_MESSAGE);
                        resultMap.put("status", ConstantConfig.HTTP_CODE_1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                        resultMap.put("status", ConstantConfig.HTTP_CODE_1003);
                        return JSONArray.fromObject(resultMap);
                    }
                } else {
                    resultMap.put("status", ConstantConfig.HTTP_CODE_1001);
                }
            } else {
                /*//校验手机号格式
                flg = CheckUtil.checkMobileNumber(username);
                if (flg) {
                    //TODO
                    //sendMobileCode(username);
                } else {
                    resultMap.put("status", ConstantConfig.HTTP_CODE_1001);
                    return JSONArray.fromObject(resultMap);
                }*/
                resultMap.put("status", ConstantConfig.HTTP_CODE_1009);
            }
        }else {
            resultMap.put("status", ConstantConfig.HTTP_CODE_1002);
        }
        return JSONArray.fromObject(resultMap);
    }


    /**
     * 修改密码
     *
     * @param user
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray updatePassword(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (user != null && !StringUtils.isEmpty(user.getUserName()) && !StringUtils.isEmpty(user.getPassWord()) && !StringUtils.isEmpty(user.getVerificCode())) {
            if(user.getUserName().indexOf("@")>-1){
                VerificCodeRedisModel vr = new VerificCodeRedisModel();
                vr.setUsername(user.getUserName());
                vr.setRamNum(user.getVerificCode());
                //从redis获取该用户对应的验证码
                String redisVerificCode =verificCodeRedisFactory.get(vr);//(String) session.getAttribute(user.getUserName());//
                if (!StringUtils.isEmpty(redisVerificCode)) {
                    //加密该用户密码
                    user.setPassWord(MD5Util.MD5(user.getPassWord()));
                    //更新该用户的密码
                    userService.updatePasswordByUserName(user);
                    resultMap.put("status", ConstantConfig.HTTP_CODE_1000);
                } else {
                    resultMap.put("status", ConstantConfig.HTTP_CODE_1007);
                }
            }else{
                resultMap.put("status", ConstantConfig.HTTP_CODE_1009);
            }
        } else {
            resultMap.put("status", ConstantConfig.HTTP_CODE_1002);
        }
        return JSONArray.fromObject(resultMap);
    }


    /**
     * 修改用户信息
     *
     * @param user
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)//
    @ResponseBody
    public JSONArray updateUserInfo(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //判断上传数据是否为空
        if (user != null && !StringUtils.isEmpty(user.getUserName())) {
            if (CheckUtil.stringLength(user.getNickName(), 255) && CheckUtil.stringLength(user.getCountry(), 50)) {
                int count = userService.updateUserInfo(user);

                if(count>0) resultMap.put("status", ConstantConfig.HTTP_CODE_1001);
                else resultMap.put("status", ConstantConfig.HTTP_CODE_1003);
            } else {
                resultMap.put("status", ConstantConfig.HTTP_CODE_1004);
            }
        }else {
            resultMap.put("status", ConstantConfig.HTTP_CODE_1002);
        }
        return JSONArray.fromObject(resultMap);
    }


    /**
     * 用户头像上传
     * @param request
     * @param response
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)//
    @ResponseBody
    public JSONArray uploadImage(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "file", required = false) MultipartFile file) {//,@RequestParam(value = "file",required = false)MultipartFile file
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //判断上传数据是否为空
        if (file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
            if(file.getSize() < ConstantConfig.IMAGE_SIZE){
                //如果文件夹不存在则创建
                String path = "";//FileUtil.imageUpload(request, file);
                try {
                    CommonsMultipartFile cf= (CommonsMultipartFile)file; //这个myfile是MultipartFile的
                    DiskFileItem fi = (DiskFileItem)cf.getFileItem();
                    path = AwsClient.uploadToS3(fi.getStoreLocation(),file.getOriginalFilename()+".jpg");
                    fi.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                resultMap.put("imageUrl", path);
                resultMap.put("status", ConstantConfig.HTTP_CODE_1000);
            }else {
                resultMap.put("status", ConstantConfig.HTTP_CODE_1008);
            }
        }else{
            resultMap.put("status", ConstantConfig.HTTP_CODE_1002);
        }
        return JSONArray.fromObject(resultMap);
    }


    /**
     * 用户头像下载
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/downloadImage", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray downloadImage(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String fileName = user.getUserName();
        if(!StringUtils.isEmpty(fileName)){
            //FileUtil.imageDownload(fileName,request, response);
            try {
                AwsClient.getContentFromS3(user.getUserName()+".jpg",response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            resultMap.put("status", ConstantConfig.HTTP_CODE_1002);
        }
        return JSONArray.fromObject(resultMap);
    }


    /**
     * 创建用户登录session
     *
     * @param oldUser
     * @param request
     * @param response
     * @param resultMap
     * @return
     */
    public void createSession(User oldUser, HttpServletRequest request, HttpServletResponse response, Map<String, Object> resultMap) {
        HttpSession session = request.getSession();
        // 1、创建cookie
        try {
            Cookie usernameCookie = new Cookie("username", oldUser.getUserName());
            Cookie dateCookie = new Cookie("date", DateUtils.formatDateTimeAll(new Date()));
            Cookie passwordCookie = new Cookie("password", oldUser.getPassWord());
            // 2、设置cookie的过期时间，如果不设置，则浏览关闭后就过期了，本地就没有了
            usernameCookie.setMaxAge(30 * 24 * 3600);
            dateCookie.setMaxAge(30 * 24 * 3600);
            passwordCookie.setMaxAge(30 * 24 * 3600);
            // 3、将cookie存于响应对象中
            response.addCookie(usernameCookie);
            response.addCookie(dateCookie);
            response.addCookie(passwordCookie);

            session.setAttribute(ConstantConfig.UID, oldUser.getId());
            session.setAttribute(ConstantConfig.USER, oldUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建登录日志
     *
     * @param username
     * @param loginType
     */
    public void saveLoginLog(String username, Integer loginType) {
        //保存登录日志
        LoginLog loginLog = new LoginLog();
        loginLog.setUserName(username);
        loginLog.setLoginType(loginType);
        loginLog.setCreateDate(new Date());
        loginLogService.insertLoginLog(loginLog);
    }
}