package com.easyim.service;

import com.easyim.dal.dataobject.RecordDO;

import java.util.List;

/**
 * 聊天记录业务接口
 *
 * @author 单程车票
 */
public interface RecordService {

    /**
     * 查询聊天记录列表
     * @param dialogId 对话ID
     * @return 聊天记录列表
     */
    List<RecordDO> queryRecordList(String dialogId);

    /**
     * 查询聊天记录列表
     * @param dialogId 对话ID
     * @return 聊天记录列表
     */
//    List<RecordDto> queryRecordDtoList(String dialogId);

    /**
     * 添加聊天记录
     * @param dialogId 对话ID
     * @param senderId 发送者ID
     * @param content 内容
     */
    void saveRecord(String dialogId, String senderId, String content);
}
