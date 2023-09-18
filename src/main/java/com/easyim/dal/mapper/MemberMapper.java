package com.easyim.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyim.dal.dataobject.MemberDO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 群组成员 Mapper
 *
 * @author 单程车票
 */
@Mapper
@Repository
public interface MemberMapper extends BaseMapper<MemberDO> {
}
