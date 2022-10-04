package com.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.model.User;

public class UserDao 
{
   private final String driver = "com.mysql.cj.jdbc.Driver";
   private final String jdbcurl = "jdbc:mysql://localhost:3306/teckdb";
   private final String jdbcUsername="root";
   private final String jdbcPassword = "root";
   private final String SELECT_ALL_USERS = "select * from users;";
   private final String INSERT_USERS_SQL ="insert into users(name,email,country)values(?,?,?);";
   private final String SELECT_USERS_BY_ID="select id,name,email,country from users where id=?;";
   private final String UPDATE_USERS_SQL="update users set name=?,email=?,country=? where id=?;";
   private final String DELETE_USERS_SQL="delete from users where id=?;";
   
   protected Connection getMyconnection()
   {
	   Connection con=null;
	   try
	   {
		 Class.forName(driver);
		 con=DriverManager.getConnection(jdbcurl,jdbcUsername,jdbcPassword); 
	   }
	   catch(SQLException e)
	   {
		   e.printStackTrace();
	   }
	   catch(ClassNotFoundException e)
	   {
		   e.printStackTrace();
	   }
	   return con;
   }
   public List<User> SelectAllUsers()
   {
	   List<User> users= new ArrayList<User>();
	   try
	   {
		   Connection con=getMyconnection();
		   PreparedStatement pstmt=con.prepareStatement(SELECT_ALL_USERS);
		   ResultSet rs=pstmt.executeQuery();
		   while(rs.next())
		   {
			   int id=rs.getInt("id");
			   String name=rs.getString("name");
			   String email=rs.getString("email");
			   String country=rs.getString("country");
			   users.add(new User(id,name,email,country));   
		   }
	   }
	   catch(SQLException e)
	   {
		   e.printStackTrace();
	   }
	   return users;
   }
   public void insertUser(User u)throws SQLException
   {
	   try
	   {
		   Connection con=getMyconnection();
		   PreparedStatement pstmt=con.prepareStatement(INSERT_USERS_SQL);
		   pstmt.setString(1,u.getName());
		   pstmt.setString(2,u.getEmail());
		   pstmt.setString(3, u.getCountry());
		   pstmt.executeUpdate();
		   
	   }
	   catch(SQLException e)
	   {
		   e.printStackTrace();
	   }
   }
public User SelectUser(int id)
{
	User u=null;
	try
	{
		Connection con=getMyconnection();
		   PreparedStatement pstmt=con.prepareStatement(SELECT_USERS_BY_ID);
		   ResultSet rs=pstmt.executeQuery();
		   while(rs.next())
		   {
			   String name=rs.getString("name");
			   String email=rs.getString("email");
			   String country=rs.getString("country");
			   u=new User(id,name,email,country);   
		   }
	   }
	   catch(SQLException e)
	   {
		   e.printStackTrace();
	   }
	   return u;
	}
public int updateUser(User u)throws SQLException
{
	int rowupdated=0;
	try
	{
		Connection con=getMyconnection();
		   PreparedStatement pstmt=con.prepareStatement(UPDATE_USERS_SQL);
		   pstmt.setString(1,u.getName());
		   pstmt.setString(2,u.getEmail());
		   pstmt.setString(3, u.getCountry());
		   pstmt.setInt(4,u.getId());
		   rowupdated=pstmt.executeUpdate();
    }
	catch(SQLException e)
	   {
		   e.printStackTrace();
	   }
	return rowupdated;
}
public int deleteUser(int id)throws SQLException
{
	int rowdeleted=0;
	try
	{
		Connection con=getMyconnection();
		   PreparedStatement pstmt=con.prepareStatement(DELETE_USERS_SQL);
		   pstmt.setInt(1,id);
		   rowdeleted=pstmt.executeUpdate();
	}
	catch(SQLException e)
	   {
		   e.printStackTrace();
	   }
	return rowdeleted;
}
}

