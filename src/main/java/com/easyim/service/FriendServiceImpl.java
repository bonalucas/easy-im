package com.easyim.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easyim.dal.dataobject.FriendDO;
import com.easyim.dal.mapper.FriendMapper;
import com.easyim.common.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 好友业务实现类
 *
 * @author 单程车票
 */
@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Override
    public List<String> queryFriendList(String userId) {
        // 根据userId查询好友列表
        LambdaQueryWrapper<FriendDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(userId), FriendDO::getUserId, userId);
        return friendMapper.selectList(queryWrapper).stream().map(FriendDO::getFriendId).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void saveFriend(String userId, String friendId) {
        // 根据查询条件判断好友是否已存在
        LambdaQueryWrapper<FriendDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(userId), FriendDO::getUserId, userId)
                .eq(StringUtils.isNotEmpty(friendId), FriendDO::getFriendId, friendId);
        FriendDO isExist = friendMapper.selectOne(queryWrapper);
        if (isExist == null) throw new CustomException("好友已存在");
        // 添加好友
        friendMapper.insert(new FriendDO(null, userId, friendId, DateUtil.date()));
        friendMapper.insert(new FriendDO(null, friendId, userId, DateUtil.date()));
    }

    @Override
    public boolean deleteFriend(String userId, String friendId) {
        // 根据查询条件删除好友
        LambdaQueryWrapper<FriendDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(userId), FriendDO::getUserId, userId)
                .eq(StringUtils.isNotEmpty(friendId), FriendDO::getFriendId, friendId);
        return friendMapper.delete(queryWrapper) > 0;
    }

}
