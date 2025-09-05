package jdbcEx01;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;

public class AccountTest {
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
            String sql = "INSERT INTO accounts(ano,owner,balance) values(?, ?, ?)";

            // PreparedStatement 얻기
            PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            // 값 지정
            String ano = "1112-1213-3232-12122";
            pstmt.setString(1,ano);
            pstmt.setString(2,"엄홍길");
            pstmt.setString(3,"100000");

            // SQL문 실행
            int result = pstmt.executeUpdate();
            System.out.println("저장된 행의 수 "+result);

            if(!ano.equals("") && ano != null) {
                String selectSql = "SELECT ano, owner, balance FROM accounts WHERE ano = ?";

                try(PreparedStatement selectPstmt = con.prepareStatement(selectSql)) {

                    selectPstmt.setString(1, ano);

                    try(ResultSet rs = selectPstmt.executeQuery()) {
                        while(rs.next()) {
                            ano = rs.getString(1);
                            System.out.println("ano = " + ano);
                            System.out.println("owner = " + rs.getString(2));
                            System.out.println("balance = " + rs.getString(3));
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

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
