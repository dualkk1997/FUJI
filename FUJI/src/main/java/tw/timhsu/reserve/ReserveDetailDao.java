package tw.timhsu.reserve;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;


import tw.timhsu.reserve.ReserveDetail;
@Service
@Transactional
public class ReserveDetailDao {
	
	@Autowired
	JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public int save(ReserveDetail p) {
		String sql = "insert into Reserve(reservename,phone,birthMonth,birthDate,period,total) values('"
				+ p.getReservename() + "','" + p.getPhone() + "','" + p.getBirthmonth() + "','"
				+ p.getBirthdate() + "','" + p.getPeriod() + "','" + p.getTotal() + "')";
		return template.update(sql);
	}
	
	
	

	public int update(ReserveDetail p) {
		String sql = "update Reserve set  reservename='" + p.getReservename()
				+ "',phone='" + p.getPhone()+ "',birthmonth='" + p.getBirthmonth() + "', birthdate='" + p.getBirthdate()
				+ "',period='" + p.getPeriod() + "', total='" + p.getTotal() + "' where reserveid='"
				+ p.getReserveid() + "'";
		return template.update(sql);
	}
	
	/*
	public int update(ReserveDetail p) {
		String sql = "update Reserve set reserveid='" + p.getReserveid() +"', reservename='" + p.getReservename()
				+ "',phone='" + p.getPhone()+ "',birthmonth='" + p.getBirthmonth() + "', birthdate='" + p.getBirthdate()
				+ "',period='" + p.getPeriod() + "', total='" + p.getTotal() + "' where reserveid='"
				+ p.getReserveid() + "'";
		return template.update(sql);
	}*/
	
	
	

	public int delete(int reserveid) {
		String sql = "delete from Reserve where reserveid='" + reserveid + "'";
		return template.update(sql);
	}

	public ReserveDetail getReserveDetailByReserveId(int reserveid) {
		String sql = "select * from Reserve where reserveid=?";
		return template.queryForObject(sql, new Object[] { reserveid },
				new BeanPropertyRowMapper<ReserveDetail>(ReserveDetail.class));
	}
	
	

	public List<ReserveDetail> getReserves() {
		return template.query("select * from Reserve", new RowMapper<ReserveDetail>() {
			public ReserveDetail mapRow(ResultSet rs, int row) throws SQLException {
				ReserveDetail e = new ReserveDetail();
				e.setReserveid(rs.getInt(1));
				e.setReservename(rs.getString(2));
				e.setPhone(rs.getString(3));
				e.setBirthmonth(rs.getInt(4));
				e.setBirthdate(rs.getInt(5));
				e.setPeriod(rs.getString(6));
				e.setTotal(rs.getInt(7));
				return e;
			}
		});
	}
	
	
	
	
	public List<ReserveDetail> getReservesInDay(int month,int day) {
		return template.query("select * from Reserve where birthmonth ="+month +" and birthdate = "+day, new RowMapper<ReserveDetail>() {
			public ReserveDetail mapRow(ResultSet rs, int row) throws SQLException {
				ReserveDetail e = new ReserveDetail();
				e.setReserveid(rs.getInt(1));
				e.setReservename(rs.getString(2));
				e.setPhone(rs.getString(3));
				e.setBirthmonth(rs.getInt(4));
				e.setBirthdate(rs.getInt(5));
				e.setPeriod(rs.getString(6));
				e.setTotal(rs.getInt(7));
				return e;
			}
		});
	}
}
