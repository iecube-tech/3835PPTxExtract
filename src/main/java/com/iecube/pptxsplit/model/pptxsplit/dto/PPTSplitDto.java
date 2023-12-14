package com.iecube.pptxsplit.model.pptxsplit.dto;

import lombok.Data;

@Data
public class PPTSplitDto {
    Integer pdfId;
    Integer pptId;
    Integer courseId;
    String pdfFilename;
    String pptFilename;
}
