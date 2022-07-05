package com.BankingSystem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
 
public class bankManagement { 
                             
 
    private static final int NULL = 0;
 
    static Connection con = connection.getConnection();
    static String sql = "";
    public static boolean
    createAccount(String name, int passCode)
    {
        try {
           
            if (name == "" || passCode == NULL) {
                System.out.println("All Field Required!");
                return false;
            }
            
            Statement st = con.createStatement();
            sql = "INSERT INTO customer_details (ac_no,cname,balance,pass_code) values(1003,'"+ name + "',1000," + passCode + ")";
 
           
            if (st.executeUpdate(sql) == 1) {
                System.out.println(name + ", Now You Login!");
                return true;
            }
            
        }
       
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean
    loginAccount(String name, int passCode) 
    {
        try {
            
            if (name == "" || passCode == NULL) {
                System.out.println("All Field Required!");
                return false;
            }
            
            sql = "select * from customer_details where cname='"+ name + "' and pass_code=" + passCode;
            PreparedStatement st= con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
           
            BufferedReader sc = new BufferedReader( new InputStreamReader(System.in));
            
            if (rs.next()) {
                
 
                int ch = 5;
                int amt = 0;
                int senderAc = rs.getInt("ac_no");
               String cname = rs.getString("cname");
                int balance= Integer.parseInt(rs.getString("balance"));
                int receiveAc;
                System.out.println("\n");
               // System.out.println("balance : "+balance);
                System.out.println( "Hello, " + cname +"Welcome Back");
                System.out.println("\n");
                System.out.println("Your Account Details ");
                System.out.print("------------------------------------------ \n");
 				
                System.out.println( " | Account No. | Name | Balance |");
                System.out.print("------------------------------------------ \n");
 				
                System.out.println( " | "+senderAc+" | " + cname + " | "+ balance+" | ");
                System.out.print("-------------------------------------------\n");
 				
                //System.out.println("sender account no :"+senderAc);
                while (true) {
                    try {
                    	System.out.println("\n");
                    	System.out.println("Perform Operations :");
                        System.out.println("1)Transfer Money");
                        System.out.println("2)Deposit Money");
                        System.out.println("3)Withdraw Money");
                        System.out.println("4)View Balance");
                        System.out.println("5)View Reports");
                        System.out.println("6)LogOut");
                        System.out.print("Enter Choice:");
                        ch = Integer.parseInt(sc.readLine());
                        if (ch == 1) {
                            System.out.print(
                                "Enter Receiver  A/c No:");
                            receiveAc = Integer.parseInt(
                                sc.readLine());
                            System.out.print(
                                "Enter Amount:");
                            amt = Integer.parseInt(
                                sc.readLine());
                            if(amt>balance)
                            {
                            	System.out.println("insufficient balance");
                            	
                            }
                            else
                            {
                            	balance=balance-amt;
                            	 try {
                            	 PreparedStatement stmt = con.prepareStatement("update customer_details set balance = ? where ac_no=? ");
                            		 stmt.setInt(1, balance );
                                     stmt.setInt(2,senderAc);
                            		 
                                     if (stmt.executeUpdate() == 1) {
                                         System.out.println("Money transfer sucessfull");
                                         System.out.println("Your Account Balance : Rs."+balance);
                                         try {
                                        	 
                                        	 PreparedStatement stmt1 = con.prepareStatement("insert into transaction (ac_no,cname,bank_name,transaction_type,receiver_acno,amount,balance,status) values (?,?,'SBI','Transfer',?,?,?,'complete')");
                                        	 stmt1.setInt(1,senderAc);
                                             stmt1.setString(2,cname);
                                             stmt1.setInt(3,receiveAc);
                                             stmt1.setInt(4,amt);
                                             stmt1.setInt(5,balance);
                                             if (stmt1.executeUpdate() == 1) {
                                                 System.out.println("Inserted into transaction table");
                                             }
                                         }
                                            
                                             catch(Exception e){
                                  
                                            	 System.out.println(e);
                                         
                                     }
                                     System.out.print("---------------------------------------------------- \n");
                                     }
                            	 }
                                     
                                 
                                
                                 catch (Exception e) {
                                     e.printStackTrace();
                                 }
                            }
                        }
                        
                        
                        
                        
                        
                        if (ch == 2) {
                           
                            System.out.print(
                                "Enter Amount to Deposit :");
                            amt = Integer.parseInt(
                                sc.readLine());
                            
                            
                            	balance=balance+amt;
                            	 try {
                            	 PreparedStatement stmt = con.prepareStatement("update customer_details set balance = ? where ac_no=? ");
                            		 stmt.setInt(1, balance );
                                     stmt.setInt(2,senderAc);
                            		 
                                     if (stmt.executeUpdate() == 1) {
                                         System.out.println("Money Credited sucessfully");
                                         System.out.println("Your Account Balance : Rs."+balance);
                                         
                                     }
                                     System.out.print("---------------------------------------------------- \n");
                     				
                                     
                                 
                            	 }
                                 catch (Exception e) {
                                     e.printStackTrace();
                                 }
                            }
 
                          
                        
                        
                        if (ch == 3) {
                            
                            System.out.print(
                                "Enter Amount to Withdrawn :");
                            amt = Integer.parseInt(
                                sc.readLine());
                            if(amt>balance)
                            {
                            	System.out.println("insufficient balance");
                            	
                            }
                            else
                            {
                            	balance=balance-amt;
                            	 try {
                            	 PreparedStatement stmt = con.prepareStatement("update customer_details set balance = ? where ac_no=? ");
                            		 stmt.setInt(1, balance );
                                     stmt.setInt(2,senderAc);
                            		 
                                     if (stmt.executeUpdate() == 1) {
                                         System.out.println("Money Debitd Sucessfully");
                                         System.out.println("Your Account Balance : Rs."+balance);
                                         
                                     }
                                     System.out.print("---------------------------------------------------- \n");
                     				
                                     
                                 }
                                
                                 catch (Exception e) {
                                     e.printStackTrace();
                                 }
                            }
                        }
                        
                        
                        else if (ch == 4) {
 
                        	
                        	String sql1 = "";
                        	sql1 = "select * from customer_details where cname='"+ name + "' and pass_code=" + passCode;
                            PreparedStatement st1= con.prepareStatement(sql);
                            ResultSet rs1 = st.executeQuery();
                            if (rs1.next()) {
                            System.out.println( "Your Balance is : " + rs1.getString("balance"));
                            }
                            System.out.print("---------------------------------------------------- \n");
            				
                        }
                        else if (ch == 5) {
 
                        	
                        	String sql1 = "";
                        	
                        	sql1 = "select * from transaction where ac_no="+ senderAc ;
                            PreparedStatement st1= con.prepareStatement(sql1);
                            ResultSet rs1 = st1.executeQuery();
                            if (rs1.next()) {
                            	 System.out.println( "| Account No | Name | Bank | Transaction Type | Receiver Acc_no | Amount | Balance | Date | Status");
                            	 System.out.println( "|"+ rs1.getString("ac_no") + "|" +rs1.getString("cname")+ "|"+ rs1.getString("bank_name") +"|"+ rs1.getString("transaction_type") +"|"+ rs1.getString("receiver_acno") +"|"+ rs1.getString("amount") +"|"+ rs1.getString("balance") +"|"+ rs1.getString("date_time") +"|"+ rs1.getString("status"));
                                 
                            	 System.out.println( "Your Balance is : " + rs1.getString("balance"));
                            }
                            System.out.print("---------------------------------------------------- \n");
            				
                        }
                        else if (ch == 6) {
                            break;
                        }
                        else {
                            System.out.println(
                                "Err : Enter Valid input!\n");
                        }
                        System.out.print("---------------------------------------------------- \n");
        				
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                return false;
            }
          
            return true;
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username Not Available!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void
    getBalance(int acNo) 
    {
        try {
 
           
            sql = "select * from customer where ac_no="
                  + acNo;
            PreparedStatement st
                = con.prepareStatement(sql);
 
            ResultSet rs = st.executeQuery(sql);
            System.out.println(
                "-----------------------------------------------------------");
            System.out.printf("%12s %10s %10s\n",
                              "Account No", "Name",
                              "Balance");
 
           
 
            while (rs.next()) {
                System.out.printf("%12d %10s %10d.00\n",
                                  rs.getInt("ac_no"),
                                  rs.getString("cname"),
                                  rs.getInt("balance"));
            }
            System.out.println(
                "-----------------------------------------------------------\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean transferMoney(int sender_ac,
                                        int reveiver_ac,
                                        int amount)
        throws SQLException 
    {
        
        if (reveiver_ac == NULL || amount == NULL) {
            System.out.println("All Field Required!");
            return false;
        }
        try {
            con.setAutoCommit(false);
            sql = "select * from customer_details where ac_no="
                  + sender_ac;
            PreparedStatement ps
                = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
 
            if (rs.next()) {
                if (rs.getInt("balance") < amount) {
                    System.out.println(
                        "Insufficient Balance!");
                    return false;
                }
            }
 
            Statement st = con.createStatement();
 
            
            con.setSavepoint();
 
            //sql = st.executeUpdate(sql) == 1) {
             //   System.out.println("Amount Debited!");
           // }
 
           
            sql = "update customer_details set balance=balance+"
                  + amount + " where ac_no=" + reveiver_ac;
            st.executeUpdate(sql);
 
            con.commit();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            con.rollback();
        }
        
        return false;
    }
}

