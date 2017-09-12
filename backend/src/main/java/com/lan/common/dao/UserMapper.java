package com.lan.common.dao;

import com.lan.common.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * package com.lan.dao
 *
 * @author spoomlzx
 * @date 2017/5/29
 */
@Repository
public interface UserMapper {

    public User getUserByEmail(String email);

    public int updateUser(@Param("user") User user);

    public int insertUser(@Param("user") User user);
}
