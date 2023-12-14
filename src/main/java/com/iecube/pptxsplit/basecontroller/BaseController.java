package com.iecube.pptxsplit.basecontroller;

import com.iecube.pptxsplit.util.JsonResult;
import com.iecube.pptxsplit.util.ex.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {
    public static final int OK=200;
    @ExceptionHandler
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof SystemException) {
            result.setState(404);
            result.setMessage("文件未找到");
        }
        return result;
    }
}
