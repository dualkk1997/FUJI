package tw.timhsu.usercontroller;

import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tw.timhsu.users.Users;
import tw.timhsu.users.UsersService;



@Controller
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final UsersService userService;
   
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    	    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    	public static boolean validateemail(String emailStr) {
    	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
    	        return matcher.find();
    	}
    	  public static final Pattern phone_v = 
    	    	    Pattern.compile("(\\+886|0)[0-9]{9}", Pattern.CASE_INSENSITIVE);
    	  public static boolean validatephone(String phone) {
  	        Matcher matcher = phone_v.matcher(phone);
  	        return matcher.find();
  	}
    @Autowired
    public AccountController(UsersService userService) {
        this.userService = userService;
    }

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
 
    @RequestMapping("/login")
    public String login(@ModelAttribute Users user, Model model){
    	
        return "home";
   }
 
    @PostMapping("/login")
    public String postlogin(@ModelAttribute Users user, Model model){
 
    	return "home";
    }
    
    @GetMapping("/")
    public String home(RedirectAttributes ra,Principal p){
    	if(p != null) {ra.addFlashAttribute("g","恭喜GOOGLE用戶登入成功");}
    	return "redirect:/home";
    }
   


    
 @GetMapping("/register")
  public String register(@ModelAttribute Users user, Model model){
      model.addAttribute("user",user);
      return "home";
  }

 
    
    @PostMapping("/register")
    //@RequestMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public String save(@Validated @ModelAttribute Users user, BindingResult bindingResult, RedirectAttributes ra ,Model model,@RequestParam("phone") String phone,@RequestParam("email") String email,@RequestParam("name") String name,@RequestParam("username") String username,@RequestParam("password") String password){
    	model.addAttribute("user",user);
        if(userService.userExists(user.getUsername())){
            bindingResult.addError(new FieldError("user","username","抱歉，此帳號名稱已有人使用"));
        }
        if(username.isEmpty()){
            bindingResult.addError(new FieldError("user","username","請輸入帳號"));
        }
        if(name.isEmpty()){
            bindingResult.addError(new FieldError("user","name","請輸入姓名"));
        }
        if(password.isEmpty()){
            bindingResult.addError(new FieldError("user","password","請輸入姓名"));
        }
        if(password.length()<6){
            bindingResult.addError(new FieldError("user","password","請輸入六位字數以上的密碼"));
        }
        
        if(phone.isEmpty()){
            bindingResult.addError(new FieldError("user","phone","請輸入手機號碼"));
        }
        if(email.isEmpty()){
            bindingResult.addError(new FieldError("user","email","請輸入信箱"));
        }
        if(!validateemail(email)){
            bindingResult.addError(new FieldError("user","email","請輸入正確的信箱格式"));
        }
        if(!validatephone(phone)){
            bindingResult.addError(new FieldError("user","phone","請輸入正確的電話格式"));
        }
       
        if(bindingResult.hasErrors()){
            return "home";
        }

        ra.addFlashAttribute("p","恭喜您!註冊成功富鮨會員!手刀登入使用我們的服務吧");

        String encode = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encode);
        user.setEmail(user.getEmail());
        user.setPhone(user.getPhone());
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
	@RequestMapping("/profile/gousersupdatepassword")
	public ModelAndView updatepassword(ModelAndView mav) {
		mav.setViewName("changepassword");
		return mav;
	}


    
    @PostMapping(path = "/profile/usersupdatepassword")
	public ModelAndView updateuserspassword(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("newpassword") String newpwd, @RequestParam("newpassword2") String newpwd2, Model m,ModelAndView mav,RedirectAttributes ra) {
    	if(username.isEmpty()) 
		{
			mav.addObject("error3","請輸入您的帳號");
			mav.setViewName("changepassword");
			return mav;
		}
    	if(newpwd.isEmpty()||newpwd2.isEmpty()) 
		{
			mav.addObject("error4","請輸入您的新密碼");
			mav.setViewName("changepassword");
			return mav;
		}
    	if(newpwd.length() < 6) 
		{
			mav.addObject("error6","密碼至少六位數");
			mav.setViewName("changepassword");
			return mav;
		}
		Users u = userService.findByusername(username);
	
		boolean result = new BCryptPasswordEncoder().matches(password, u.getPassword());
		
		if(result) {
			
			if (newpwd.equals(newpwd2)) {
				
				
			String bcnewpwd = new BCryptPasswordEncoder().encode(newpwd);
				
				userService.updatePassword(username, bcnewpwd);
				ra.addFlashAttribute("yes", "修改密碼成功");
				mav.setViewName("redirect:/home");
				return mav;
				
			}else if(!newpwd2.equals(newpwd)) {
				mav.addObject("error","新密碼不一致，請再確認");
				mav.setViewName("changepassword");
				return mav;
					}
			else {
				mav.addObject("error2","帳號或密碼不正確");
				mav.setViewName("changepassword");
				return mav;
			}
			
		
	}
		mav.addObject("error3","密碼不正確");
		mav.setViewName("changepassword");
		return mav;
		
    }
}





