package jdbcEx01;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;

public class BoardInsertTest {
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
            String sql = "INSERT INTO boards(btitle,bcontent,bwriter,bdate,bfilename,bfiledata) values(?, ?, ?, now(), ? ,?)";

            // PreparedStatement 얻기
            PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            // 값 지정
            pstmt.setString(1,"글제목3");
            pstmt.setString(2,"글내용33333");
            pstmt.setString(3,"작성자3");
            pstmt.setString(4, "spring.jpg");
            pstmt.setBlob(5,new FileInputStream("C:/Temp/spring.jpg"));


            // SQL문 실행
            int result = pstmt.executeUpdate();
            System.out.println("저장된 행의 수 "+result);

            int bno = 0;
            // bno 값 얻기
            if(result == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next()) {
                    bno = rs.getInt(1);
//                    System.out.println("bno = " + bno);

                }
                rs.close();
            }
            if(bno > 0){
                String selectSql = "SELECT bno, btitle, bcontent, bwriter, bdate, bfilename FROM boards WHERE bno = ?";

                try(PreparedStatement selectPstmt = con.prepareStatement(selectSql)) {

                    selectPstmt.setInt(1, bno);

                    try(ResultSet rs = selectPstmt.executeQuery()) {
                        while(rs.next()) {
                            bno = rs.getInt(1);
                            System.out.println("bno = " + bno);
                            System.out.println("btitle = " + rs.getString(2));
                            System.out.println("bcontent = " + rs.getString(3));
                            System.out.println("bwriter = " + rs.getString(4));
                            System.out.println("bdate = " + rs.getTimestamp(5));
                            System.out.println("bfilename = " + rs.getString(6));
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
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
