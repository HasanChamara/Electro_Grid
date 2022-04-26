package model;

import java.sql.*;

public class Billcal {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bill", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	
	public String insertBill(String accNumber, String name, int units, String date, double total) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			
			// create a prepared statement
			String query = " insert into billinfo (id, accNumber, name, units , date, total)" + " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, accNumber);
			preparedStmt.setString(3, name);
			preparedStmt.setInt(4, units);
			preparedStmt.setString(5, date);
			preparedStmt.setDouble(6, total);
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Billinfo.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	


	public String readBillInfo() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>ID</th><th>Acc Number</th><th>Acc Name</th>" + "<th>Units</th>"
					+ "<th>Date</th>"+ "<th>Total Payment</th>" + "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from billinfo";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String id = Integer.toString(rs.getInt("id"));
				String accNumber = rs.getString("accNumber");
				String name = rs.getString("name");
				String units = Integer.toString(rs.getInt("units"));
				String date = rs.getString("date");
				String total = rs.getString("total");
				// Add into the html table
				output += "<tr><td>" + id + "</td>";
				output += "<td>" + accNumber + "</td>";
				output += "<td>" + name + "</td>";
				output += "<td>" + units + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + total + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='billcal.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						+ "<input name='id' type='hidden' value='" + id + "'>" + "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Billcal.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	
	
	public String updateBillInfo(String id, String accNumber, String name, int units, String date, double total) {

		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE billinfo SET accNumber=?,name=?,units=?,date=?, total=? WHERE id=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, accNumber);
			preparedStmt.setString(2, name);
			preparedStmt.setInt(3, units);
			preparedStmt.setString(4, date);
			preparedStmt.setDouble(5, total);
			preparedStmt.setInt(6, Integer.parseInt(id));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the billinfo.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	

	public String deletBillInfo(String id) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from billinfo where id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(id));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the billinfo.";
			System.err.println(e.getMessage());
		}
		return output;
	}


}
