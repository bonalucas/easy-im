package com.easyim.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easyim.comm.message.login.dto.RecordDto;
import com.easyim.dal.dataobject.RecordDO;
import com.easyim.dal.mapper.RecordMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 聊天记录业务实现类
 *
 * @author 单程车票
 */
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public List<RecordDO> queryRecordList(String dialogId) {
        // 根据条件查询聊天记录列表
        LambdaQueryWrapper<RecordDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(dialogId), RecordDO::getDialogId, dialogId)
                .orderByAsc(RecordDO::getId);
        return recordMapper.selectList(queryWrapper);
    }

    @Override
    public List<RecordDto> queryRecordDtoList(String dialogId) {
        return recordMapper.selectDtoList(dialogId);
    }

    @Override
    public void saveRecord(String dialogId, String senderId, String content) {
        RecordDO record = new RecordDO(null, dialogId, senderId, content, DateUtil.date());
        recordMapper.insert(record);
    }
}
