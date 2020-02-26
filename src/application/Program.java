package application;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;
import model.model.dao.DaoFactory;
import model.model.dao.SellerDao;

public class Program {
	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
//		System.out.println("==== Test 1:  seller findById ====");
//		Seller seller = sellerDao.findById(3);
//		System.out.println(seller);
	
		System.out.println("\n==== Test 2:  seller findDepartment ====");
		Department department = new Department(2, null);
		List<Seller> list = sellerDao.fildByDepartment(department);
		for(Seller obj : list) {
			System.out.println(obj);
		}
		
//		System.out.println("\n==== Test 3:  seller findAll ====");
//		List<Seller> sellerAll = sellerDao.findAll();
//		for(Seller s : sellerAll) {
//			System.out.println(s);
//		}
		
//		System.out.println("\n==== Test 4:  seller Insert ====");
//		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.00, department);
//		sellerDao.insert(newSeller);
//		System.out.println("Inserted! new id = "+ newSeller.getId());
		
//		System.out.println("\n==== Test 5:  seller Update ====");
//		Seller sellerUpdate = sellerDao.findById(1);
//		sellerUpdate.setName("Martha Waina");
//		sellerDao.update(sellerUpdate);
//		System.out.println("Update completed");
		
//		System.out.println("\n==== Test 6:  seller Delete ====");
//		sellerDao.deleteById(8);;
//		System.out.println("Delete completed");
		
		
	}

}