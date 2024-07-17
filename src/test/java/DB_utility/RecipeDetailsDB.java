package DB_utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RecipeDetailsDB {
	    private static final String URL = "jdbc:postgresql://localhost:5432/startechies scraping hackaton";
	    private static final String USER = "postgres";
	    private static final String PASSWORD = "postgress";
	    private void connect() {
	    try (Connection connection=DriverManager.getConnection(URL, USER, PASSWORD);){
	    if(connection!= null) {
	    	System.out.println("connection successful");
	    }else {
	    	System.out.println("connection not successful");
	    	
	    }
	}catch(SQLException e) {
		e.printStackTrace();
	}
	}
	  public static void main(String[] args){
		  RecipeDetailsDB SqlConnect=new RecipeDetailsDB();
		  SqlConnect.connect();
	  }
	}


