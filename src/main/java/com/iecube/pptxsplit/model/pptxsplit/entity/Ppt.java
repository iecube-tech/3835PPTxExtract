package com.iecube.pptxsplit.model.pptxsplit.entity;

import lombok.Data;

@Data
public class Ppt {
    Integer id;
    Integer courseId;
    String filename;
    String originFilename;
    String fileType;
}
