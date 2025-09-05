package jdbcEx01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionTest {
    public static void main(String[] args) {
        try {
            // 1. DB의 드라이버를 찾아서 로드해야 한다.    MYSQL JDBC 드라이버 등록
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");
        } catch (Exception e) {
            System.out.println("Driver loading failed");
            return;
        }

        // 2. 드라이버로드가 OK라면, 연결 Connection 객체 생성
        String url = "jdbc:mysql://localhost:3306/bookmarketdb?serverTimezone=Asia/Seoul";
        String user = "root";
        String password = "mysql1234";

        String sql = " insert into person(id,name) values(1, '신세계')";

        try(
            // 5. 다 사용한 Statements와 Connection 객체를 닫는다.
            Connection con = DriverManager.getConnection(url,user,password);
            // 3. Connection 객체가 생성되었다면, 쿼리문을 Statemens 객체에 담아 DB에게 전송한다.s
            Statement stmt = con.createStatement();
        ) {
            // 4. 전송한 결과를 받아서 처리한다.
            System.out.println("Connection established successfully " + con);

            int result = stmt.executeUpdate(sql);
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
