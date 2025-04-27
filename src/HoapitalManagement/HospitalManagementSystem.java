package HoapitalManagement;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {

    private static final String url = "jdbc:mysql://localhost:3306/hospital"; // replace with your DB name
  private static  final   String username = "root"; // usually "root"
    private static final String password = "dani@4781"; // your MySQL password
    public static void main(String[] args) {
        try {
            // Load the MySQL JDBC driver (optional for newer versions)
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();}


            // Try to connect
        Scanner scanner=new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patients patients=new Patients(connection,scanner);
            Doctor doctor=new Doctor(connection);
            while (true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. add Patients");
                System.out.println("2. view Patients");
                System.out.println("3.view Doctor");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter Your Choice");
                int choice=scanner.nextInt();
                switch (choice){
                    case 1:
                        patients.addpatients();
                        System.out.println();
                        break;
                    case 2:
                       patients.viewpatients();
                        System.out.println();
                        break;
                    case 3:
                    doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                    BookAppointment(patients,doctor,connection,scanner);
                        System.out.println();
                        break;
                    case 5:
                    return ;
                    default:
                        System.out.println("invalid choice");
                        break;
                }
            }

//            if (connection != null) {
//                System.out.println("Database connected successfully!");
//            } else {
//                System.out.println("Failed to make connection!");
//            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void BookAppointment(Patients patients,Doctor doctor, Connection connection,Scanner scanner){
        System.out.print("Enter Patient id :");
        int patient_id=scanner.nextInt();
        System.out.print("Enter doctr id :");
        int doctor_id=scanner.nextInt();
        System.out.print("Enter Appointment Date (YYYY-MM-DD):");
        String Appointment_Data=scanner.next();
        if (patients.checkpatient(patient_id) && doctor.checkDoctor(doctor_id)) {
            if (checkdoctoravailability(doctor_id, Appointment_Data,connection)) {
                String AppointmentQuery = "INSERT INTO appointments(patient_id,doctor_id,appointments_date) VALUES(?,?,?);";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(AppointmentQuery);
                    preparedStatement.setInt(1, doctor_id);
                    preparedStatement.setInt(2, doctor_id);
                    preparedStatement.setString(3, Appointment_Data);
                    int rowsaffected = preparedStatement.executeUpdate();
                    if (rowsaffected > 0) {
                        System.out.println("Appointment booked");
                    } else {
                        System.out.println("failed to book Appointment");

                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Doctor not available on this date");
            }
        }
        else{
                System.out.println("Either patient or doctor does't exist");
            }

    }
    public static boolean checkdoctoravailability(int doctor_id,String Appointment_date ,Connection connection){
        String query="SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointments_date =?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,doctor_id);
            preparedStatement.setString(2,Appointment_date);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next()){
                int count=resultSet.getInt(1);
                if (count==0){
                    return true;
                }else {
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();

        } return false;

    }

}
