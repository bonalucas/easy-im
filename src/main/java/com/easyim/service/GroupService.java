package com.easyim.service;

import com.easyim.dal.dataobject.GroupDO;

import java.util.List;

/**
 * 群组业务接口
 *
 * @author 单程车票
 */
public interface GroupService {

    /**
     * 查询群组列表
     * @param groupIdList 群组ID列表
     * @return 群组列表
     */
    List<GroupDO> queryGroupList(List<String> groupIdList);

    /**
     * 新增群组
     * @param groupName 群组名称
     * @param groupAvatar 群组头像
     * @return 新增结果反馈
     */
    boolean createGroup(String groupName, String groupAvatar);

    /**
     * 删除群组
     * @param groupId 群组ID
     * @return 删除结果反馈
     */
    boolean deleteGroup(String groupId);

    /**
     * 查询群组
     * @param groupId 群组ID
     * @return 群组信息
     */
    GroupDO queryGroup(String groupId);

}
