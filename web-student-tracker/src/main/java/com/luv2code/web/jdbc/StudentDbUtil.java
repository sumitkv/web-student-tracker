package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {
	private DataSource dataSource;
	
	public StudentDbUtil(DataSource theDataSource) {
		dataSource=theDataSource;
	}
	
	public List<Student> getStudents() throws Exception{
		List<Student> students = new ArrayList<>();
		
		Connection myConn=null;
		Statement myStmt=null;
		ResultSet myRs=null;
		//int studentId;
		
		try {
			// convert student id to int
			//studentId = Integer.parseInt(theStudentId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to get selected student
			String sql = "select * from student order by last_name";
			
			// create prepared statement
			//myStmt = myConn.prepareStatement(sql);
			myStmt = myConn.createStatement();
			
			// set params
			//myStmt.setInt(1, studentId);
			
			// execute statement
			myRs = myStmt.executeQuery(sql);
			
			// retrieve data from result set row
			while (myRs.next()) {
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				
				// use the studentId during construction
				Student tempStudent = new Student(id, firstName, lastName, email);
				students.add(tempStudent);
			}
			return students;
	
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
			
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		// TODO Auto-generated method stub
		try {
			if(myRs !=null) {
				myRs.close();
			}
			if(myStmt !=null) {
				myStmt.close();
			}
			if(myConn !=null) {
				myConn.close();
			}
			
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
	}

	public void addStudent(Student theStudent) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create sql for insert
			String sql = "insert into student "
					   + "(first_name, last_name, email) "
					   + "values (?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set the param values for the student
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			
			// execute sql insert
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
		
		
	}

}
