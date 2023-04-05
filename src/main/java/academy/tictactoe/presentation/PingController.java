package academy.tictactoe.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PingController {
    public PingController() {}

    @RequestMapping("/*")
    public @ResponseBody String healthcheck() {
        return "I’m alive";
    }

    @RequestMapping("/ping")
    public @ResponseBody String ping() {
        return "Pong";
    }

    @RequestMapping("/greeting")
    public @ResponseBody String greeting(@RequestParam(name = "name", required = false, defaultValue = "Peter") String name) {
        return String.format("Hello, %s!", name);
    }

}
