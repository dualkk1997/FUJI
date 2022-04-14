package tw.timhsu.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("backend")
@SessionAttributes(names = { "totalPages", "totalElements" })
public class UsersController {

	@Autowired
	private UsersService uService;

	@RequestMapping("/userslist/edit/{id}")
	public String editProduct(@PathVariable(name = "id") int id, Model m) {
		Users users = uService.findById(id);

		m.addAttribute("users", users);
		return "productseditform";
	}

	@PostMapping("/userslist/update")
	public String update(Users users) {
		String encode = users.getPassword();
		users.setPassword(encode);

		uService.update(users);
		return "redirect:/backend/userslist/page/1";
	}

	@RequestMapping("/userslist/delete/{id}")
	public String delete(@PathVariable(name = "id") int id) {
		uService.deleteUsers(id);
		return "redirect:/backend/userslist/page/1";
	}

	// 查看客人
	@RequestMapping("/userslist/page/{pageNum}")
	public String viewPage(Model model, @PathVariable(name = "pageNum") int pageNum, @Param("keyword") String keyword) {

		String role = "user";
		String role2 = "user2";

		Page<Users> page = uService.listAll(pageNum, keyword, role, role2);

		List<Users> users = page.getContent();

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("keyword", keyword);

		model.addAttribute("users", users);

		return "userslist";
	}

	@RequestMapping("/userslist/page/1")
	public String viewHomePage(Model model, String keyword) {
		return viewPage(model, 1, keyword);
	}

	// 查看員工
	@RequestMapping("/employeelist/page/{pageNum}")
	public String viewEmployeePage(Model model, @PathVariable(name = "pageNum") int pageNum,
			@Param("keyword") String keyword) {

		String role = "admin";
		String role2 = "employee";

		Page<Users> page = uService.listAll(pageNum, keyword, role, role2);

		List<Users> users = page.getContent();

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("keyword", keyword);

		model.addAttribute("users", users);

		return "employeelist";
	}

	@RequestMapping("/employeelist/page/1")
	public String viewEmployeeHomePage(Model model, String keyword) {
		return viewEmployeePage(model, 1, keyword);
	}

	@PostMapping("/employeelist/update")
	public String updateEmployee(Users users) {
		String encode = users.getPassword();
		users.setPassword(encode);

		uService.update(users);
		return "redirect:/backend/employeelist/page/1";
	}

	@RequestMapping("/employeelist/delete/{id}")
	public String deleteEmployee(@PathVariable(name = "id") int id) {
		uService.deleteUsers(id);
		return "redirect:/backend/employeelist/page/1";
	}

	// 4/13

	@RequestMapping("/updatepassword")
	public ModelAndView updatepassword(ModelAndView mav) {
		mav.setViewName("homepageUpdatePassword");
		return mav;
	}

	@PostMapping(path = "/updatepassword1")
	public ModelAndView update(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("newpassword") String newpwd, @RequestParam("newpassword2") String newpwd2, Model m,ModelAndView mav) {
//		Map<String, String> errors = new HashMap<String, String>();
		
		Users u = uService.findByusername(username);
		boolean result = new BCryptPasswordEncoder().matches(password, u.getPassword());
		
		if(result) {
			
			if (newpwd.equals(newpwd2)) {
				
				String bcnewpwd = new BCryptPasswordEncoder().encode(newpwd);
				
				uService.updatePassword(username, bcnewpwd);
				mav.setViewName("redirect:/backend/employeelist/page/1");
				return mav;
				
			}else if(!newpwd2.equals(newpwd)) {
				mav.addObject("error","密碼不一致");
				mav.setViewName("homepageUpdatePassword");
				return mav;
					}
			}else {
			
				mav.addObject("error2","帳號或密碼不正確");
				mav.setViewName("homepageUpdatePassword");
				return mav;
			}
			mav.setViewName("homepageUpdatePassword");
			return mav;
			
		
	}
}
