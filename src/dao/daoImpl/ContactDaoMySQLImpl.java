package dao.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dao.ContactDao;
import entity.Contact;
import util.JDBCUtil;

public class ContactDaoMySQLImpl implements ContactDao {

	@Override
	public void addContact(Contact contact) {

		Connection conn = null;
		PreparedStatement pstat = null;

		try {
			conn = JDBCUtil.getConnection();
			String sql = "insert into contact(id,name,gender,age,qq,email) values(?,?,?,?,?,?)";
			pstat = conn.prepareStatement(sql);
			String id = UUID.randomUUID().toString().replace("-", "");
			pstat.setString(1, id);
			pstat.setString(2, contact.getName());
			pstat.setString(3, contact.getGender());
			pstat.setString(4, contact.getAge());
			pstat.setString(5, contact.getQq());
			pstat.setString(6, contact.getEmail());
			pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(conn, pstat);
		}

	}

	@Override
	public void updateContact(Contact contact) {

		Connection conn = null;
		PreparedStatement pstat = null;

		try {
			conn = JDBCUtil.getConnection();
			String sql = "update contact set name=?,gender=?,age=?,email=?,qq=? where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(6, contact.getId());
			pstat.setString(1, contact.getName());
			pstat.setString(2, contact.getGender());
			pstat.setString(3, contact.getAge());
			pstat.setString(5, contact.getQq());
			pstat.setString(4, contact.getEmail());
			pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(conn, pstat);
		}

	}

	@Override
	public void deleteContactById(String id) {

		Connection conn = null;
		PreparedStatement pstat = null;

		try {
			conn = JDBCUtil.getConnection();
			String sql = "delete from contact where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(conn, pstat);
		}
	}

	@Override
	public List<Contact> findAllContact() {

		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;

		try {
			conn = JDBCUtil.getConnection();
			String sql = "select * from contact";
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			List<Contact> list = new ArrayList<Contact>();
			while (rs.next()) {
				Contact c = new Contact();
				c.setId(rs.getString("id"));
				c.setName(rs.getString("name"));
				c.setAge(rs.getString("age"));
				c.setGender(rs.getString("gender"));
				c.setQq(rs.getString("qq"));
				c.setEmail(rs.getString("email"));
				list.add(c);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(conn, pstat);
		}
	}

	@Override
	public Contact findById(String id) {
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConnection();
			String sql = "select * from contact where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			rs = pstat.executeQuery();
			Contact c = null;
			if (rs.next()) {
				c = new Contact();
				c.setId(rs.getString("id"));
				c.setName(rs.getString("name"));
				c.setGender(rs.getString("gender"));
				c.setAge(rs.getString("age"));
				c.setEmail(rs.getString("email"));
				c.setQq(rs.getString("qq"));
			}
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(conn, pstat);
		}
	}

	@Override
	public boolean isExistByName(String name) {
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConnection();
			String sql = "select * from contact where name=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, name);
			rs = pstat.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(conn, pstat);
		}
	}

}
