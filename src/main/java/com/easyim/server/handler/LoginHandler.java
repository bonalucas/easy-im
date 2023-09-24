package com.easyim.server.handler;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.login.LoginRequestMessage;
import com.easyim.comm.message.login.LoginResponseMessage;
import com.easyim.comm.message.login.dto.DialogDto;
import com.easyim.comm.message.login.dto.FriendDto;
import com.easyim.comm.message.login.dto.RecordDto;
import com.easyim.common.Constants;
import com.easyim.common.ServiceException;
import com.easyim.convert.DialogConvert;
import com.easyim.convert.GroupConvert;
import com.easyim.dal.dataobject.DialogDO;
import com.easyim.dal.dataobject.GroupDO;
import com.easyim.dal.dataobject.UserDO;
import com.easyim.server.util.SocketChannelUtil;
import com.easyim.service.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 登录处理器
 *
 * @author 单程车票
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class LoginHandler extends BaseHandler<LoginRequestMessage> {

    @Autowired
    private UserService userService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DialogService dialogService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private FriendService friendService;

    @Override
    public void channelRead(Channel channel, LoginRequestMessage msg) {
        log.info("登录消息处理请求：{} ", JSON.toJSONString(msg));
        boolean auth = userService.loginAuth(msg.getUserId(), msg.getUserPassword());
        if (!auth) throw new ServiceException("登录认证失败");
        // 登录成功加入缓存
        SocketChannelUtil.addChannel(msg.getUserId(), channel);
        // 绑定群组
        List<String> groupIdList = memberService.queryGroup(msg.getUserId());
        for (String groupId : groupIdList) {
            SocketChannelUtil.addGroup(groupId, channel);
        }
        // 封装反馈信息
        LoginResponseMessage loginResponse = new LoginResponseMessage();
        // 查询用户信息并填充结果
        UserDO user = userService.queryUser(msg.getUserId());
        loginResponse.setUserId(user.getUserId());
        loginResponse.setUserAvatar(user.getUserAvatar());
        loginResponse.setUserNickname(user.getUserNickname());
        // 查询群组信息列表并填充结果
        List<GroupDO> groupList = groupService.queryGroupList(groupIdList);
        loginResponse.setGroupList(GroupConvert.INSTANCT.convertDtoList(groupList));
        // 查询好友信息并填充结果
        List<String> friendIdList = friendService.queryFriendList(msg.getUserId());
        List<FriendDto> friendList = new ArrayList<>();
        for (String friendId : friendIdList) {
            UserDO friend = userService.queryUser(friendId);
            friendList.add(new FriendDto(friend.getUserId(), friend.getUserNickname(), friend.getUserAvatar()));
        }
        loginResponse.setFriendList(friendList);
        // 查询对话信息列表并填充结果
        List<DialogDO> dialogList = dialogService.queryDialogList(msg.getUserId());
        List<DialogDto> dialogDtoList = new ArrayList<>();
        for (DialogDO dialogDO : dialogList) {
            // 填充已有字段
            DialogDto dialogDto = DialogConvert.INSTANCT.convertDto(dialogDO);
            // 填充对话名称和对话头像
            if (Objects.equals(dialogDO.getDialogType(), Constants.DialogType.SINGLE_CHAT.getCode())) {
                UserDO receiver = userService.queryUser(dialogDO.getReceiverId());
                dialogDto.setName(receiver.getUserNickname());
                dialogDto.setAvatar(receiver.getUserAvatar());
            } else {
                GroupDO receiver = groupService.queryGroup(dialogDO.getReceiverId());
                dialogDto.setName(receiver.getGroupName());
                dialogDto.setAvatar(receiver.getGroupAvatar());
            }
            // 获取对话的聊天记录并填充结果
            List<RecordDto> recordDtoList = recordService.queryRecordDtoList(dialogDO.getDialogId());
            dialogDto.setRecordList(recordDtoList);
            dialogDto.setSketch(recordDtoList.get(recordDtoList.size()-1).getContent());
            dialogDto.setNow(recordDtoList.get(recordDtoList.size()-1).getRecordTime());
        }
        loginResponse.setDialogList(dialogDtoList);
        // 传输消息
        channel.writeAndFlush(loginResponse);
    }
}
