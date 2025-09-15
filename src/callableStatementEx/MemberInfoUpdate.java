package callableStatementEx;

import util.DBUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberInfoUpdate {
    public static void main(String[] args) throws IOException {
        Connection conn = DBUtil.getConnection();
        System.out.print("수정할 아이디 입력 : ");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String m_userid = input.readLine();
        System.out.print("메뉴 입력 1 : 비밀번호를 수정, 2 : 이메일 수정, 3 : 핸드폰 번호 수정");
        String m_menuNumber = input.readLine();
        System.out.print("수정할 내용 입력 : ");
        String m_update_text = input.readLine();
        String sql = "CALL SP_MEMBER_INFO_UPDATE(?,?,?)";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, m_userid);
            cstmt.setString(2, m_menuNumber);
            cstmt.setString(3, m_update_text);
            int result = cstmt.executeUpdate();
            if (result > 0) {
                System.out.println("회원정보 업데이트 완료.");
            } else {
                System.out.println("존재하지 않는 아이디입니다.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
