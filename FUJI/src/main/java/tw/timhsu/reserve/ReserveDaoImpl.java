package tw.timhsu.reserve;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import tw.timhsu.reserve.ReserveDetail;

public class ReserveDaoImpl implements ReserveDao{
	@Autowired
	DataSource datasource;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public int reserve(ReserveDetail reservedetail) {

		String sql = "insert into Reserve values(?,?,?,?,?,?,?)";

		return jdbcTemplate.update(sql, new Object[] { reservedetail.getReserveid(), reservedetail.getReservename(), reservedetail.getPhone(),
				reservedetail.getBirthmonth(), reservedetail.getBirthdate(), reservedetail.getPeriod(), reservedetail.getTotal() });
	}
	
	/*
	public int reserve(ReserveDetail reservedetail) {

		String sql = "insert into Reserve values(?,?,?,?,?,?)";

		return jdbcTemplate.update(sql, new Object[] {  reservedetail.getReservename(), reservedetail.getPhone(),
				reservedetail.getBirthmonth(), reservedetail.getBirthdate(), reservedetail.getPeriod(), reservedetail.getTotal() });
	}*/
	
	

}

class ReserveMapper implements RowMapper<ReserveDetail> {

	public ReserveDetail mapRow(ResultSet rs, int arg1) throws SQLException {
		ReserveDetail reservedetail = new ReserveDetail();

		reservedetail.setReserveid(rs.getInt("reserveid"));
		reservedetail.setReservename(rs.getString("reservename"));
		reservedetail.setPhone(rs.getString("phone"));
		reservedetail.setBirthmonth(rs.getInt("birthmonth"));
		reservedetail.setBirthdate(rs.getInt("birthdate"));
		reservedetail.setPeriod(rs.getString("period"));
		reservedetail.setTotal(rs.getInt("total"));

		return reservedetail;
	}
}
