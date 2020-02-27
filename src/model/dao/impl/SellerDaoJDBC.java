package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.connections.DB;
import model.entities.Department;
import model.entities.Seller;
import model.exceptions.DbException;
import model.model.dao.SellerDao;

public class SellerDaoJDBC implements SellerDao {
	private Connection cnn;

	public SellerDaoJDBC(Connection cnn) {
		this.cnn = cnn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement ps  = null;
		
		try {
			ps = cnn.prepareStatement(""
					+ "INSERT INTO seller " 
					+"(Name, Email, BirthDate, BaseSalary, DepartmentId) " + 
					"VALUES " 
					+"(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			ps.setDouble(4, obj.getBaseSalary());
			ps.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = ps.executeUpdate();
			if(rowsAffected > 0 ) {
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.CloseResultSet(rs);
			}else {
				throw new DbException("Unexpected error! No rows affected.");
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.CloseStatement(ps);
		}
		

	}

	@Override
	public void update(Seller obj) {
		PreparedStatement ps = null;
		try {
			ps = cnn.prepareStatement(
					"UPDATE seller " + 
					"SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " + 
					"WHERE Id = ?");
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			ps.setDouble(4, obj.getBaseSalary());
			ps.setInt(5, obj.getDepartment().getId());
			ps.setInt(6, obj.getId());
			
			ps.executeUpdate();
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.CloseStatement(ps);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = cnn.prepareStatement(
					"DELETE FROM seller " + 
					"WHERE Id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.CloseStatement(ps);
		}

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cnn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");
			ps.setInt(1, id);

			rs = ps.executeQuery();
			if (rs.next()) {
				Department d = instantiateDepartment(rs);
				Seller s = instantiateSeller(rs, d);
				return s;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.CloseStatement(ps);
			DB.CloseResultSet(rs);
		}

	}

	private Seller instantiateSeller(ResultSet rs, Department d) throws SQLException {
		Seller s = new Seller();
		s.setId(rs.getInt("Id"));
		s.setName(rs.getString("Name"));
		s.setEmail(rs.getString("Email"));
		s.setBaseSalary(rs.getDouble("BaseSalary"));
		s.setBirthDate(new java.util.Date(rs.getTimestamp("BirthDate").getTime()));
		s.setDepartment(d);
		return s;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department d = new Department();
		d.setId(rs.getInt("DepartmentId"));
		d.setName(rs.getString("DepName"));
		return d;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = cnn.prepareStatement(
			"SELECT seller.*,department.Name as DepName " 
			+ "FROM seller INNER JOIN department "
			+ "ON seller.DepartmentId = department.Id " 
			+ "ORDER BY Name");
			
			rs = ps.executeQuery();
			List<Seller> listSeller = new ArrayList<Seller>();
			Map<Integer, Department> mapDepartmentId = new HashMap<Integer, Department>();
			while (rs.next()) {
				Department d = mapDepartmentId.get(rs.getInt("DepartmentId"));
				if (d == null) {
					d = instantiateDepartment(rs);
					mapDepartmentId.put(rs.getInt("DepartmentId"), d);
				}
				Seller s = instantiateSeller(rs, d);
				listSeller.add(s);
			}
			return listSeller;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.CloseStatement(ps);
			DB.CloseResultSet(rs);
		}
	}

	@Override
	public List<Seller> fildByDepartment(Department department) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cnn.prepareStatement(
					"SELECT seller.*, department.Name as DepName " 
			+ "FROM seller INNER JOIN department "
			+ "ON seller.DepartmentId = department.Id " 
			+ "WHERE DepartmentId = ? " 
			+ "ORDER BY Name");
			
			ps.setInt(1, department.getId());
			rs = ps.executeQuery();

			List<Seller> listSeller = new ArrayList<>();
			Map<Integer, Department> mapDepartament = new HashMap<Integer, Department>();

			while (rs.next()) {
				Department d = mapDepartament.get(rs.getInt("DepartmentID"));
				if (d == null) {
					d = instantiateDepartment(rs);
					mapDepartament.put(rs.getInt("DepartmentId"), d);
				}

				Seller s = instantiateSeller(rs, d);
				listSeller.add(s);

			}
			return listSeller;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.CloseStatement(ps);
			DB.CloseResultSet(rs);
		}
	}

}
