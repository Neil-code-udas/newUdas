package com.boshz.udas.secretPersonnel.service;

import com.boshz.udas.secretPersonnel.entity.SecretPersonnel;
import com.boshz.udas.secretPersonnel.excel.SecretPersonnelExcel;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 涉密人员管理表 业务接口
 */
public interface SecretPersonnelService {

    boolean insert(SecretPersonnel record);

    boolean deleteById(Long id);

    boolean update(SecretPersonnel record);

    SecretPersonnel getById(Long id);

    /**
     * 分页查询：QueryEntity封装条件+pageNum+pageSize
     */
    PageVo getListByPage(QueryEntity<SecretPersonnel> queryEntity);

    List<SecretPersonnel> getByCondition(SecretPersonnel param);

    void exportExcel(SecretPersonnel query, HttpServletResponse response) throws IOException;

    Integer importExcel(MultipartFile file);
}
