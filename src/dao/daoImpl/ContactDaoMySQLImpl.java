package dao.daoImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import dao.ContactDao;
import entity.Contact;
import util.JDBCUtil;

public class ContactDaoMySQLImpl implements ContactDao {

	Connection conn = null;
	private QueryRunner qr = new QueryRunner();
	@Override
	public void addContact(Contact contact) {

		
		try {
			conn = JDBCUtil.getConnection();
			String sql = "insert into contact(id,name,gender,age,qq,email) values(?,?,?,?,?,?)";
			String id = UUID.randomUUID().toString().replace("-", "");
			qr.update(conn, sql, id, contact.getName(), contact.getGender(), contact.getAge(), contact.getQq(), contact.getEmail());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(conn, null);
		}

	}

	@Override
	public void updateContact(Contact contact) {


		try {
			conn = JDBCUtil.getConnection();
			String sql = "update contact set name=?,gender=?,age=?,email=?,qq=? where id=?";
			qr.update(conn, sql, contact.getName(), contact.getGender(), contact.getAge(), contact.getEmail(), contact.getQq(), contact.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(conn, null);
		}

	}

	@Override
	public void deleteContactById(String id) {

		try {
			conn = JDBCUtil.getConnection();
			String sql = "delete from contact where id=?";
			
			qr.update(conn, sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(conn, null);
		}
	}

	@Override
	public List<Contact> findAllContact() {

		try {
			conn = JDBCUtil.getConnection();
			String sql = "select * from contact";
			List<Contact> list = qr.query(conn, sql, new BeanListHandler<Contact>(Contact.class));
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(conn, null);
		}
	}

	@Override
	public Contact findById(String id) {
		try {
			conn = JDBCUtil.getConnection();
			String sql = "select * from contact where id=?";
			Contact c = qr.query(conn, sql, new BeanHandler<Contact>(Contact.class), id);
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(conn, null);
		}
	}

	@Override
	public boolean isExistByName(String name) {
		try {
			conn = JDBCUtil.getConnection();
			String sql = "select * from contact where name=?";
			String str = qr.query(conn, sql, new ScalarHandler<String>(), name);
			if (str!=null) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(conn, null);
		}
	}

}
