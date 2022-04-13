package tw.timhsu.products;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tw.timhsu.products.Category;
import tw.timhsu.products.CategoryService;

@Controller
@RequestMapping("backend")
public class CategoryController {
	
	@Autowired
	private CategoryService cService;
	
	
	@GetMapping("/categorylist")
	public String categoryPage(Model m) {
		List<Category> categoryList = cService.findAll();
		m.addAttribute("categorylist", categoryList);
		return "categorylist";
	}
	
	@PostMapping("/createcategory")
	public String addCategory(Category c) {
		cService.create(c);
	
		return "redirect:/backend/categorylist";
	}

	@PostMapping("/categorylist/update")
	public String update(Category cate) {
		
		cService.update(cate);
		return "redirect:/backend/categorylist";
	}
	
	@RequestMapping("/categorylist/delete/{id}")
	public String delete(@PathVariable(name="id")int id,Model m) {
			
			cService.deleteCategory(id);
			
		
		return "redirect:/backend/categorylist";
	}


}
