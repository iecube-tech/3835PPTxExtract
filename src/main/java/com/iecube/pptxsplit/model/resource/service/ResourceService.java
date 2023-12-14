package com.iecube.pptxsplit.model.resource.service;

import com.iecube.pptxsplit.model.resource.entity.Resource;
import com.iecube.pptxsplit.model.resource.vo.ResourceVo;

import java.util.List;

public interface ResourceService {
    List<Resource> getResourceListByPPT(Integer pptId);

    ResourceVo getResourceVoById(Integer resourceId);

    ResourceVo getResourceVoByFilename(String filename);

}
