package jdbcEx01;

import java.sql.*;

public class ConnectionPreparedInsert {
    public static void main(String[] args) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bookmarketdb?serverTimezone=Asia/Seoul";
        String user = "root";
        String password = "mysql1234";


        try {
            // 1. DB의 드라이버를 찾아서 로드해야 한다.    MYSQL JDBC 드라이버 등록
            Class.forName(driver);
            System.out.println("Driver loaded successfully");
        } catch (Exception e) {
            System.out.println("Driver loading failed");
            return;
        }

        // 2. 드라이버로드가 OK라면, 연결 Connection 객체 생성


        try(
            // 도로 연결
            Connection con = DriverManager.getConnection(url,user,password);
        ) {
            System.out.println("AutoCommit 상태: " + con.getAutoCommit());
            con.setAutoCommit(true);

            // 매개변수화 된 SQL문
            String sql = " insert into person(id,name) values(?, ?)";

            // PreparedStatement 얻기
            PreparedStatement pstmt = con.prepareStatement(sql);
            
            // 값 지정
            pstmt.setInt(1, 100);
            pstmt.setString(2,"신길동");

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
