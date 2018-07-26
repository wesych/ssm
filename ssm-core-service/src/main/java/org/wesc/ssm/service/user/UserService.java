package org.wesc.ssm.service.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wesc.ssm.dao.entity.Permission;
import org.wesc.ssm.dao.entity.Role;
import org.wesc.ssm.dao.entity.User;
import org.wesc.ssm.dao.entity.UserExample;
import org.wesc.ssm.dao.mapper.UserMapper;
import org.wesc.ssm.dao.querymapper.UserRolesAndPermissionsQueryMapper;
import org.wesc.ssm.service.exception.ErrorCodes;
import org.wesc.ssm.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户服务类
 *
 * @Auther: Wesley Cheung
 * @Date: 2018/7/10 20:41
 */
@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRolesAndPermissionsQueryMapper queryUserRolesAndPermissionsMapper;

    /**
     * Add a user.
     * @param user
     */
    public void addUser(User user) {
        userMapper.insertSelective(user);
    }

    /**
     * Find by Account.
     * @param account
     * @return
     */
    public User findUserByAccount(String account) throws ServiceException {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        List<User> users = userMapper.selectByExample(example);
        if (null == users || users.size() == 0) {
            logger.warn("No such account found in user info!");
            return null;
        }
        if (users.size() > 1) {
            logger.error("Account should be unique to each user!");
            throw new ServiceException(ErrorCodes.TOO_MANY_MOBILES, "Account should be unique to each user.");
        }
        return  users.get(0);
    }

    /**
     * Find by Mobile.
     * @param mobile
     * @return
     */
    public User findUserByMobile(String mobile) throws ServiceException {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andMobileEqualTo(mobile);
        List<User> users = userMapper.selectByExample(example);
        if (null == users || users.size() == 0) {
            logger.warn("No such mobile found in user info!");
            return null;
        }
        if (users.size() > 1) {
            logger.error("Email should be unique to each user!");
            throw new ServiceException(ErrorCodes.TOO_MANY_MOBILES, "Mobile should be unique to each user.");
        }
        return  users.get(0);
    }

    /**
     * Find by Email.
     * @param email
     * @return
     * @throws ServiceException
     */
    public User findUserByEmail(String email) throws ServiceException {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(email);
        List<User> users = userMapper.selectByExample(example);
        if (null == users || users.size() == 0) {
            logger.warn("No such email found in user info!");
            return null;
        }
        if (users.size() > 1) {
            logger.error("Email should be unique to each user!");
            throw new ServiceException(ErrorCodes.TOO_MANY_EMAILS, "Email should be unique to each user.");
        }
        return  users.get(0);
    }

    /**
     * Find all users.
     * @return
     */
    public List<User> findAllUser() {
        return userMapper.selectByExample(new UserExample());
    }

    /**
     * Find All Account.
     * @return
     */
    public List<String> findAllAccounts(){
        List<String> accountList = new ArrayList<>();
        for (User user : findAllUser()) {
            accountList.add(user.getAccount());
        }
        return accountList;
    }

    /**
     * Find All Mobile.
     * @return
     */
    public List<String> findAllMobiles() {
        List<String> mobileList = new ArrayList<>();
        for (User user : findAllUser()) {
            mobileList.add(user.getMobile());
        }
        return mobileList;
    }

    /**
     * Find All Email.
     * @return
     */
    public List<String> findAllEmails() {
        List<String> emailList = new ArrayList<>();
        for (User user : findAllUser()) {
            if (user.getEmail() != null) {
                emailList.add(user.getEmail());
            }
        }
        return emailList;
    }

    /**
     * Find user roles.
     * @param userId
     * @return
     */
    public List<Role> findUserRolesById(int userId) {
        List<Role> roles = queryUserRolesAndPermissionsMapper.findAllRolesByUserId(userId);
        return  roles;
    }

    /**
     * Find user permissions.
     * @param userId
     * @return
     */
    public List<Permission> findUserPermissionsById(int userId) {
        return queryUserRolesAndPermissionsMapper.findAllPermissionsByUserId(userId);
    }

    /**
     * 获取加密盐
     * @return
     */
    public String getCredentialSalt(User user){
        return "ssm_" + user.getAccount() + "_" + user.getMobile() + "_" + user.getEmail();
    }

}
