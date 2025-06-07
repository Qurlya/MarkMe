package ArcheAge.MarkMe.Ctrl.Main;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Work_space {
    // Страница авторизации
    @GetMapping("/work_space")
    public String work_space(HttpSession session, Model model) {
        String nickname = (String) session.getAttribute("nickname");
        model.addAttribute("nickname", nickname);
        return "Main/work_space";
    }
}
