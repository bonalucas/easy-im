package com.easyim.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 群组 DO
 *
 * @author 单程车票
 */
@TableName("easy_im_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDO {

    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 群组ID
     */
    private String groupId;

    /**
     * 群组名称
     */
    private String groupName;

    /**
     * 群组头像
     */
    private String groupAvatar;

    /**
     * 创建时间
     */
    private Date createTime;

}
