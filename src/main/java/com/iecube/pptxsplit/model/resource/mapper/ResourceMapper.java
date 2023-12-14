package com.iecube.pptxsplit.model.resource.mapper;

import com.iecube.pptxsplit.model.resource.entity.Resource;
import com.iecube.pptxsplit.model.resource.vo.ResourceVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResourceMapper {
    Integer insert(Resource resource);
    List<Resource> getResourceListByPPT(Integer pptId);

    ResourceVo getResourceVoById(Integer resourceId);

    ResourceVo getResourceVoByFilename(String filename);
}
