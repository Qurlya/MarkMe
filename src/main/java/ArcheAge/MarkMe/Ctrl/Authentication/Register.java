package ArcheAge.MarkMe.Ctrl.Authentication;

import ArcheAge.MarkMe.Database.Pg;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Register {
    // Страница авторизации
    @GetMapping("/register")
    public String loginPage() {
        return "Authentication/registration";
    }


    // Обработка данных авторизации
    @PostMapping("/register")
    public String login(
            @RequestParam String nickname,
            @RequestParam String vkontakte,
            @RequestParam String way,
            @RequestParam String pin,
            HttpSession session,
            Model model
    ) {
        // Сохраняем имя пользователя в сессии
        System.out.println(Pg.checkOnceMembers(nickname));
        if (!Pg.checkOnceMembers(nickname)){
            Pg.addMembers(nickname, vkontakte, way, pin);
            session.setAttribute("nickname", nickname);
            return "redirect:/";
        } else {
            model.addAttribute("errorOnceMessage", "Такой никнейм уже зарегистрирован");
            return "Authentication/registration";
        }
    }
}
