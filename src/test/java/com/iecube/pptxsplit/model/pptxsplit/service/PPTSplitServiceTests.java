package com.iecube.pptxsplit.model.pptxsplit.service;

import com.iecube.pptxsplit.model.resource.entity.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PPTSplitServiceTests {
    @Autowired
    private PPTSplitService pptSplitService;

    @Test
    public void pptSplit() throws Exception {
       List<Resource> all = pptSplitService.splitPPT(1);
        System.out.println(all);
    }
}
