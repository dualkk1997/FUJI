package tw.timhsu.usercontroller;

import java.security.Principal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tw.timhsu.users.Users;
import tw.timhsu.users.UsersService;



@Controller
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    private final UsersService userService;
   
    
    @Autowired
    public AccountController(UsersService userService) {
        this.userService = userService;
    }
//    //google預設導回來的路徑
//    @GetMapping("/")
//    public String gotoindex(@ModelAttribute Users user, Model model,Principal principal){
//        model.addAttribute("user",user);
//        return "/home";
//    }
    @GetMapping("/home")
    public String index(@ModelAttribute Users user, Model model,Principal principal){
        model.addAttribute("user",user);
        return "/home";
    }

//    @GetMapping("/home")
//    public String test2(){
//   
//        return "home";
//    }
    
//    @RequestMapping("/login")
//    public String login() {
   @GetMapping("/login")
    public String gotologin(@ModelAttribute Users user, Model model){
        model.addAttribute("user",user);
        return "redirect:/home";
   }
 
    @PostMapping("/login")
    public String login(){
    	//model.addAttribute("user",user);
    		return "redirect:/home";    
    }


    
 @GetMapping("/register")
  public String register(@ModelAttribute Users user, Model model){
      model.addAttribute("user",user);
      return "home";
  }

    
    
    @PostMapping("/register")
    //@RequestMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute Users user, BindingResult bindingResult, RedirectAttributes ra ,Model model){
    	model.addAttribute("user",user);
        if(userService.userExists(user.getUsername())){
            bindingResult.addError(new FieldError("user","username","Account already in use"));
        }

        if(bindingResult.hasErrors()){
            return "home";
        }

        ra.addFlashAttribute("message","Success!your registration is compelete");

        String encode = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encode);
        userService.createUser(user);
        return "redirect:/home";
        }
    
    
    private Users getPrincipal() {
    	Users user = null;
    	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Users) {
    		user =(Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	}
    	return user;
    }
    
    
    }


