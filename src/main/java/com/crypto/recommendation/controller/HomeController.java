package com.crypto.recommendation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for Home Page.
 *
 * @author lioannidis
 * @version 0.1
 */
@Controller
public class HomeController {

    /**
     * Maps the home page.
     * @return the index.html
     */
    @GetMapping("/")
    public String home() {
        return "index.html";
    }
}
