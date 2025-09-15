package callableStatementEx;

import util.DBUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class MemberDelete {
    public static void main(String[] args) throws IOException {
        Connection conn = DBUtil.getConnection();
        System.out.print("삭제할 아이디 입력 : ");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String m_userid = input.readLine();
        String sql = "CALL SP_MEMBER_DELETE(?)";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, m_userid);
            int result = cstmt.executeUpdate();
            if (result > 0) {
                System.out.println("회원 삭제 완료.");
            } else {
                System.out.println("존재하지 않는 아이디입니다.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
