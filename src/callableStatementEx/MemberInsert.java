package callableStatementEx;

import util.DBUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class MemberInsert {

    public static void main(String[] args) throws IOException {
        Connection conn = DBUtil.getConnection();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("[신규 회원 등록]");
        System.out.print("ID : ");
        String m_userid = input.readLine();
        System.out.print("비밀번호 : ");
        String m_pwd = input.readLine();
        System.out.print("이메일 : ");
        String m_email = input.readLine();
        System.out.print("핸드폰 번호 : ");
        String m_hp = input.readLine();

        String sql = "{CALL SP_MEMBER_INSERT(?,?,?,?,?)}";

        try(CallableStatement call = conn.prepareCall(sql)){
            //IN 파라미터 세팅
            call.setString(1, m_userid);
            call.setString(2, m_pwd);
            call.setString(3, m_email);
            call.setString(4, m_hp);

            //OUT 파라미터 세팅
            call.registerOutParameter(5, Types.INTEGER);
            // 실행
            call.execute();
            int rtn = call.getInt(5);
            if(rtn == 100){
//                conn.rollback();
                System.out.println("이미 가입된 사용자입니다.");
            } else {
                System.out.println("회원 가입 완료.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
