package com.iecube.pptxsplit.model.resource.service.Impl;

import com.iecube.pptxsplit.baseservice.ex.FileEmptyException;
import com.iecube.pptxsplit.baseservice.ex.InsertException;
import com.iecube.pptxsplit.model.resource.entity.Resource;
import com.iecube.pptxsplit.model.resource.mapper.ResourceMapper;
import com.iecube.pptxsplit.model.resource.service.ResourceService;
import com.iecube.pptxsplit.model.resource.vo.ResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<Resource> getResourceListByPPT(Integer pptId) {
        List<Resource> resourceList = resourceMapper.getResourceListByPPT(pptId);
        return resourceList;
    }

    @Override
    public ResourceVo getResourceVoById(Integer resourceId) {
        ResourceVo resourceVo = resourceMapper.getResourceVoById(resourceId);
        if(resourceVo == null){
            throw new FileEmptyException("不存在该文件");
        }
        return resourceVo;
    }

    @Override
    public ResourceVo getResourceVoByFilename(String filename) {
        ResourceVo resourceVo =resourceMapper.getResourceVoByFilename(filename);
        if(resourceVo == null){
            throw new FileEmptyException("不存在该文件");
        }
        return resourceVo;
    }
}
