package com.easyim.service;

import java.util.List;

/**
 * 群成员业务接口
 *
 * @author 单程车票
 */
public interface MemberService {

    /**
     * 保存群组成员信息
     * @param userId 用户唯一ID
     * @param groupId 群组ID
     * @return 保存结果反馈
     */
    boolean saveMember(String userId, String groupId);

    /**
     * 查询群成员用户ID列表
     * @param groupId 群组ID
     * @return 用户ID列表
     */
    List<String> queryMember(String groupId);

    /**
     * 查询用户的群组信息列表
     * @param userId 用户唯一ID
     * @return 群组信息列表
     */
    List<String> queryGroup(String userId);

}
