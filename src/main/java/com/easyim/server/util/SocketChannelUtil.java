package com.easyim.server.util;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通道工具类
 *
 * @author 单程车票
 */
public class SocketChannelUtil {

    /**
     * 存储通道信息
     */
    private static final Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    /**
     * 存储用户信息
     */
    private static final Map<String, String> userMap = new ConcurrentHashMap<>();

    /**
     * 存储群组通道
     */
    private static final Map<String, ChannelGroup> groupMap = new ConcurrentHashMap<>();

    /**
     * 添加用户信息和通道信息
     * @param userId 用户唯一ID
     * @param channel 通道唯一ID
     */
    public static void addChannel(String userId, Channel channel) {
        channelMap.put(userId, channel);
        userMap.put(channel.id().toString(), userId);
    }

    /**
     * 移除通道信息
     * @param channelId 通道ID
     */
    public static void removeChannelByChannelId(String channelId){
        String userId = userMap.get(channelId);
        if (null == userId) return;
        channelMap.remove(userId);
    }

    /**
     * 移除通道信息
     * @param userId 用户唯一ID
     */
    public static void removeChannelByUserId(String userId){
        channelMap.remove(userId);
    }

    /**
     * 获取通道信息
     * @param userId 用户唯一ID
     * @return 通道信息
     */
    public static Channel getChannel(String userId) {
        return channelMap.get(userId);
    }

    public static boolean hasAuth(String channelId) {
        return userMap.get(channelId) != null;
    }

    /**
     * 添加群组成员通信管道
     * @param groupId 群组ID
     * @param channel 成员通道
     */
    public synchronized static void addGroup(String groupId, Channel channel) {
        ChannelGroup channelGroup = groupMap.get(groupId);
        if (null == channelGroup) {
            channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            groupMap.put(groupId, channelGroup);
        }
        channelGroup.add(channel);
    }

    /**
     * 移除群组成员通道
     * @param groupId 群组ID
     * @param channel 成员通道
     */
    public synchronized static void removeGroup(String groupId, Channel channel){
        ChannelGroup channelGroup = groupMap.get(groupId);
        if (null == channelGroup) return;
        channelGroup.remove(channel);
    }

    /**
     * 移除指定用户的所有群组通道
     * @param channel 成员通道
     */
    public static void removeAllGroup(Channel channel){
        for (ChannelGroup next : groupMap.values()) {
            next.remove(channel);
        }
    }

    /**
     * 获取群组通信管道
     * @param groupId 群组ID
     * @return 群组通道
     */
    public static ChannelGroup getGroup(String groupId) {
        return groupMap.get(groupId);
    }

}
