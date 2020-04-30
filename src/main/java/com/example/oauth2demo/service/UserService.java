package com.example.oauth2demo.service;

import com.example.oauth2demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: demo
 * @description: 用户service接口，一定要实现UserDetailsService这个接口，然后标注为一个service
 * @author: HyJan
 * @create: 2020-04-30 09:58
 **/
@Service
public class UserService implements UserDetailsService {

    /**
     * 上面正常应实现的逻辑是获取所有的资源，然后用于给下边的方法进行判断
     * 但是这里没有其他的服务或者是连上数据库，所以这里仅仅在程序定义一些假数据进行测试
     * 如果可以的话，则是连上数据库获取是调用其他服务获取资源的事情了
     */
    private List<User> users;

    @Autowired
     private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        users = new ArrayList<>();
        String password = passwordEncoder.encode("123456");
        // 最后一个参数表示认证该用户拥有的权利，可能有多个，所以是一个列表
        users.add(new User("HyJan",password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        users.add(new User("Lily",password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        users.add(new User("CanJ",password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
        users.add(new User("Copper",password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
    }


    /**
     * 实现的此方法，主要是看用户是否是我们的用户，如果是的话则进行授权
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //先去获取用户资源列表，然后进行判断是否包含请求的用户，这里假设users就是我们获取到的资源列表
        List<User> userList = users.stream().filter(user -> user.getUsername().equals(userName))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(userList)){
            return userList.get(0);
        }else {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
    }
}
