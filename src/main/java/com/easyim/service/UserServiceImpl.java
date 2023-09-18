package com.easyim.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easyim.dal.dataobject.UserDO;
import com.easyim.dal.mapper.UserMapper;
import com.easyim.common.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户业务实现类
 *
 * @author 单程车票
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(String userId, String userPassword, String userNickname, String userAvatar) {
        // 判断 userId 是否存在
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(userId), UserDO::getUserId, userId);
        UserDO isExist = userMapper.selectOne(queryWrapper);
        if (isExist != null) throw new CustomException("该账号已被注册");
        // 创建用户对象并入库
        UserDO user = new UserDO(null, userId, userNickname, userAvatar, userPassword, DateUtil.date());
        userMapper.insert(user);
    }

    @Override
    public boolean loginAuth(String userId, String userPassword) {
        // 根据 userId 获取用户信息
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(userId), UserDO::getUserId, userId);
        UserDO user = userMapper.selectOne(queryWrapper);
        if (user == null) throw new CustomException("该账号不存在");
        return userPassword.equals(user.getUserPassword());
    }

    @Override
    public UserDO queryUser(String userId) {
        // 根据 userId 获取用户信息
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(userId), UserDO::getUserId, userId);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public List<UserDO> queryUserByNickname(String userNickname) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(userNickname), UserDO::getUserNickname, userNickname);
        return userMapper.selectList(queryWrapper);
    }
}
