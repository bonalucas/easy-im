package com.easyim.comm.message;

import com.easyim.comm.message.chat.ChatRequestMessage;
import com.easyim.comm.message.chat.ChatResponseMessage;
import com.easyim.comm.message.dialog.DeleteDialogRequestMessage;
import com.easyim.comm.message.dialog.DialogNoticeRequestMessage;
import com.easyim.comm.message.dialog.DialogNoticeResponseMessage;
import com.easyim.comm.message.error.GlobalErrorResponseMessage;
import com.easyim.comm.message.file.FileUploadRequestMessage;
import com.easyim.comm.message.file.FileUploadResponseMessage;
import com.easyim.comm.message.friend.AddFriendRequestMessage;
import com.easyim.comm.message.friend.AddFriendResponseMessage;
import com.easyim.comm.message.friend.SearchFriendRequestMessage;
import com.easyim.comm.message.friend.SearchFriendResponseMessage;
import com.easyim.comm.message.heartbeat.HeartBeatRequestMessage;
import com.easyim.comm.message.heartbeat.HeartBeatResponseMessage;
import com.easyim.comm.message.login.LoginRequestMessage;
import com.easyim.comm.message.login.LoginResponseMessage;
import com.easyim.comm.message.reconnect.ReconnectRequestMessage;
import com.easyim.comm.message.register.RegisterRequestMessage;
import com.easyim.comm.message.register.RegisterResponseMessage;
import com.easyim.comm.message.test.TestRequestMessage;
import com.easyim.comm.message.test.TestResponseMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息基础类
 *
 * @author 单程车票
 */
public abstract class Message {

    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 服务端状态
     */
    private String serverStatus;

    private final static Map<Byte, Class<? extends Message>> messageType = new ConcurrentHashMap<>();

    static {
        messageType.put(MessageTypeConstants.GlobalErrorResponseMessage, GlobalErrorResponseMessage.class);
        messageType.put(MessageTypeConstants.LoginRequestMessage, LoginRequestMessage.class);
        messageType.put(MessageTypeConstants.LoginResponseMessage, LoginResponseMessage.class);
        messageType.put(MessageTypeConstants.DialogNoticeRequestMessage, DialogNoticeRequestMessage.class);
        messageType.put(MessageTypeConstants.DialogNoticeResponseMessage, DialogNoticeResponseMessage.class);
        messageType.put(MessageTypeConstants.DeleteDialogRequestMessage, DeleteDialogRequestMessage.class);
        messageType.put(MessageTypeConstants.SearchFriendRequestMessage, SearchFriendRequestMessage.class);
        messageType.put(MessageTypeConstants.SearchFriendResponseMessage, SearchFriendResponseMessage.class);
        messageType.put(MessageTypeConstants.AddFriendRequestMessage, AddFriendRequestMessage.class);
        messageType.put(MessageTypeConstants.AddFriendResponseMessage, AddFriendResponseMessage.class);
        messageType.put(MessageTypeConstants.ChatRequestMessage, ChatRequestMessage.class);
        messageType.put(MessageTypeConstants.ChatResponseMessage, ChatResponseMessage.class);
        messageType.put(MessageTypeConstants.FileUploadRequestMessage, FileUploadRequestMessage.class);
        messageType.put(MessageTypeConstants.FileUploadResponseMessage, FileUploadResponseMessage.class);
        messageType.put(MessageTypeConstants.RegisterRequestMessage, RegisterRequestMessage.class);
        messageType.put(MessageTypeConstants.RegisterResponseMessage, RegisterResponseMessage.class);
        messageType.put(MessageTypeConstants.ReconnectRequestMessage, ReconnectRequestMessage.class);
        messageType.put(MessageTypeConstants.HeartBeatRequestMessage, HeartBeatRequestMessage.class);
        messageType.put(MessageTypeConstants.HeartBeatResponseMessage, HeartBeatResponseMessage.class);

        messageType.put(MessageTypeConstants.TestRequestMessage, TestRequestMessage.class);
        messageType.put(MessageTypeConstants.TestResponseMessage, TestResponseMessage.class);
    }

    public static Class<? extends Message> get(Byte constant) {
        return messageType.get(constant);
    }

    /**
     * 获取消息类型常量
     *
     * @return 返回消息类型常量
     */
    public abstract Byte getConstant();

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }

}
