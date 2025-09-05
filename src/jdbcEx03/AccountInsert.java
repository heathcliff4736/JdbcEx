package jdbcEx03;

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountInsert {
    public static void main(String[] args) {
        Connection conn = DBUtil.getConnection();

        String sql = "INSERT INTO accounts(ano,owner,balance) values(?,?,?)";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,"110-000-042-024");
            pstmt.setString(2,"김현식");
            pstmt.setString(3,"1000000");
            int rows = pstmt.executeUpdate();
            System.out.println(rows+" row successfully inserted");

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
