package com.easyim.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyim.dal.dataobject.MemberDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 群组成员 Mapper
 *
 * @author 单程车票
 */
@Mapper
public interface MemberMapper extends BaseMapper<MemberDO> {
}
