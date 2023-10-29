package com.easyim.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyim.dal.dataobject.RecordDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 聊天记录 Mapper
 *
 * @author 单程车票
 */
@Mapper
public interface RecordMapper extends BaseMapper<RecordDO> {

//    List<RecordDto> selectDtoList(@Param("dialogId") String dialogId);

}
