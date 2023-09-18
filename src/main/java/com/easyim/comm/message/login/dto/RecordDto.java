package com.easyim.comm.message.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 聊天记录 Dto
 *
 * @author 单程车票
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordDto {

    /**
     * 对话ID
     */
    private String dialogId;

    /**
     * 发送者ID
     */
    private String senderId;

    /**
     * 发送者名称
     */
    private String name;

    /**
     * 发送者头像
     */
    private String avatar;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息时间
     */
    private Date recordTime;

}
