package com.github.Viktor2308.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartPageController {

    @GetMapping("/start-page")
    public String index() {
        return "filesupload";
    }
}
