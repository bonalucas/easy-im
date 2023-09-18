package com.easyim.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyim.dal.dataobject.FriendDO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 好友 Mapper
 *
 * @author 单程车票
 */
@Mapper
@Repository
public interface FriendMapper extends BaseMapper<FriendDO> {
}
