package cn.net.ibingo.core.service.impl;

import cn.net.ibingo.core.dao.UserDao;
import cn.net.ibingo.core.model.User;
import cn.net.ibingo.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yuxiangjie on 2017-07-06.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public User findUserByUserName(String userName) {
        return userDao.findUserByUserName(userName);
    }

    @Override
    public User findUserByUserId(String userId) {
        return userDao.findUserByUserId(userId);
    }

    @Override
    public User findUserByNameAndPass(String userName, String passWord) {
        return userDao.findUserByNameAndPass(userName,passWord);
    }

    @Override
    public int insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public int updateUserInfo(User user) {
        return userDao.updateUserInfo(user);
    }

    @Override
    public int updatePasswordByUserName(User user) {
        return userDao.updatePasswordByUserName(user);
    }
}
