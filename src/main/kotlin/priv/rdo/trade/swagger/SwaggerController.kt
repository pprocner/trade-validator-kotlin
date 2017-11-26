package priv.rdo.trade.swagger

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class SwaggerController {
    @RequestMapping(value = "/documentation")
    fun index(): String {
        return "redirect:swagger-ui.html"
    }
}
