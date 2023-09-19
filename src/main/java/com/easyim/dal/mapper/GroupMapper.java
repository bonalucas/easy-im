package com.easyim.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyim.dal.dataobject.GroupDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 群组 Mapper
 *
 * @author 单程车票
 */
@Mapper
public interface GroupMapper extends BaseMapper<GroupDO> {
}
