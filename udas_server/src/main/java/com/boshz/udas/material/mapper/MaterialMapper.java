package com.boshz.udas.material.mapper;

import com.boshz.udas.material.entity.Material;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MaterialMapper {

    Material selectById(Long id);

    int deleteById(Long id);

    int insert(Material record);

    int update(Material record);

    List<Material> selectByCondition(@Param("query") Material param);

    /**
     * 分页查询
     */
    List<Material> queryAllByLimit(@Param("query") Material query,
                                   @Param("offset") Integer offset,
                                   @Param("pageSize") Integer pageSize);

    Long selectCount(@Param("query") Material query);

    /**
     * 批量新增
     */
    int insertBatch(@Param("entities") List<Material> entities);

    List<Material> selectList(@Param("materialName") String materialName);


    Material selectByName(@Param("materialName") String materialName);


    int updateById(Material material);



    int updateStock(@Param("id") Long id, @Param("newStock") BigDecimal newStock);
}
