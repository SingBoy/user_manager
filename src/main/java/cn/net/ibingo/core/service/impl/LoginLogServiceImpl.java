package cn.net.ibingo.core.service.impl;

import cn.net.ibingo.core.dao.LoginLogDao;
import cn.net.ibingo.core.dao.UserDao;
import cn.net.ibingo.core.model.LoginLog;
import cn.net.ibingo.core.model.User;
import cn.net.ibingo.core.service.LoginLogService;
import cn.net.ibingo.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yuxiangjie on 2017-07-06.
 */
@Service
public class LoginLogServiceImpl implements LoginLogService{
    @Autowired
    private LoginLogDao loginLogDao;

    @Override
    public int insertLoginLog(LoginLog loginLog) {
        return loginLogDao.insertLoginLog(loginLog);
    }
}
