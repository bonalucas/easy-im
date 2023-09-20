package com.easyim.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyim.dal.dataobject.FileDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件 Mapper
 *
 * @author 单程车票
 */
@Mapper
public interface FileMapper extends BaseMapper<FileDO> {
}
