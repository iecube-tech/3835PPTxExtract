package com.iecube.pptxsplit.model.pptxsplit.mapper;

import com.iecube.pptxsplit.model.pptxsplit.dto.PPTSplitDto;
import com.iecube.pptxsplit.model.pptxsplit.entity.Pdf;
import com.iecube.pptxsplit.model.pptxsplit.entity.Ppt;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PPTMapper {
    Ppt getById(Integer pptId);
    Integer insertPPT(Ppt ppt);

    Integer insertPDF(Pdf pdf);

    PPTSplitDto getPPTSplitDtoByPdfId(Integer pdfId);

    List<Ppt> coursePPTs(Integer courseId);
}
