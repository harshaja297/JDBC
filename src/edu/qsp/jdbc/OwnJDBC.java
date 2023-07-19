package edu.qsp.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class OwnJDBC {

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo_db","root","root");
			
			return connection;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static PreparedStatement getPreparedStatement(String str) throws Exception{
		PreparedStatement preparedStatement= getConnection().prepareStatement(str);
		
		return preparedStatement;
	}
	
	public static void main(String[] args) {
		String sqlString = "";
		boolean b= true;
		Scanner intIn = new Scanner(System.in);
		Scanner strIn = new Scanner(System.in);
		do {
		System.out.println("1.Insert Data");
		System.out.println("2.Get All Data");
		System.out.println("3.Remove Data");
		System.out.println("4.Get Data by Id ");
		System.out.println("5.Update by Id ");
		System.out.println("6.Exit");
		int choice = intIn.nextInt();
		switch (choice) {
		case 1: {
			try {
				sqlString = "insert into testfirst values(?,?,?)";
				PreparedStatement preparedStatement= getConnection().prepareStatement(sqlString);
				
				System.out.println("Enter id :");
				int id = intIn.nextInt();
				preparedStatement.setInt(1, id);
				System.out.println("Enter name :");
				String name = strIn.nextLine();
				preparedStatement.setString(2, name);
				System.out.println("Enter age :");
				int age = intIn.nextInt();
				preparedStatement.setInt(3, age);
				
				preparedStatement.executeUpdate();
				System.out.println("DataBase Updated");
				System.out.println("----------------------------------");
				break;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("ID may be duplicate or illegal input Check Properly");
				break;
			}
		}
		case 2: {
			sqlString = "select * from testfirst ";
			try {
				PreparedStatement preparedStatement= getPreparedStatement(sqlString);
				
				ResultSet rs = preparedStatement.executeQuery();
				
				while(rs.next()) {
					int id = rs.getInt(1);
					int age = rs.getInt(3);
					String name = rs.getString(2);
					System.out.println("id- "+id + " name- "+name +" age- "+age );
					
				}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		}
		case 3: {
			sqlString = "delete from testfirst where id= ?";
			try {
				PreparedStatement preparedStatement= getPreparedStatement(sqlString);
				System.out.println("Enter Id to remove");
				int id = intIn.nextInt();
				preparedStatement.setInt(1, id);
				preparedStatement.executeUpdate();
				System.out.println("Deleted Successfully ");
			break;
			}catch(Exception e) {
				e.printStackTrace();
				break;
			}
		}
		case 4: {
			sqlString = "Select name,id,age from testfirst where id = ?";
			PreparedStatement preparedStatement;
			try {
				preparedStatement = getPreparedStatement(sqlString);
				System.out.println("Enter Id to select");
				int id = intIn.nextInt();
				preparedStatement.setInt(1, id);
				
				ResultSet rs= preparedStatement.executeQuery();
				while(rs.next()) {
					int id2 = rs.getInt("id");
					int age = rs.getInt("age");
					String name = rs.getString("name");
					System.out.println("id- "+id2 + " name- "+name +" age- "+age );
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		break;
		}
		case 5: {
			System.out.println("Enter Id :");
			int id = intIn.nextInt();
			sqlString = "update testfirst set name= ?, age = ? where id = ?";//important
			try {
				PreparedStatement preparedStatement = getPreparedStatement(sqlString);
				System.out.println("Enter name ");
				String name = strIn.nextLine();
				System.out.println("Enter age ");
				int age = intIn.nextInt();
				
				preparedStatement.setInt(2, age);
				preparedStatement.setInt(3, id);
				preparedStatement.setString(1, name);
				
				preparedStatement.executeUpdate();
				
				System.out.println("Updated Successfully");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		break;
		}
		case 6: {System.out.println("ThankYou");
			
		b = false;
		break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + choice);
		}
		}while(b);
		
	}
}
