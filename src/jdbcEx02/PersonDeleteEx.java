package jdbc.jdbcEx02;


import jdbc.jdbcEx01.vo.Person;
import jdbc.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
JDBC를 이용하여 데이터삭제를 하는 DELETE문을 실행하는 방법
DELETE FROM 테이블명;   해당 테이블의 모든 행을 지운다.
DELETE FROM 테이블명 WHERE id(PK) = 식별값;       >> 해당 식별값의 행만 지울수 있다.
DELETE FROM person WHERE num = 1;              >> person 테이블의 pk num = 1 행을 삭제한다.
String sql = "DELETE FROM person WHERE num = ?";
*/
public class PersonDeleteEx {
    public static void main(String[] args) {
        Connection conn = DBUtil.getConnection();
        
        // SQL문 작성
        String sql = "DELETE FROM person WHERE num = ?";
        
        // PreparedStatement 생성하고 값 지정
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);
            int rows = pstmt.executeUpdate();
            System.out.println(rows+" rows deleted");

            String selectSql = "SELECT id, name FROM person";
            ResultSet rs = pstmt.executeQuery(selectSql);
            while(rs.next()) {
                jdbc.jdbcEx01.vo.Person person = new Person();
                person.setId(rs.getInt(1));
                person.setName(rs.getString(2));
                System.out.println(person.getId()+" "+person.getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
