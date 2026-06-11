package com.boshz.udas.secretPersonnel.mapper;

import com.boshz.udas.secretPersonnel.entity.SecretPersonnel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SecretPersonnelMapper {

    SecretPersonnel selectById(Long id);

    int deleteById(Long id);

    int insert(SecretPersonnel record);

    int update(SecretPersonnel record);

    List<SecretPersonnel> selectByCondition(@Param("param") SecretPersonnel param);

    /**
     * 分页查询
     */
    List<SecretPersonnel> queryAllByLimit(@Param("query") SecretPersonnel query,
                                          @Param("offset") Integer offset,
                                          @Param("pageSize") Integer pageSize);

    Long selectCount(@Param("query") SecretPersonnel query);

    /**
     * 批量新增
     */
    int insertBatch(@Param("entities") List<SecretPersonnel> entities);
}
