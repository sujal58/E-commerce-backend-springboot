package com.sujal.Ecommerce.Controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {

    @GetMapping("/health-check")
    public String home(){
        log.info("Hello from spring-boot server.");
        return "Hello from Spring boot server!!!";
    }
}
