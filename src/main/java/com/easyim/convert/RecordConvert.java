package com.easyim.convert;

import com.easyim.comm.message.login.dto.RecordDto;
import com.easyim.dal.dataobject.RecordDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * Record 类型转换接口
 *
 * @author 单程车票
 */
@Mapper
public interface RecordConvert {

    RecordConvert INSTANCT = Mappers.getMapper(RecordConvert.class);

    RecordDto convertDto(RecordDO bean);

}
