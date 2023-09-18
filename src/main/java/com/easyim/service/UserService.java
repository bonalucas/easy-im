package com.easyim.service;

import com.easyim.dal.dataobject.UserDO;

import java.util.List;

/**
 * 用户业务接口
 *
 * @author 单程车票
 */
public interface UserService {

    /**
     * 注册用户
     * @param userId 用户唯一ID
     * @param userPassword 用户密码
     * @param userNickname 用户昵称
     * @param userAvatar 用户头像
     */
    void register(String userId, String userPassword, String userNickname, String userAvatar);

    /**
     * 登录认证
     * @param userId 用户唯一ID
     * @param userPassword 用户密码
     * @return 认证结果反馈
     */
    boolean loginAuth(String userId, String userPassword);

    /**
     * 查询用户基本信息
     * @param userId 用户唯一ID
     * @return 用户信息
     */
    UserDO queryUser(String userId);

    /**
     * 模糊查询用户列表
     * @param userNickname 用户昵称
     * @return 用户列表
     */
    List<UserDO> queryUserByNickname(String userNickname);

}
