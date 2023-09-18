package com.easyim.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyim.dal.dataobject.DialogDO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 对话 Mapper
 *
 * @author 单程车票
 */
@Mapper
@Repository
public interface DialogMapper extends BaseMapper<DialogDO> {
}
