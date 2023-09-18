package com.easyim.service;

import com.easyim.dal.dataobject.DialogDO;

import java.awt.*;
import java.util.List;

/**
 * 对话业务接口
 *
 * @author 单程车票
 */
public interface DialogService {

    /**
     * 添加单聊对话
     * @param senderId 发送者ID
     * @param receiverId 接收者ID
     */
    void createSingleChatDialog(String senderId, String receiverId);

    /**
     * 添加群聊对话
     * @param groupId 群聊ID
     * @param senderId 发送者ID
     */
    void createGroupChatDialog(String groupId, String senderId);

    /**
     * 查询用户的对话列表
     * @param senderId 发送者ID
     * @return 对话列表
     */
    List<DialogDO> queryDialogList(String senderId);

    /**
     * 查询指定对话信息
     * @param senderId 发送者ID
     * @param receiverId 接收者ID
     * @return 对话信息
     */
    DialogDO queryDialog(String senderId, String receiverId);

    /**
     * 删除对话
     * @param dialogId 对话唯一ID
     */
    void deleteDialog(String dialogId);

}
