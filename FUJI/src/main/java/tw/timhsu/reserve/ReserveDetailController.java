package tw.timhsu.reserve;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import tw.timhsu.reserve.ReserveDetailDao;
import tw.timhsu.reserve.ReserveDetail;
//import tw.timhsu.reserve.Test;

@Controller
@RequestMapping("/reserve")
public class ReserveDetailController {
	
	@Autowired
	private ReserveDetailDao reservedetaildao;
	
	@Autowired
	private ReserveService reserviceservice;
	
	@GetMapping("/reserve")
	public String reservePage() {
		return "/reserve";
	}
	@RequestMapping(value="/reserveform")    
    public String showform(Model m){    
        m.addAttribute("command", new ReserveDetail());  
        return "/reserveform";   
    }    

    @PostMapping("save")  
    public String save(Reserve reserve){    
    	reserviceservice.update(reserve);
    	
        return "redirect:/viewreserve";    
    }   
    
    @ResponseBody
    @CrossOrigin
    @PostMapping(value="/saveReserve", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)  // 好的嗎?  
    public String saveReserve(@RequestBody ReserveDetail reservedetail){    
    	reservedetaildao.save(reservedetail);    
        return "ok";    
    }
    
    @RequestMapping("/viewreserve")    
    public String viewemp(Model m){    
        List<ReserveDetail> list=reservedetaildao.getReserves();    
        m.addAttribute("list",list);  
        return "/viewreserve";    
    }
    
    @ResponseBody
    @CrossOrigin
    @RequestMapping(value = "/getReserve", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")    
    public String getReserve(Model m){    
        List<ReserveDetail> list=reservedetaildao.getReserves(); 
        String json = new Gson().toJson(list);
        return json;    
    }
    @ResponseBody
    @CrossOrigin
    @RequestMapping(value = "/getReserve/{month}/{day}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")    
    public String getReserveInDay(@PathVariable int month,@PathVariable int day){    
        List<ReserveDetail> list=reservedetaildao.getReservesInDay(month,day); 
        String json = new Gson().toJson(list);
        return json;    
    } 
        
    @RequestMapping(value="/editreserve/{reserveid}")    
    public String edit(@PathVariable int reserveid, Model m){    
    	ReserveDetail reservedetail=reservedetaildao.getReserveDetailByReserveId(reserveid);    
        m.addAttribute("command",reservedetail);  
        return "/reserveeditform";    
    }  
    
    
    
      
    @RequestMapping(value="/editsave",method = RequestMethod.POST)    
    public String editsave(@ModelAttribute("reservedetail") ReserveDetail reservedetail){    
    	reservedetaildao.update(reservedetail);   
    	System.out.print(reservedetail.getReservename());
    	
        return "redirect:/viewreserve";    
    }    
        
    @RequestMapping(value="/deletereserve/{reserveid}",method = RequestMethod.GET)    
    public String delete(@PathVariable int reserveid){    
    	reservedetaildao.delete(reserveid);    
        return "redirect:/viewreserve";    
    }

}
