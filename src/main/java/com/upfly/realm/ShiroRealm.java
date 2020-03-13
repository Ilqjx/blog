package com.upfly.realm;

import com.upfly.po.User;
import com.upfly.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getPrincipal().toString();
        User user = userService.getUser(username);
        if (user == null) {
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
        return info;
    }

}
