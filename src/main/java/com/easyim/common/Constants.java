package com.easyim.common;

/**
 * 服务端常量
 *
 * @author 单程车票
 */
public class Constants {

    /**
     * AttributeKey标识名
     */
    public interface AttributeKeyName {
        String MEETING_ID = "meetingID";
        String USER_NAME = "username";
    }

    /**
     * 消息类型
     */
    public interface ChatMessageType {
        Byte SYSTEM_TYPE = 0;
        Byte TEXT_TYPE = 1;
        Byte PICTURE_TYPE = 2;
        Byte FILE_TYPE = 3;
    }

    /**
     * 服务器错误码
     */
    public interface EasyIMError {
        String MEETING_CONFLICT = "【系统错误：会议冲突】";
        String MEETING_NO_EXIST = "【会议异常：会议不存在】";
    }

}
