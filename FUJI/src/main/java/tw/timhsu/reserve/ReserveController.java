package tw.timhsu.reserve;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tw.timhsu.products.Products;


@Controller
public class ReserveController {
	
	@Autowired
	private ReserveService rService;
	
	
	@RequestMapping("/reservelist/page/{pageNum}")
	public String viewPage(Model model,
			@PathVariable(name = "pageNum") int pageNum,
			@Param("keyword") String keyword) {
		
		Page<Reserve> page = rService.listAll(pageNum, keyword);
		
		List<Reserve> reserve = page.getContent();

		model.addAttribute("currentPage", pageNum);    
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("keyword", keyword);
		model.addAttribute("reservelist", reserve);
	

		
		return "homepageReserve";
	}
    
    @RequestMapping("/reservelist/page/1")
	public String viewHomePage(Model model ,String keyword) {
    	
		return viewPage(model, 1, keyword);
	}
    
    @RequestMapping("/reservelist/delete/{id}")
	public String deleteEmployee(@PathVariable(name="id")int id) {
		rService.deleteReserveById(id);
		return"redirect:/reservelist/page/1";
	}
    
    @PostMapping("/reservelist/update")
   	public String update(Reserve reserve) {
   		
   		rService.update(reserve);
   		
   		return "redirect:/reservelist/page/1";
   	}
}
