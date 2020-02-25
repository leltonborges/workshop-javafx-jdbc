package model.connections;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import model.exceptions.DbException;
import model.exceptions.FileExcecption;

public class DB {

	private static Connection cnn = null;

	public static Connection getConnection() {
		if(cnn == null) {
			try {
			Properties props = loadProperties();
			String url = props.getProperty("url");
			cnn = DriverManager.getConnection(url, props);
			//JOptionPane.showMessageDialog(null, "sucesso");

			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return cnn;
	}
	
	public static void closeConnection() {
		if(cnn != null) {
			try {
				cnn.close();
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	private static Properties loadProperties() {
		try (FileInputStream fis = new FileInputStream("src/db.properties")) {
			Properties props = new Properties();
			props.load(fis);
			return props;
		} catch (IOException e) {
			throw new FileExcecption(e.getMessage());
		}
	}
	
	public static void CloseStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void CloseResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
