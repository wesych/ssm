package org.wesc.ssm.dao.generator;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

/**
 * 所有Mapper接口的父接口. 
 * 
 * @param <ENTITY> 实体类类型
 * @param <ID>     ID字段类型
 */
public interface ManagementMapper<ENTITY extends BaseEntity<ID>, ID> {

    /**
     * 根据查询条件分页查询.
     * @param map 查询条件
     * @param rb  分页信息
     * @return    查询结果
     */
    List<ENTITY> selectByMap(Map<String, Object> map, RowBounds rb);

    /**
     * 根据查询条件查询符合条件的记录数.
     * @param map 查询条件
     * @return 符合条件的记录数
     */
    Integer countByMap(Map<String, Object> map);
    
    /**
     * 根据ID查询唯一记录.
     * @param id ID
     * @return 查询结果
     */
    ENTITY selectById(ID id);
    
    /**
     * 插入记录.
     * @param obj 实体类
     * @return 受影响记录数
     */
    Integer insertSelective(ENTITY obj);

    /**
     * 根据ID列表删除多条记录.
     * @param ids ID列表
     * @return 受影响记录数
     */
    Integer deleteByIds(List<ID> ids);

    /**
     * 根据ID更新.
     * @param obj 实体类(包含ID字段)
     * @return 受影响记录数
     */
    Integer updateSelectiveById(ENTITY obj);
}
