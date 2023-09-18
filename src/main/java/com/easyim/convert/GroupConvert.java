package com.easyim.convert;

import com.easyim.comm.message.login.dto.GroupDto;
import com.easyim.dal.dataobject.GroupDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
 * Group 类型转换接口
 *
 * @author 单程车票
 */
@Mapper
public interface GroupConvert {

    GroupConvert INSTANCT = Mappers.getMapper(GroupConvert.class);

    List<GroupDto> convertDtoList(List<GroupDO> list);

}
