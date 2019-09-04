package io.infinitestrike.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;

public class SqlConnector {
	
	public static final int dropKey = 0xDEC0DE;
	private ArrayList<Statement> statments = new ArrayList<Statement>();
	
	private Connection connection;
	
	public SqlConnector(String name){
		try{
			Class.forName("org.sqlite.JDBC");
			
			connection = DriverManager.getConnection("jdbc:sqlite:" + name);
		}catch(Exception e){
			LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage());
			connection = null;
		}
	}
	
	public Statement createStatement()throws DataBaseNotFoundException{
		
		if(connection == null){
			throw new DataBaseNotFoundException("No database file found.");
		}
		
		try {
			Statement s = connection.createStatement();
			this.statments.add(s);
			return s;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage());
			return null;
		}
	}
	
	public void executeUpdate(String str){
		Statement s = null;
		try {
			s = connection.createStatement();
			s.executeUpdate(str);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage());
		}finally{
			try {
				s.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage());
			}
		}
	}
	
	public ResultSet executeQuery(String str){
		Statement s = null;
		ResultSet set = null;
		try {
			s = connection.createStatement();
			set = s.executeQuery(str);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage());
		}finally{
			try {
				s.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage());
			}
		}
		
		return set;
	}
	
	public boolean tableExists(String name) throws DataBaseNotFoundException{
		if(connection == null){
			throw new DataBaseNotFoundException("No database file found.");
		}
		
		try {
			DatabaseMetaData dbm = connection.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, name, null);
			if (tables.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage());
			return false;
		}
	}
	
	public boolean recordExists(String table, String recordColumn, String recordName) throws SQLException, DataBaseNotFoundException{
		Statement s = this.createStatement();
		ResultSet set = s.executeQuery("SELECT * FROM " + table);
		while(set.next()){
			if(set.getString(recordColumn).equals(recordName)){
				return true;
			}
		}
		return false;
	}
	
	
	public void dropTable(String table, int key){
		if(key == SqlConnector.dropKey){
			this.executeUpdate("DROP TABLE " + table);
		}
	}
	
	public void dropTable(String table){
		dropTable(table,0);
	}
	
	public String getReadableResultSet(ResultSet rs) {
		try {
			String ret = "";
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			while(rs.next()){
				for(int i = 0; i <= cols; i++){
					if(i > 1){
						ret += ",  ";
					}
					String colVal = rs.getString(i);
					ret += colVal + " " + rsmd.getColumnName(i);
				}
				ret += "\n";
			}
			return ret;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return null;
	}
	
	public void close() throws SQLException{
		for(Statement s : this.statments){
			s.close();
		}
		this.connection.close();
	}
}
