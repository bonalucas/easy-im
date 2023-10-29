package com.easyim.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * User 类型转换接口
 *
 * @author 单程车票
 */
@Mapper
public interface UserConvert {

    UserConvert INSTANCT = Mappers.getMapper(UserConvert.class);

//    UserDto convertDto(UserDO bean);

}
