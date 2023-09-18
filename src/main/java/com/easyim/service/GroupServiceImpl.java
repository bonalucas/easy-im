package com.easyim.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easyim.dal.dataobject.GroupDO;
import com.easyim.dal.mapper.GroupMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 群组业务实现类
 *
 * @author 单程车票
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public List<GroupDO> queryGroupList(List<String> groupIdList) {
        // 根据userId查询群组列表
        LambdaQueryWrapper<GroupDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(!CollectionUtils.isEmpty(groupIdList), GroupDO::getGroupId, groupIdList);
        return groupMapper.selectList(queryWrapper);
    }

    @Override
    public boolean createGroup(String groupName, String groupAvatar) {
        GroupDO group = new GroupDO(null, IdUtil.getSnowflakeNextIdStr(), groupName, groupAvatar, DateUtil.date());
        return groupMapper.insert(group) > 0;
    }

    @Override
    public boolean deleteGroup(String groupId) {
        LambdaQueryWrapper<GroupDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(groupId), GroupDO::getGroupId, groupId);
        return groupMapper.delete(queryWrapper) > 0;
    }

    @Override
    public GroupDO queryGroup(String groupId) {
        LambdaQueryWrapper<GroupDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(groupId), GroupDO::getGroupId, groupId);
        return groupMapper.selectOne(queryWrapper);
    }

}
