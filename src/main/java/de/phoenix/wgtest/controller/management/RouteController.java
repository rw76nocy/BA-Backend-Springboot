package de.phoenix.wgtest.controller.management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouteController {
    /*
    * Add every frontend route here to forward to static react index.html
    */
    @RequestMapping(value = {
            "/",
            "/login",
            "/register",
            "/home",
            "/living_group",
            "/profile",
            "/appointment",
            "/record",
            "/options",
            "/employees",
            "/accounts",
            "/children",
            "/child/**",
            "/create"
    })
    public String index() {
        return "forward://index.html";
    }
}