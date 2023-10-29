package com.easyim.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * Friend 类型转换接口
 *
 * @author 单程车票
 */
@Mapper
public interface FriendConvert {

    FriendConvert INSTANCT = Mappers.getMapper(FriendConvert.class);

//    List<FriendDto> convertDtoList(List<FriendDO> list);

}
