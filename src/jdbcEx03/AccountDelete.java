package jdbcEx03;

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDelete {
    public static void main(String[] args) {
        Connection conn = DBUtil.getConnection();

        String sql = "DELETE FROM accounts WHERE ano = ?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,"1112-1213-3232-12122");
            int rows = pstmt.executeUpdate();
            System.out.println(rows+" row successfully deleted");

            String selectSql = "SELECT * FROM accounts";
            ResultSet rs = pstmt.executeQuery(selectSql);
            while(rs.next()) {
                jdbcEx03.vo.Account account = new jdbcEx03.vo.Account();
                account.setAno(rs.getString(1));
                account.setOwner(rs.getString(2));
                account.setBalance(rs.getBigDecimal(3));
                System.out.println("예금주 : "+account.getOwner()+"  계좌번호 : "+account.getAno());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
