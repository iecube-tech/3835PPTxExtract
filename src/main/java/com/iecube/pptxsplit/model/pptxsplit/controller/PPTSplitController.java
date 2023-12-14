package com.iecube.pptxsplit.model.pptxsplit.controller;

import com.iecube.pptxsplit.basecontroller.BaseController;
import com.iecube.pptxsplit.model.pptxsplit.entity.Pdf;
import com.iecube.pptxsplit.model.pptxsplit.entity.Ppt;
import com.iecube.pptxsplit.model.pptxsplit.service.PPTSplitService;
import com.iecube.pptxsplit.model.resource.entity.Resource;
import com.iecube.pptxsplit.util.DownloadUtil;
import com.iecube.pptxsplit.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ppt")
public class PPTSplitController extends BaseController {
    @Autowired
    private PPTSplitService pptSplitService;

    @Value("${resource-location}/file/")
    private String filepath;

    @PostMapping("/ppt")
    public JsonResult<Ppt> uploadPPT(MultipartFile file, Integer courseId) throws IOException {
        Ppt ppt = pptSplitService.uploadPPT(file, courseId);
        return new JsonResult<>(OK, ppt);
    }

    @PostMapping("/pdf")
    public JsonResult<Pdf> uploadPDF(MultipartFile file, Integer pptId) throws IOException{
        Pdf pdf = pptSplitService.uploadPDF(file, pptId);
        return new JsonResult<>(OK, pdf);
    }

    @PostMapping("/extract")
    public JsonResult<List> extract(Integer pdfId) throws Exception {
        List<Resource> resourceList = pptSplitService.splitPPT(pdfId);
        return new JsonResult<>(OK,resourceList);
    }

    @GetMapping("/ppts/{courseId}")
    public JsonResult<List> coursePPTs(@PathVariable Integer courseId){
        List<Ppt> pptList = pptSplitService.coursePPTs(courseId);
        return new JsonResult<>(OK, pptList);
    }

    @GetMapping("/load/id/{pptId}")
    public void getPPT(@PathVariable Integer pptId, HttpServletResponse response){
        Ppt ppt =pptSplitService.getById(pptId);
        DownloadUtil.httpDownload(new File(filepath+ppt.getFilename()),ppt.getOriginFilename(),response);
    }
}
