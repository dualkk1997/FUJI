package tw.timhsu.products;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {
	
	@Autowired
	private CategoryRepository cRep;
	
	public Category create(Category c) {
		return cRep.save(c);
	}
	
	public List<Category> findAll() {
		return cRep.findAll();
	}
	
	public Optional<Category> findById(int id){
		return cRep.findById(id);
	}
	
	public Category update(Category cate) {
		return cRep.save(cate);
	}
	
	public void deleteCategory(int id) {
		cRep.deleteById(id);
	}


}
