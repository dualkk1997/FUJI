package tw.timhsu.products;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import tw.timhsu.products.Category;
import tw.timhsu.products.CategoryService;

@Controller
public class ProductsController {
	
	@Autowired
	private ProductsService pService;
	
	@Autowired
	private CategoryService cService;
	
	@GetMapping("/listProducts")
	public String showExampleView(Model model)
	{
		List<Products> products =pService.getAllProduct();
		List<Category> categoryList = cService.findAll();
		model.addAttribute("productlist", products);
		return "productlist";
	}
	
	
    @GetMapping("/listProducts/addproducts")
    public String showAddProduct(Model model){
    
    	model.addAttribute("categoryList", cService.findAll());
    	return "productscreate";
    }
    
    @PostMapping("/add")
    public String saveProduct(@RequestParam("productimage") MultipartFile file,
    		@RequestParam("productname") String name,
    		@RequestParam("productprice") int price,
    		@RequestParam("category") Category category,Model m) {
    	
    		
    	pService.saveProductToDB(file, name, price,category);
    	return "redirect:/listProducts/page/1";
    }
    
    @RequestMapping("/listProducts/edit/{id}")
	public String editProduct(@PathVariable(name="id") int id, Model m) {
		Products products = pService.findById(id);
		m.addAttribute("categoryList", cService.findAll());
		m.addAttribute("products",products);
		return "productseditform";
	}
    
    @RequestMapping("/listProducts/delete/{id}")
	public String delete(@PathVariable(name="id")int id) {
		pService.deleteProductById(id);
		return"redirect:/listProducts/page/1";
	}
   
    
    @PostMapping("/listProducts/update")
    public String update(Products products) {
    	
    	pService.update(products);
    	
    	return "redirect:/listProducts/page/1";
    }
    
    
    @RequestMapping("/listProducts/page/{pageNum}")
	public String viewPage(Model model,
			@PathVariable(name = "pageNum") int pageNum,
			@Param("keyword") String keyword) {
		
		Page<Products> page = pService.listAll(pageNum, keyword);
		
		List<Products> products = page.getContent();

		List<Category> categoryList = cService.findAll();
		model.addAttribute("currentPage", pageNum);    
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("keyword", keyword);
		model.addAttribute("productlist", products);
		model.addAttribute("categoryList", categoryList);

		
		return "homepageProduct";
	}
    
    @RequestMapping("/listProducts/page/1")
	public String viewHomePage(Model model ,String keyword) {
    	
		return viewPage(model, 1, keyword);
	}
    
    @PostMapping("/listProducts/changeImage")
    public String changePrice(@RequestParam("pid") int id ,
    		@RequestParam("productimage") MultipartFile file)
    {
    	pService.changeProductimage(id, file);
    	return "redirect:/listProducts/page/1";
    }

    
}
