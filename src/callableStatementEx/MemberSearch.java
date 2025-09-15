package callableStatementEx;

import util.DBUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberSearch {
    public static void main(String[] args) throws IOException {
        Connection conn = DBUtil.getConnection();
        System.out.print("검색할 아이디 입력 : ");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String m_userid = input.readLine();
        String sql = "CALL SP_MEMBER_SEARCH(?)";
        try(CallableStatement cstmt = conn.prepareCall(sql)){
            cstmt.setString(1, m_userid);
            ResultSet rs = cstmt.executeQuery();
            if(rs.next()){
                System.out.printf("%-3s || %-12s || %-6s || %-25s || %-15s || %-20s || %-3s%n",
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                );
            } else {
                System.out.println("존재하지 않는 사용자입니다.");
                return;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
