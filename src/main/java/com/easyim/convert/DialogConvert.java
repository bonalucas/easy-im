package com.easyim.convert;

import com.easyim.comm.message.login.dto.DialogDto;
import com.easyim.dal.dataobject.DialogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * Dialog 类型转换接口
 *
 * @author 单程车票
 */
@Mapper
public interface DialogConvert {

    DialogConvert INSTANCT = Mappers.getMapper(DialogConvert.class);

    DialogDto convertDto(DialogDO bean);

}
