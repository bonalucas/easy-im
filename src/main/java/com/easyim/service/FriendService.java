package com.easyim.service;

import java.util.List;

/**
 * 好友业务接口
 *
 * @author 单程车票
 */
public interface FriendService {

    /**
     * 查询好友列表
     * @param userId 用户唯一ID
     * @return 好友ID列表
     */
    List<String> queryFriendList(String userId);

    /**
     * 添加好友
     * @param userId 用户唯一ID
     * @param friendId 好友用户唯一ID
     */
    void saveFriend(String userId, String friendId);

    /**
     * 删除好友
     * @param userId 用户唯一ID
     * @param friendId 好友用户唯一ID
     * @return 删除结果反馈
     */
    boolean deleteFriend(String userId, String friendId);

}
