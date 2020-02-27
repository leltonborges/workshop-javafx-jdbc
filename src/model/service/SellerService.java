package model.service;

import java.util.List;

import model.entities.Seller;
import model.model.dao.DaoFactory;
import model.model.dao.SellerDao;

public class SellerService {
	
	private SellerDao dao = DaoFactory.createSellerDao();
	
	public List<Seller> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Seller d) {
		if(d.getId() == null) {
			dao.insert(d);
		}else {
			dao.update(d);
		}
	}

	public void remove(Seller obj) {
		dao.deleteById(obj.getId());
	}
}
