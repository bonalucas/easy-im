package com.easyim.comm.message.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 好友 Dto
 *
 * @author 单程车票
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendDto {

    /**
     * 好友用户ID
     */
    private String friendId;

    /**
     * 好友名称
     */
    private String friendName;

    /**
     * 好友头像
     */
    private String friendAvatar;

}
