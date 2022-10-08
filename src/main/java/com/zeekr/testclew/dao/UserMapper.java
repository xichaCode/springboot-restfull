package com.zeekr.testclew.dao;

import com.zeekr.testclew.model.User;

/**
 * @ClassName UserMapper
 * @Description TODO
 * @Author xibu
 * @Date UserMapper 22:48
 * @Version 1.0
 **/
public interface UserMapper {

    User selectByPrimaryKey(int id);
}
