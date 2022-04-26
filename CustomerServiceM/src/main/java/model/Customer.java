package model;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;

import java.sql.*;
public class Customer {
	
	// A common method to connect to the DB
		private Connection connect() {
			Connection con = null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");

				// Provide the correct details: DBServer/DBName, username, password
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/customer", "root", "");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return con;
		}
		
		
		public String insertCustomer(String cName, String cAddress, int cPhone, String cEmail, String cType) {
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for inserting.";
				}
				// create a prepared statement
				String query = " insert into customers (cID, cName, cAddress, cPhone, cEmail, cType)" + " values (?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, cName);
				preparedStmt.setString(3, cAddress);
				preparedStmt.setInt(4, cPhone);
				preparedStmt.setString(5, cEmail);
				preparedStmt.setString(6, cType);
				preparedStmt.execute();
				con.close();
				output = "Inserted successfully";
			} catch (Exception e) {
				output = "Error while inserting the customer.";
				System.err.println(e.getMessage());
			}
			return output;
		}
		
		public String readCustomers() {
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for reading.";
				}
				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>AccoutID</th><th>Name</th><th>Address</th>" + "<th>Phone No</th>"
						+ "<th>Email</th>" + "<th>Con Type</th>" + "<th>Update</th><th>Remove</th></tr>";

				String query = "select * from customers";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				// iterate through the rows in the result set
				while (rs.next()) {
					String cID = Integer.toString(rs.getInt("cID"));
					String cName = rs.getString("cName");
					String cAddress = rs.getString("cAddress");
					String cPhone = Integer.toString(rs.getInt("cPhone"));
					String cEmail = rs.getString("cEmail");
					String cType = rs.getString("cType");
					// Add into the html table
					output += "<tr><td>" + cID + "</td>";
					output += "<td>" + cName + "</td>";
					output += "<td>" + cAddress + "</td>";
					output += "<td>" + cPhone + "</td>";
					output += "<td>" + cEmail + "</td>";
					output += "<td>" + cType + "</td>";
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
							+ "<td><form method='post' action='customers.jsp'>"
							+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
							+ "<input name='cID' type='hidden' value='" + cID + "'>" + "</form></td></tr>";
				}
				con.close();
				// Complete the html table
				output += "</table>";
			} catch (Exception e) {
				output = "Error while reading the customers.";
				System.err.println(e.getMessage());
			}
			return output;
		}
		
		
		public String updateCustomer(String cID, String cName, String cAddress, int cPhone, String cEmail, String cType) {

			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for updating.";
				}
				// create a prepared statement
				String query = "UPDATE customers SET cName=?,cAddress=?,cPhone=?,cEmail=?,cType=? WHERE cID=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setString(1, cName);
				preparedStmt.setString(2, cAddress);
				preparedStmt.setInt(3, cPhone);
				preparedStmt.setString(4, cEmail);
				preparedStmt.setString(5, cType);
				preparedStmt.setInt(6, Integer.parseInt(cID));
				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Updated successfully";
			} catch (Exception e) {
				output = "Error while updating the customer.";
				System.err.println(e.getMessage());
			}
			return output;
		}
		
		
		public String deleteCustomer(String cID) {
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for deleting.";
				}
				// create a prepared statement
				String query = "delete from customers where cID=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(cID));
				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Deleted successfully";
			} catch (Exception e) {
				output = "Error while deleting the customer.";
				System.err.println(e.getMessage());
			}
			return output;
		}

}
