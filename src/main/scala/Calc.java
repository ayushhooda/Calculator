import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.sql.*;
import java.util.Date;
public class Calc {

    // method to retrieve all calculated results
    public void getResults(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2","root","knoldus");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Calculator");
            while(rs.next()){
                int id = rs.getInt("id");
                Date timeStamp = rs.getDate("time");
                int num1 = rs.getInt("left_op");
                int num2 = rs.getInt("right_op");
                char op = rs.getString("operator").charAt(0);
                int ans = rs.getInt("result");
                System.out.format("%s, %s, %s, %s, %s, %s\n", id, timeStamp, num1, num2, op, ans);
            }
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    // method to view Specific Operator Results
    public void getSpecificResults(char op1){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2","root","knoldus");
            PreparedStatement stmt = con.prepareStatement("select * from Calculator where operator = ?");
            stmt.setString(1,String.valueOf(op1));
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                Date timeStamp = rs.getDate("time");
                int num1 = rs.getInt("left_op");
                int num2 = rs.getInt("right_op");
                char op = rs.getString("operator").charAt(0);
                int ans = rs.getInt("result");
                System.out.format("%s, %s, %s, %s, %s, %s\n", id, timeStamp, num1, num2, op, ans);
            }
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }


    // method to insert the calculated result
    public void insertResult(int num1,int num2,char op,double ans){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2","root","knoldus");
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()));
            String query = "insert into Calculator (time, left_op, right_op, operator, result)"
                            + " values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1,timeStamp);
            preparedStmt.setInt(2,num1);
            preparedStmt.setInt(3,num2);
            preparedStmt.setString(4, String.valueOf(op));
            preparedStmt.setDouble(5,ans);
            preparedStmt.execute();
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }


    public void useCalculator(){
        Calc obj = new Calc();
        int num1,num2,ch;
        double ans=0;
        char op=' ';
        Scanner in = new Scanner(System.in);
        System.out.println("Enter first operand");
        num1 = in.nextInt();
        System.out.println("Enter second operand");
        num2 = in.nextInt();
        System.out.println("Select operation: ");
        System.out.println("1.Addition");
        System.out.println("2.Subtraction");
        System.out.println("3.Multiply");
        System.out.println("4.Divide");
        System.out.println("5.Power");
        System.out.println("6.Modulus");
        System.out.println("7.Maximum of two operands");
        System.out.println("8.Minimum of two operands");
        System.out.println("9.Back to menu");
        System.out.println("Enter your choice");
        ch = in.nextInt();
        switch(ch) {
            case 1:
                ans = num1 + num2;
                op = '+';
                break;
            case 2:
                ans = num1 - num2;
                op = '-';
                break;
            case 3:
                ans = num1 * num2;
                op = '*';
                break;
            case 4:
                ans = num1 / num2;
                op = '/';
                break;
            case 5:
                ans = Math.pow(num1, num2);;
                op = '^';
                break;
            case 6:
                ans = num1 % num2;
                op = '%';
                break;
            case 7:
                ans = num1 > num2 ? num1 : num2;
                op = '>';
                break;
            case 8:
                ans = num1 < num2 ? num1 : num2;
                op = '<';
                break;
            case 9:
                main(new String[] {"a","b","c"});
                break;
            default:
                System.out.println("Wrong choice");
                break;
        }
        if(op!=' ')
        obj.insertResult(num1,num2,op,ans);
    }


    public void getSelectedOperator() {
        Calc obj = new Calc();
        Scanner in = new Scanner(System.in);
        int ch1;
        char op1 = ' ';
        System.out.println("Select Operator: ");
        System.out.println("1. +");
        System.out.println("2. -");
        System.out.println("3. *");
        System.out.println("4. /");
        System.out.println("5. ^");
        System.out.println("6. %");
        System.out.println("7. >");
        System.out.println("8. <");
        System.out.println("9.Back to main menu");
        System.out.println("Enter your choice: ");
        ch1 = in.nextInt();
        switch(ch1){
            case 1:
                op1 = '+';
                break;
            case 2:
                op1 = '-';
                break;
            case 3:
                op1 = '*';
                break;
            case 4:
                op1 = '/';
                break;
            case 5:
                op1 = '^';
                break;
            case 6:
                op1 = '%';
                break;
            case 7:
                op1 = '>';
                break;
            case 8:
                op1 = '<';
                break;
            case 9:
                main(new String[] {"a","b","c"});
                break;
            default:
                System.out.println("Wrong choice");
                break;
        }
        if(op1!=' ')
        obj.getSpecificResults(op1);
    }


    public static void main(String[] args){
        Calc obj = new Calc();
        int ch;
        Scanner in = new Scanner(System.in);
        System.out.println("****MENU****");
        System.out.println("1.Use Calculator");
        System.out.println("2.Read all calculated data from table");
        System.out.println("3.Read all operations for specific operator");
        System.out.println("4.Exit");
        System.out.println("Enter your choice");
        ch = in.nextInt();
        switch(ch){
            case 1:
                obj.useCalculator();
                break;
            case 2:
                obj.getResults();
                break;
            case 3:
                obj.getSelectedOperator();
                break;
            case 4:
                break;
            default:
                System.out.println("Wrong choice");
                break;
        }

    }
}
