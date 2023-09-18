package com.easyim.comm.message.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 对话 Dto
 *
 * @author 单程车票
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DialogDto {

    /**
     * 对话ID
     */
    private String dialogId;

    /**
     * 对话类型
     */
    private Integer dialogType;

    /**
     * 对话名称
     */
    private String name;

    /**
     * 对话头像
     */
    private String avatar;

    /**
     * 对话简述
     */
    private String sketch;

    /**
     * 最新消息时间
     */
    private Date now;

    /**
     * 聊天记录
     */
    private List<RecordDto> RecordList;

}
