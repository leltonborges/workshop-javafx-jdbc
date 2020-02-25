package application;

import model.entities.Department;
import model.model.dao.DaoFactory;
import model.model.dao.DepartmentDao;

public class Program2 {

	public static void main(String[] args) {
		DepartmentDao departmentDao = DaoFactory.createDepartmetDao();
		
		System.out.println("==== Test 1:  department  findById====");
		Department d = departmentDao.findById(1);
		System.out.println(d);
		
//		System.out.println("\n==== Test 2:  department  findAll====");
//		List<Department> list = departmentDao.findAll();
//		for(Department dep: list) {
//			System.out.println(dep);
//		}
		
//		System.out.println("\n==== Test 3:  department  update====");
//		Department newDepartment = new Department(null, "Lelton");
//		departmentDao.insert(newDepartment);
//		System.out.println("Inserted! new id = "+ newDepartment.getId());

		System.out.println("\n==== Test 4:  department  Update====");
		Department updateDepartment = departmentDao.findById(7);
		updateDepartment.setName("Lelton Borges");
		departmentDao.update(updateDepartment);
		System.out.println("Update completed");
		
		System.out.println("\n==== Test 5:  department  delete ====");
		departmentDao.deleteById(7);
		System.out.println("Delete completed");
	}

}
