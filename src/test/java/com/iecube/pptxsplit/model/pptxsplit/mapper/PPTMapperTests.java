package com.iecube.pptxsplit.model.pptxsplit.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PPTMapperTests {

    @Autowired
    private PPTMapper pptMapper;

    @Test
    public void getPPTSplitDtoByPDFIdTest(){
        System.out.println(pptMapper.getPPTSplitDtoByPdfId(1));
    }
}
