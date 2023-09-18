package com.easyim.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyim.comm.message.login.dto.RecordDto;
import com.easyim.dal.dataobject.RecordDO;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 聊天记录 Mapper
 *
 * @author 单程车票
 */
@Mapper
@Repository
public interface RecordMapper extends BaseMapper<RecordDO> {

    List<RecordDto> selectDtoList(@Param("dialogId") String dialogId);

}
