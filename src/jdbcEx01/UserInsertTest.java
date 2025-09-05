package jdbcEx01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserInsertTest {
    public static void main(String[] args) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bookmarketdb?serverTimezone=Asia/Seoul";
        String user = "root";
        String password = "mysql1234";


        try {
            Class.forName(driver);
            System.out.println("Driver loaded successfully");
        } catch (Exception e) {
            System.out.println("Driver loading failed");
            return;
        }

        try(Connection con = DriverManager.getConnection(url,user,password);) {
            System.out.println("AutoCommit 상태: " + con.getAutoCommit());
            con.setAutoCommit(true);

            // 매개변수화 된 SQL문
            String sql = "insert into users(userid,username,userpassword,userage,useremail) values(?, ?, ?, ?, ?)";

            // PreparedStatement 얻기
            PreparedStatement pstmt = con.prepareStatement(sql);

            // 값 지정
            pstmt.setString(1,"99");
            pstmt.setString(2,"김현식");
            pstmt.setString(3,"1234");
            pstmt.setInt(4, 55);
            pstmt.setString(5,"kim1@gmail.com");

            // SQL문 실행
            int result = pstmt.executeUpdate();
            System.out.println("저장된 행의 수 "+result);

            if (result == 1) {
                System.out.println("Insert successfully");
            } else {
                System.out.println("Insert failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
