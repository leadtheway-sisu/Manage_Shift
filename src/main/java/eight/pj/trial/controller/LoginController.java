package eight.pj.trial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class LoginController {
	
	@GetMapping("/login")
	public String login(){
		return "/login";
	}
	
    @GetMapping("/") 
    public String loginCheck(){
        return "/login";  
    }
	
	@GetMapping("/index")
	public String index() {
		return "/index";
	}
	
	@GetMapping("/user")
	public String user(){
		return "/user";
	}

	@GetMapping("/admin")
	public String admin(){
		return "/admin";
	}
	
	@GetMapping("/error")
	public String error(){
		return "/error";
	}
	
	@PostMapping("/user")
	public String throughUser(){
		return "user";
	}
} 
