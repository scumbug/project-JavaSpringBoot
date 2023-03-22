package sg.edu.ntu.m3project.m3project;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
class TestController {

    @GetMapping("/food")
    fun index(): String {
        return "THIS IS A TEST"
    }
}