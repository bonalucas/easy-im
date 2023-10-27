package com.easyim.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easyim.comm.server.common.ServerConstants;
import com.easyim.dal.dataobject.DialogDO;
import com.easyim.dal.mapper.DialogMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 对话业务实现类
 *
 * @author 单程车票
 */
@Service
public class DialogServiceImpl implements DialogService {

    @Autowired
    private DialogMapper dialogMapper;

    @Transactional
    @Override
    public void createSingleChatDialog(String senderId, String receiverId) {
        String dialogId = IdUtil.getSnowflakeNextIdStr();
        dialogMapper.insert(new DialogDO(null, dialogId, senderId, receiverId, ServerConstants.DialogType.SINGLE_CHAT.getCode(), DateUtil.date()));
        dialogMapper.insert(new DialogDO(null, dialogId, receiverId, senderId, ServerConstants.DialogType.SINGLE_CHAT.getCode(), DateUtil.date()));
    }

    @Override
    public void createGroupChatDialog(String groupId, String senderId) {
        dialogMapper.insert(new DialogDO(null, groupId, senderId, groupId, ServerConstants.DialogType.GROUP_CHAT.getCode(), DateUtil.date()));
    }

    @Override
    public List<DialogDO> queryDialogList(String senderId) {
        LambdaQueryWrapper<DialogDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(senderId), DialogDO::getSenderId, senderId);
        return dialogMapper.selectList(queryWrapper);
    }

    @Override
    public DialogDO queryDialog(String senderId, String receiverId) {
        LambdaQueryWrapper<DialogDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(senderId), DialogDO::getSenderId, senderId)
                .eq(StringUtils.isNotEmpty(receiverId), DialogDO::getReceiverId, receiverId);
        return dialogMapper.selectOne(queryWrapper);
    }

    @Override
    public void deleteDialog(String dialogId) {
        LambdaQueryWrapper<DialogDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(dialogId), DialogDO::getDialogId, dialogId);
        dialogMapper.delete(queryWrapper);
    }
}
