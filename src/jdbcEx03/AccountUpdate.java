package jdbcEx03;

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountUpdate {
    public static void main(String[] args) {
        Connection conn = DBUtil.getConnection();

        String sql = new StringBuilder()
                .append("UPDATE accounts SET ")
                .append("ano = ?, ")
                .append("owner = ?, ")
                .append("balance = ? ")
                .append("WHERE ano = ?").toString();

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,"999-999-999-999");
            pstmt.setString(2,"비둘기");
            pstmt.setString(3,"9999999999");
            pstmt.setString(4,"110-000-002-023");
            int rows = pstmt.executeUpdate();
            System.out.println(rows+" row successfully updated");

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
