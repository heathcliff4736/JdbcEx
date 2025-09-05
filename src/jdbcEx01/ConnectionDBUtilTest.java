package jdbcEx01;

import jdbcEx01.vo.Person;
import util.DBUtil;

import java.sql.*;

public class ConnectionDBUtilTest {
    public static void main(String[] args) {
        try {
            Connection con = DBUtil.getConnection();

            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate("INSERT INTO person(id,name) values(123,'김관장') ");
            if (result == 1) {
                System.out.println("Insert success");
            }

            String selectSql = "SELECT id, name FROM person";
            ResultSet rs = stmt.executeQuery(selectSql);
            while(rs.next()) {
                jdbcEx01.vo.Person person = new Person();
                person.setId(rs.getInt(1));
                person.setName(rs.getString(2));
                System.out.println(person.toString());
            }
        } catch (Exception e) {
            System.out.println("Connection established" + e.getMessage());
        }
    }
}
