package com.easyim.pojo;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import lombok.*;

import java.util.List;

/**
 * 会议DO
 *
 * @author 单程车票
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MeetingDO {

    /**
     * 会议ID
     */
    private String meetingID;

    /**
     * 创建者通道
     */
    private Channel creator;

    /**
     * 会议主题
     */
    private String theme;

    /**
     * 会议通道
     */
    private ChannelGroup channelGroup;

}
