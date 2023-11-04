package com.easyim.common;

import com.easyim.pojo.MeetingDO;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务器会话工具类
 *
 * @author 单程车票
 */
public class ServerSessionUtil {

    /**
     * 会议缓存
     */
    private static final Map<String, MeetingDO> meetingCache = new ConcurrentHashMap<>();

    /**
     * 创建会议
     *
     * @param meetingID 会议ID
     * @param theme 会议主题
     * @param channel 创建者信道
     */
    public synchronized static void createMeeting(String meetingID, String theme, Channel channel) {
        if (meetingCache.containsKey(meetingID)) return;
        MeetingDO meetingDO = MeetingDO.builder()
            .meetingID(meetingID)
            .creator(channel)
            .theme(theme)
            .channelGroup(new DefaultChannelGroup(GlobalEventExecutor.INSTANCE)).build();
        meetingDO.getChannelGroup().add(channel);
        meetingCache.put(meetingID, meetingDO);
    }

    /**
     * 加入会议
     *
     * @param meetingID 会议ID
     * @param channel 通道
     */
    public synchronized static void addMeeting(String meetingID, Channel channel) {
        if (!meetingCache.containsKey(meetingID)) return;
        meetingCache.get(meetingID).getChannelGroup().add(channel);
    }

    /**
     * 获取会议
     *
     * @param meetingID 会议ID
     * @return 会议
     */
    public static MeetingDO getMeeting(String meetingID) {
        return meetingCache.get(meetingID);
    }

    /**
     * 获取会议通道
     *
     * @param meetingID 会议ID
     * @return 会议通道
     */
    public static ChannelGroup getMeetingChannel(String meetingID) {
        if (!meetingCache.containsKey(meetingID)) return null;
        return meetingCache.get(meetingID).getChannelGroup();
    }

    /**
     * 判断会议是否存在
     *
     * @param meetingID 会议ID
     * @return 反馈
     */
    public static boolean isExist(String meetingID) {
        return meetingCache.containsKey(meetingID);
    }

    /**
     * 结束会议
     *
     * @param meetingID 会议ID
     */
    public synchronized static void closeMeeting(String meetingID){
        if (!meetingCache.containsKey(meetingID)) return;
        meetingCache.remove(meetingID);
    }

    /**
     * 退出会议
     *
     * @param meetingID 会议ID
     * @param channel 请求通道
     */
    public synchronized static void leaveMeeting(String meetingID, Channel channel){
        if (!meetingCache.containsKey(meetingID)) return;
        meetingCache.get(meetingID).getChannelGroup().remove(channel);
    }

}
