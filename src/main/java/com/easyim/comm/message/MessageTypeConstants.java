package com.easyim.comm.message;

import com.easyim.comm.message.login.LoginRequestMessage;

/**
 * 消息类型
 *
 * @author 单程车票
 */
public interface MessageTypeConstants {

    Byte GlobalErrorResponseMessage = 0;
    Byte LoginRequestMessage = 1;
    Byte LoginResponseMessage = 2;
    Byte AddFriendRequestMessage = 3;
    Byte AddFriendResponseMessage = 4;
    Byte SearchFriendRequestMessage = 5;
    Byte SearchFriendResponseMessage = 6;
    Byte DeleteDialogRequestMessage = 7;
    Byte DialogNoticeRequestMessage = 8;
    Byte DialogNoticeResponseMessage = 9;
    Byte ChatRequestMessage = 10;
    Byte ChatResponseMessage = 11;
    Byte FileUploadRequestMessage = 12;
    Byte FileUploadResponseMessage = 13;
    Byte RegisterRequestMessage = 14;
    Byte RegisterResponseMessage = 15;

}
