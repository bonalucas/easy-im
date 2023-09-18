package com.easyim.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easyim.dal.dataobject.MemberDO;
import com.easyim.dal.mapper.MemberMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 群成员业务实现类
 *
 * @author 单程车票
 */
@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public boolean saveMember(String userId, String groupId) {
        MemberDO member = new MemberDO(null, userId, groupId, DateUtil.date());
        return memberMapper.insert(member) > 0;
    }

    @Override
    public List<String> queryMember(String groupId) {
        LambdaQueryWrapper<MemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(groupId), MemberDO::getGroupId, groupId);
        return memberMapper.selectList(queryWrapper).stream().map(MemberDO::getUserId).collect(Collectors.toList());
    }

    @Override
    public List<String> queryGroup(String userId) {
        LambdaQueryWrapper<MemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(userId), MemberDO::getUserId, userId);
        return memberMapper.selectList(queryWrapper).stream().map(MemberDO::getGroupId).collect(Collectors.toList());
    }

}
