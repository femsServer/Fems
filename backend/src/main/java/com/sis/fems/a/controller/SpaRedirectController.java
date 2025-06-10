package com.sis.fems.a.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//SPA 라우팅 처리용 컨트롤러
@Controller
public class SpaRedirectController {
    @RequestMapping(value = "/{[path:[^\\.]*}")
    public String redirect() {
        return "forward:/index.html";
    }
}
