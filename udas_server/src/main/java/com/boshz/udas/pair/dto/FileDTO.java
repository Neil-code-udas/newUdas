package com.boshz.udas.pair.dto;

import lombok.Data;

@Data
public class FileDTO {
    /** 文件名称 */
    private String fileName;
    /** 文件访问地址 */
    private String fileUrl;
}