package com.easyim.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 好友 DO
 *
 * @author 单程车票
 */
@TableName("easy_im_friend")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendDO {

    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 好友用户ID
     */
    private String friendId;

    /**
     * 创建时间
     */
    private Date createTime;

}
