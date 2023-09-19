package com.easyim.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyim.dal.dataobject.DialogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 对话 Mapper
 *
 * @author 单程车票
 */
@Mapper
public interface DialogMapper extends BaseMapper<DialogDO> {
}
