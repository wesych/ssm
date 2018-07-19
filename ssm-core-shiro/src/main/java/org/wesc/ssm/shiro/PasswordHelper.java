package org.wesc.ssm.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wesc.ssm.dao.entity.User;
import org.wesc.ssm.service.user.UserService;

/**
 * @author Wesley Cheung
 * @Date Created in 20:24 2017/12/21
 */
@Service
public class PasswordHelper {

    @Autowired
    private UserService userService;

    /**
     * 算法名称：md5, sha-1, sha-224, sha-256, sha-384, sha-512
     */
    @Value("${password.algorithmName}")
    private String algorithmName;

    /**
     * 迭代次数
     */
    @Value("${password.hashIterations}")
    private int hashIterations;

    /**
     * 用户密码加密
     * @param user
     */
    public String encryptPassword(User user) {
        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                new CustomSimpleByteSource(userService.getCredentialSalt(user)),
                hashIterations
        ).toHex();
        user.setPassword(newPassword);

        return newPassword;
    }
}
