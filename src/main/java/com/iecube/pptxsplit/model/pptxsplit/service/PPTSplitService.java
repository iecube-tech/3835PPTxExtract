package com.iecube.pptxsplit.model.pptxsplit.service;

import com.iecube.pptxsplit.model.pptxsplit.entity.Pdf;
import com.iecube.pptxsplit.model.pptxsplit.entity.Ppt;
import com.iecube.pptxsplit.model.resource.entity.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PPTSplitService {
    Ppt getById(Integer pptId);

    Ppt uploadPPT(MultipartFile file, Integer courseId) throws IOException;

    Pdf uploadPDF(MultipartFile file, Integer pptId) throws IOException;

    List<Resource> splitPPT(Integer pdfId) throws Exception;

    List<Ppt> coursePPTs(Integer courseId);

}
