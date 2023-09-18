package com.easyim.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 对话 DO
 *
 * @author 单程车票
 */
@TableName("easy_im_dialog")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DialogDO {

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
     * 接收者ID
     */
    private String receiverId;

    /**
     * 对话类型
     */
    private Integer dialogType;

    /**
     * 创建时间
     */
    private Date createTime;

}
