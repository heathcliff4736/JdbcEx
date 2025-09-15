package callableStatementEx;

import jdbc_boards.util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberList {
    public static void main(String[] args) {
        Connection conn = DBUtil.getConnection();

        String sql = "CALL SP_MEMBER_LIST()";

        try(CallableStatement call = conn.prepareCall(sql)) {
            try (ResultSet rs = call.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("%-3s || %-12s || %-12s || %-25s || %-15s || %-20s || %-3s%n",
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7)
                    );
                }
            }
            call.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
