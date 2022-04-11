package tw.timhsu.products;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import tw.timhsu.products.Category;


@Service
public class ProductsService {
	
	@Autowired
	private ProductsRepository pRep;
	
	public void  saveProductToDB(MultipartFile file,String name,int price, Category category){
			
	
		Products p = new Products();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename()); //清理副檔名
		if(fileName.contains("..")) {
		
			System.out.println("not a a valid file");}
		
		try {
			p.setProductimage(Base64.getEncoder().encodeToString(file.getBytes())); //用Base64去編碼
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		
        p.setProductname(name);
        p.setProductprice(price);
        p.setCategory(category);
        
        pRep.save(p);
	}
	
	public List<Products> getAllProduct(){
		return pRep.findAll();
		}
	
	public Products findById(int id){
		return pRep.findById(id).get();
	}
	
	public Products update(Products products) {
		
		return pRep.save(products);
	}
	
	
    public void deleteProductById(int id) {
    
    	pRep.deleteById(id); 
    	}
   
    
    public Page<Products> listAll(int pageNum,String keyword) {
		int pageSize = 5;
		
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
				
		if(keyword!=null) {
			return pRep.findByKeyword(keyword, pageable);
		}
		
		return pRep.findAll(pageable);}
    public void changeProductimage(int id,MultipartFile file) {
    
    	Products p = new Products();
    	p = pRep.findById(id).get();
    	String fileName = StringUtils.cleanPath(file.getOriginalFilename()); //清理副檔名
		if(fileName.contains("..")) {
		
			System.out.println("not a a valid file");}
		
		try {
			p.setProductimage(Base64.getEncoder().encodeToString(file.getBytes())); //用Base64去編碼
		}catch (IOException e) {
			e.printStackTrace();
		}
		pRep.save(p);
}

	public List<Products> findByCategory(int categoryId) {
		return pRepository.findByCategory_categoryId(categoryId);
	}
}
	
    
    
