package jdbcEx02;

import jdbcEx01.vo.Person;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// UPDATE 테이블명 SET (필드 = '수정값') WHERE num = 1;
// String sql = "UPDATE person SET id = ?, name = ? WHERE num = ?";
// String sql = new StringBuilder()
//              .append("UPDATE person SET ")
//              .append("id = ?, ")
//              .append("name = ? ")
//              .append("WHERE num = ?").toString();
public class PersonUpdateEx {
    public static void main(String[] args) {
        Connection connection = DBUtil.getConnection();

        // 매개변수화된 UPDATE SQL문
        String sql = new StringBuilder()
                .append("UPDATE person SET ")
                .append("id = ?, ")
                .append("name = ? ")
                .append("WHERE num = ?").toString();

        // PreparedStatement 객체 생성하고 값 지정
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setInt(1, 100);
            pstmt.setString(2,"김도윤");
            pstmt.setInt(3, 1);
            
            // SQL문 실행
            int rows = pstmt.executeUpdate();
            // SELECT 할때는 pstmt.executeQuery();
            // INSERT, DELETE, UPDATE는 pstmt.executeUpdate(); >> int값으로 성공 결과가 반환됨

            System.out.println(rows+" rows updated");

            String selectSql = "SELECT id, name FROM person";
            ResultSet rs = pstmt.executeQuery(selectSql);
            while(rs.next()) {
                jdbcEx01.vo.Person person = new Person();
                person.setId(rs.getInt(1));
                person.setName(rs.getString(2));
                System.out.println(person.getId()+" "+person.getName());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
