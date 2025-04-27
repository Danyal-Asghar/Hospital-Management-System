package HoapitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patients {
    private Connection connection;
    private Scanner scanner;
    public Patients(Connection connection, Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }
    public void addpatients(){
        System.out.print("enter patients name:");
        String name=scanner.next();
        System.out.print("enter patients age:");
        String age=scanner.next();
        System.out.print("enter patients gender:");
        String gender=scanner.next();

        try{
         String query="INSERT INTO patients(name,age,gender) VALUES(?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,age);
            preparedStatement.setString(3,gender);
            int effectedrow=preparedStatement.executeUpdate();
            if (effectedrow>0){
                System.out.println("add patients successfully");
            }else {
                System.out.println("failed to add patients");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public void viewpatients(){
        String query="select * from patients";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Patients:");
            System.out.println("+-------------+-------------+-------------+------------+");
            System.out.println("| Patients id | Name        | Age         | Gender     |");
            System.out.println("+-------------+-------------+-------------+------------+");
            while (resultSet.next()){
                int id =resultSet.getInt("id");
                String name=resultSet.getString("name");
                int age=resultSet.getInt("age");
                String gender=resultSet.getString("gender");
                System.out.printf("| %-11s  |%-11s | %-11s | %-10s \n",id,name,age,gender);
                System.out.println("+-------------+-------------+-------------+------------+");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean checkpatient(int id){
        String Query = "select * from patients where id =?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
