package org.wesc.ssm.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.wesc.ssm.dao.entity.UcUser;
import org.wesc.ssm.dao.entity.UcUserExample;

public interface UcUserMapper {
    long countByExample(UcUserExample example);

    int deleteByExample(UcUserExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(UcUser record);

    int insertSelective(UcUser record);

    List<UcUser> selectByExampleWithRowbounds(UcUserExample example, RowBounds rowBounds);

    List<UcUser> selectByExample(UcUserExample example);

    UcUser selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") UcUser record, @Param("example") UcUserExample example);

    int updateByExample(@Param("record") UcUser record, @Param("example") UcUserExample example);

    int updateByPrimaryKeySelective(UcUser record);

    int updateByPrimaryKey(UcUser record);
}