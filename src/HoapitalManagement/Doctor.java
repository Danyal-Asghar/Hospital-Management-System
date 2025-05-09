package HoapitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;
    public Doctor(Connection connection){
        this.connection=connection;

    }

    public void viewDoctors(){
        String query="select * from doctors";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Doctor:");
            System.out.println("+-------------+-----------+-----------------+");
            System.out.println("| Doctor id | Name        | Specialization  |");
            System.out.println("+-------------+-----------+-----------------+");
            while (resultSet.next()){
                int id =resultSet.getInt("id");
                String name=resultSet.getString("name");
               String specialization=resultSet.getString("specialization");
                System.out.printf("| %-11s | %-9s | %-15s \n",id,name,specialization);
                System.out.println("+-------------+-----------+-----------------+");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean checkDoctor(int id){
        String Query = "select * from doctors where id =?";
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
