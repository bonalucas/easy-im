package com.easyim.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyim.dal.dataobject.GroupDO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 群组 Mapper
 *
 * @author 单程车票
 */
@Mapper
@Repository
public interface GroupMapper extends BaseMapper<GroupDO> {
}
