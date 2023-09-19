package com.easyim.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyim.dal.dataobject.FriendDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 好友 Mapper
 *
 * @author 单程车票
 */
@Mapper
public interface FriendMapper extends BaseMapper<FriendDO> {
}
