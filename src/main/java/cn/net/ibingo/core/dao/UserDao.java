package cn.net.ibingo.core.dao;

import cn.net.ibingo.core.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by yuxiangjie on 2017-07-06.
 */
public interface UserDao {

    public User findUserByUserName(String userName);

    public User findUserByUserId(String userId);

    public User findUserByNameAndPass(@Param("userName") String userName, @Param("passWord")String passWord);

    public int insertUser(User user);

    public int updateUserInfo(User user);

    public int updatePasswordByUserName(User user);
}
