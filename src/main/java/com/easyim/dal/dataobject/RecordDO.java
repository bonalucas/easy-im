package com.easyim.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 聊天记录 DO
 *
 * @author 单程车票
 */
@TableName("easy_im_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordDO {

    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 对话ID
     */
    private String dialogId;

    /**
     * 发送者ID
     */
    private String senderId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息时间
     */
    private Date recordTime;

}
