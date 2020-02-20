package model.model.dao;

import model.connections.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmetDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}

}
