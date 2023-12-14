package com.iecube.pptxsplit.model.pptxsplit.entity;

import lombok.Data;

@Data
public class Pdf {
    Integer id;
    Integer pptId;
    String filename;
    String originFilename;
    String fileType;
}
