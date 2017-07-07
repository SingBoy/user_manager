package cn.net.ibingo.core.service;

import cn.net.ibingo.core.model.User;

/**
 * Created by yuxiangjie on 2017-07-05.
 */
public interface UserService {

    public User findUserByUserName(String userName);

    public User findUserByUserId(String userId);

    public User findUserByNameAndPass(String userName,String passWord);

    public int insertUser(User user);

    public int updateUserInfo(User user);

    public int updatePasswordByUserName(User user);
}
