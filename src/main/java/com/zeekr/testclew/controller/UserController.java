package com.zeekr.testclew.controller;

import com.zeekr.testclew.dao.UserMapper;
import com.zeekr.testclew.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author xibu
 * @Date UserController 22:45
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("id")
    public User getOneUser(@PathVariable("id") Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

}
