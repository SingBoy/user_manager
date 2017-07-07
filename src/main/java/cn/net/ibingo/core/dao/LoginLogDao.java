package cn.net.ibingo.core.dao;

import cn.net.ibingo.core.model.LoginLog;
import cn.net.ibingo.core.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by lqj on 2017-07-06.
 */
public interface LoginLogDao {

    public int insertLoginLog(LoginLog loginLog);

}
