package com.iecube.pptxsplit.model.resource.entity;

import lombok.Data;

@Data
public class Resource {
    Integer id;
    Integer pptId;
    Integer page;
    String filename;
    String originFilename;
    String type;
    String fileType;
    Double pageWidth;
    Double PageHeight;
    Double x;
    Double y;
    Double width;
    Double height;
}
