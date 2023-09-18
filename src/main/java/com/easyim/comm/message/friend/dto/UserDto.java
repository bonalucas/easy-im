package com.easyim.comm.message.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户 Dto
 *
 * @author 单程车票
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

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
     * 用户状态
     */
    private Integer status;

}
