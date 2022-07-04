package com.BankingSystem;

import java.sql.Connection;
import java.sql.DriverManager;
// Global connection Class
public class connection {
	static Connection con; // Global Connection Object
	public static Connection getConnection()
	{
		try {
			
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@localhost:1521/xe";
			String user = "sys as sysdba";	 //mysql username
			String pass = "root"; //mysql passcode
			
			con = DriverManager.getConnection(url, user,pass);
			System.out.println("Connection ");
		}
		catch (Exception e) {
			System.out.println("Connection Failed!");
		}

		return con;
	}
}
