package com.xibu.springbootrestfull.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：HEJIAN
 * @Class ：HelloController
 * @date ：Created in 2020-05-05 12:45
 * @description：向页面输出字符串
 * @modified By：
 * @version: 1.0$
 */
@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World";
    }
}
