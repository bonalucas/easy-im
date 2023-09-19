package com.easyim.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyim.dal.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 Mapper
 *
 * @author 单程车票
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
