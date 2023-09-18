package com.easyim.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 群组成员 DO
 *
 * @author 单程车票
 */
@TableName("easy_im_member")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDO {

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
     * 群组ID
     */
    private String groupId;

    /**
     * 创建时间
     */
    private Date createTime;

}
