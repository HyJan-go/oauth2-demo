package com.example.oauth2demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: demo
 * @description: 测试控制类
 * @author: HyJan
 * @create: 2020-04-30 10:43
 **/
@RequestMapping("/user")
@RestController
public class UserController {

    /**
     * 添加需要登录的接口用于测试
     *
     * 测试地址
     * http://localhost:9000/oauth/authorize?response_type=code&client_id=admin&redirect_uri=http://www.baidu.com&scope=all&state=normal
     *
     * 从上面地址中获取code的值
     *
     * 获取token 的地址（post） localhost:9000/oauth/token
     *
     * 要认真跟jwt-demo的进行比较，这不是解析jwt，而是直接通过token进行返回信息
     * @param authentication
     * @return
     */
    @RequestMapping("/info")
    public Object getUserInfo(Authentication authentication){
        return authentication.getPrincipal();
    }
}
