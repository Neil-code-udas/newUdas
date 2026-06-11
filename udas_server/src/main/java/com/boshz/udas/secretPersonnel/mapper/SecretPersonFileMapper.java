package com.boshz.udas.secretPersonnel.mapper;

import com.boshz.udas.secretPersonnel.entity.SecretPersonFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SecretPersonFileMapper {

    SecretPersonFile selectById(Long secretId);

    int deleteById(Long secretId);

    int insert(SecretPersonFile record);

    int update(SecretPersonFile record);

    List<SecretPersonFile> selectByCondition(@Param("param") SecretPersonFile param);

    /**
     * 分页查询
     */
    List<SecretPersonFile> queryAllByLimit(@Param("query") SecretPersonFile query,
                                           @Param("offset") Integer offset,
                                           @Param("pageSize") Integer pageSize);

    Long selectCount(@Param("query") SecretPersonFile query);

    /**
     * 批量新增
     */
    int insertBatch(@Param("entities") List<SecretPersonFile> entities);
}
