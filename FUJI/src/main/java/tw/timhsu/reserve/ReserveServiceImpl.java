package tw.timhsu.reserve;

import org.springframework.beans.factory.annotation.Autowired;

import tw.timhsu.reserve.ReserveDao;
import tw.timhsu.reserve.ReserveDetail;

public class ReserveServiceImpl {
	
	@Autowired
	public ReserveDao reserveDao;
	
	public int reserve(ReserveDetail reservedetail) {
	    return reserveDao.reserve(reservedetail);
	}

}
