package eight.pj.trial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import eight.pj.trial.entity.LoginUser;
import eight.pj.trial.entity.LoginUserDto;
import eight.pj.trial.service.LoginUserService;

@Controller 
public class RegisterController { //新規ユーザー登録

    @Autowired
    private LoginUserService userService;

    @GetMapping("/register")   // "/register"というURLに対するGETリクエストを処理                      
    public ModelAndView registerForm() {
        ModelAndView mav = new ModelAndView();  // 新しいUserDtoオブジェクトを"ユーザー"という名前で追加     
        mav.addObject("user", new LoginUserDto());        
        mav.setViewName("register");                
        return mav;                                 
    }

    @PostMapping("/register")    
    public String register(@ModelAttribute LoginUserDto user) {
        LoginUser existing = userService.findByName(user.getName()); 
        if(existing != null){  
            return "register"; 
        }
        System.out.println(user.toString());
        userService.save(user); 
        return "login"; 
    }
}
