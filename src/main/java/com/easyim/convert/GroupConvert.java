package com.easyim.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * Group 类型转换接口
 *
 * @author 单程车票
 */
@Mapper
public interface GroupConvert {

    GroupConvert INSTANCT = Mappers.getMapper(GroupConvert.class);

//    List<GroupDto> convertDtoList(List<GroupDO> list);

}
