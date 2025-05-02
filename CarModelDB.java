
package carmodeldb;

import java.sql.*;
import java.util.Scanner;

/**
 * @author Olama 
 */
public class CarModelDB {

   
    public static void main(String[] args) {
        
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
        String url = "jdbc:derby://localhost:1527/CarDB";
        Scanner sc = new Scanner(System.in);
        
        try {
            conn = DriverManager.getConnection(url);
            stm = conn.createStatement();
            
            boolean running = true; 
             
            while(running){
               
            System.out.println("Menu: ");
            System.out.println("1. Display all car makes");
            System.out.println("2. Find the oldest model");
            System.out.println("3. Total value of all cars ");
            System.out.println("4. Most expensive car");
            System.out.println("5. The least expensive car");
            System.out.println("6. Exit");
            System.out.print("Your option:");
            int choice = sc.nextInt();
            
            rs = stm.executeQuery("SELECT * FROM CARS");
            switch(choice){
                
                case 1: 
                    displayAllCarMakes(rs);
                    break;
                case 2:
                    findOldestModel(rs);
                    break;
                case 3:
                    calculateTotalValue(rs);
                    break;
                case 4:    
                    findMostExpensiveCar(rs);
                    break;
                case 5:     
                    findLeastExpensiveCar(rs);
                    break;
                case 6:    
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:    
                    System.out.println("Invalid option!  Please choose a valid option\n");
                    break;
            }   
               
           }     
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            try {
                if (rs != null) rs.close();
                if (stm != null) rs.close();
                if (conn != null)conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            sc.close();
        }  
        
  
    }
    
    private static void displayAllCarMakes(ResultSet rs) throws SQLException{
           
            System.out.println("All car makes:");
            while(rs.next()){
                String make = rs.getString("Make");
                System.out.println(make); 
            }   
        }
    
    
    private static void findOldestModel(ResultSet rs) throws SQLException{
         
        int oldestYear = Integer.MAX_VALUE;
        String oldestModel = "";
        while(rs.next()){
           int year = rs.getInt("Manufacture_Year");
           if(year < oldestYear){
               oldestYear = year;
               oldestModel = rs.getString("Make") +""+ rs.getString("Model");
               
           }
            
        }
         System.out.println("Oldest car model: " + oldestModel +""+ "("+ oldestYear +")" );
    }
    
    
    private static void calculateTotalValue(ResultSet rs) throws SQLException{
         
        double totalValue = 0;
        while(rs.next()){
            totalValue += rs.getDouble("Price");
        }
        
        System.out.println("Total value of cars:" + totalValue);  
    }
    
    private static void findMostExpensiveCar(ResultSet rs) throws SQLException{
        
        double carPrice = 00;
        double expensiveCar;
        String car = "";
        while(rs.next()){
            expensiveCar = rs.getDouble("Price");
            if(expensiveCar > carPrice){
                carPrice = expensiveCar;
                car = rs.getString("Model")+""+rs.getString("Make");
                
            }
            
        }  
         System.out.println("The most expensive car: "+car+" Price: " + carPrice);
    }
    
    
    private static void findLeastExpensiveCar(ResultSet rs)throws SQLException{
        
        double cheapestCarPrice;
        double leastCarPrice = Double.MAX_VALUE;
        String cheapCar = "";
        while(rs.next()){
            cheapestCarPrice = rs.getDouble("Price");
            if(cheapestCarPrice < leastCarPrice){
                leastCarPrice = cheapestCarPrice;              
                cheapCar = rs.getString("Model")+""+rs.getString("Make");
            }
           
        }
        System.out.println("The least expensive car: "+ cheapCar+" Price: "+leastCarPrice);
         
    }
    
      
}
