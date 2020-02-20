package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.connections.DB;
import model.entities.Department;
import model.exceptions.DbException;
import model.model.dao.DepartmentDao;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection cnn;

	public DepartmentDaoJDBC(Connection cnn) {
		this.cnn = cnn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement ps = null;
		
		try {
			ps = cnn.prepareStatement(
					"INSERT INTO department (Name)"
					+ "VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, obj.getName());
			int rowsAffected = ps.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next()) {
				 int id = rs.getInt(1);
				 obj.setId(id);
				}
			}else {
				throw new DbException("Unexpected error! No rows affected.");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.CloseStatement(ps);
		}
		
	}

	@Override
	public void update(Department obj) {
		PreparedStatement ps = null;
		try {
			ps = cnn.prepareStatement(
					"UPDATE department SET Name = ? where Id = ?");
			ps.setString(1, obj.getName());
			ps.setInt(2, obj.getId());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.CloseStatement(ps);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		
		try {
			ps = cnn.prepareStatement("DELETE FROM department "
					+ "WHERE Id = ?");
			ps.setInt(1, id);
			
			ps.executeUpdate();
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.CloseStatement(ps);
		}

	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cnn.prepareStatement("SELECT * FROM department " + "WHERE Id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			Department d = null;
			if (rs.next()) {
				d = instantiateDepartment(rs);
			}
			return d;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.CloseResultSet(rs);
			DB.CloseStatement(ps);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = cnn.prepareStatement("SELECT * FROM department ORDER BY Name");
			rs = ps.executeQuery();
			List<Department> listDepartment = new ArrayList<>();

			while (rs.next()) {
				Department d = instantiateDepartment(rs);
				listDepartment.add(d);
			}
			return listDepartment;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.CloseResultSet(rs);
			DB.CloseStatement(ps);
		}
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department d = new Department();
		d.setId(rs.getInt("Id"));
		d.setName(rs.getString("Name"));
		return d;
	}

}
