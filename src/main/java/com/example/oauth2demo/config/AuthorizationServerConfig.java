package com.example.oauth2demo.config;

import com.example.oauth2demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * @program: demo
 * @description: 认证服务器配置
 * @author: HyJan
 * @create: 2020-04-30 10:17
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    /**
     * 客户端请求认证配置
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 配置clientId
                .withClient("admin")
                // 配置client的密码
                .secret(passwordEncoder.encode("admin123456"))
                // 配置token的访问有效期
                .accessTokenValiditySeconds(3600)
                // 配置刷新token的有效期
                .refreshTokenValiditySeconds(864000)
                // 单点登录时，如果跳转地址是一定要填的；授权成功后跳转，这个授权码会返回给改地址，所以一般这个地址是后台的，然后获取授权码，拿授权码post拿到token
                .redirectUris("http://www.baidu.com")
                // 开启自动授权，开启之后，就不用再点击授权了，所以线上一般都是不开启的
//                .autoApprove(true)
                // 配置申请的权限范围
                .scopes("all")
                // 配置授权类型
                .authorizedGrantTypes("authorization_code","password","refresh_token");
    }

    /**
     * 使用密码模式时要配置
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService);
    }
}
