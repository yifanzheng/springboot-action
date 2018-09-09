package com.example.exception.ajax;

import com.example.exception.pojo.JsonResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 控制器
 *
 * @author kevin
 * @date 2018-09-07 21:33
 **/
@Controller
@RequestMapping("ajax")
public class AjaxErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public JsonResponse error(){
        String content=null;
        if(content.equals(" ")){
            return JsonResponse.ok(content);
        }
        return JsonResponse.ok();
    }

    @RequestMapping("/ajaxError")
    public String ajaxError(){
        return "thymeleaf/ajaxerror";
    }

}
