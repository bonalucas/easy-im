package com.easyim.comm.message;

/**
 * 消息类型
 *
 * @author 单程车票
 */
public interface MessageTypeConstants {

    Byte LoginRequestMessage = 1;
    Byte LoginResponseMessage = 2;
    Byte AddFriendRequestMessage = 3;
    Byte AddFriendResponseMessage = 4;
    Byte SearchFriendRequestMessage = 5;
    Byte SearchFriendResponseMessage = 6;
    Byte DeleteDialogRequestMessage = 7;
    Byte DialogNoticeRequestMessage = 8;
    Byte DialogNoticeResponseMessage = 9;

}
