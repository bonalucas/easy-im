package com.easyim.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户 DO
 *
 * @author 单程车票
 */
@TableName("easy_im_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDO {

    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Long Id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户昵称
     */
    private String userNickname;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 创建时间
     */
    private Date createTime;

}
