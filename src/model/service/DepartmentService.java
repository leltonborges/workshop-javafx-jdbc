package model.service;

import java.util.List;

import model.entities.Department;
import model.model.dao.DaoFactory;
import model.model.dao.DepartmentDao;

public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepartmetDao();
	
	public List<Department> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Department d) {
		if(d.getId() == null) {
			dao.insert(d);
		}else {
			dao.update(d);
		}
	}

}
