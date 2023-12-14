package com.iecube.pptxsplit.model.resource.controller;

import com.iecube.pptxsplit.basecontroller.BaseController;
import com.iecube.pptxsplit.model.resource.entity.Resource;
import com.iecube.pptxsplit.model.resource.service.ResourceService;
import com.iecube.pptxsplit.model.resource.vo.ResourceVo;
import com.iecube.pptxsplit.util.DownloadUtil;
import com.iecube.pptxsplit.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController {

    @Autowired
    private ResourceService resourceService;

    @Value("${resource-location}/extract/")
    private String extract;

    @GetMapping("/{pptId}")
    public JsonResult<List> resourceOfPPT(@PathVariable Integer pptId){
        List<Resource> resourceList = resourceService.getResourceListByPPT(pptId);
        return new JsonResult<>(OK,resourceList);
    }


    @GetMapping("/load/id/{resourceId}")
    public void getResource(@PathVariable Integer resourceId, HttpServletResponse response){
        ResourceVo resourceVo = resourceService.getResourceVoById(resourceId);
        String parentFile = extract+resourceVo.getCourseId();
        File file = new File(parentFile, resourceVo.getFilename());
        DownloadUtil.httpDownload(file,resourceVo.getOriginFilename(),response);
    }

    @GetMapping("/load/name/{filename}")
    public void getResourceByFilename(@PathVariable String filename, HttpServletResponse response){
        ResourceVo resourceVo = resourceService.getResourceVoByFilename(filename);
        String parentFile = extract+resourceVo.getCourseId();
        File file = new File(parentFile, resourceVo.getFilename());
        DownloadUtil.httpDownload(file,resourceVo.getOriginFilename(),response);
    }
}
