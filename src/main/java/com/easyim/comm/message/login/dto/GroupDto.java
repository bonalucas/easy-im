package com.easyim.comm.message.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 群组 Dto
 *
 * @author 单程车票
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {

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

}
