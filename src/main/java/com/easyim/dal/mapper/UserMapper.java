package com.easyim.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyim.dal.dataobject.UserDO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户 Mapper
 *
 * @author 单程车票
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserDO> {
}
