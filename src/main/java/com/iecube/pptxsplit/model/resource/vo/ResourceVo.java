package com.iecube.pptxsplit.model.resource.vo;

import lombok.Data;

@Data
public class ResourceVo {
    Integer courseId;
    Integer pptId;
    String filename;
    String originFilename;
}
