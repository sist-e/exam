package com.bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bank.vo.Member;

public class MemberDAO {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@211.238.142.130:1521:orcl";
	private String user = "scott";
	private String pwd ="111111";

	public Connection getConn(){
		Connection con=null;
		try {
			Class.forName(driver); //����̺� �ε� 
		} catch (ClassNotFoundException e) { // �ε��� Ŭ������ ������ 
			System.out.println("����̹� �ε忡 �����Ͽ����ϴ�.");
			e.printStackTrace();
		}

		try {
			//�����ϰ��� class member�� ���� member���̺� �־��� ��.
			con = DriverManager.getConnection(url,user, pwd);
		} catch (SQLException e) {
			System.out.println("���ῡ�� �����Ͽ����ϴ�.");
			e.printStackTrace();
		} 

		return con;
	}

	public Member getMember(String mid) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Member member = null;

		String sql = "SELECT * FROM MEMBERS WHERE MID = ?";
		con = getConn();
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, mid);
			rs = ps.executeQuery();
			if(rs.next()) {
				member = new Member();
				member.setMid(rs.getString("mid"));
				member.setPwd(rs.getString("pwd"));
				member.setName(rs.getString("name"));
				member.setGender(rs.getString("gender"));
				member.setAge(rs.getShort("age"));
				member.setBirthday(rs.getString("birthday"));
				member.setPhone(rs.getString("phone"));
				member.setRegdate(rs.getString("regdate"));

			}

		} catch (SQLException e) {
			System.out.println("ȸ����ȸ �߿� ���� �߻�");
			e.printStackTrace();
		} finally {//���ܰ� �߻��ص� ������ ����
			//���ܹ߻��ص� �ݾ�����Ѵ�
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("�������� ����");
				e.printStackTrace();
			}
		}

		return member;
	}

	public List<Member> getMembers(String col) 
	{
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Member m = null;

		String sql = "SELECT * FROM MEMBERS ORDER BY " + col;

		List<Member> list = new ArrayList<Member>();
		con = getConn();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);

			while(rs.next())
			{
				m = new Member();            //Members�� ���� �ִ� ����
				m.setMid(rs.getString("mid"));
				m.setPwd(rs.getString("pwd"));
				m.setName(rs.getString("name"));
				m.setGender(rs.getString("gender"));
				m.setAge(rs.getShort("age"));
				m.setBirthday(rs.getString("birthday"));
				m.setPhone(rs.getString("phone"));
				m.setRegdate(rs.getString("regdate"));

				list.add(m);
			}
		} catch (SQLException e) {
			System.out.println("ȸ����ȸ�߿� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		} finally {   //������ �����ϴ� ����ó��
			try {
				rs.close();
				st.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("���������� �����Ͽ����ϴ�.");
				e.printStackTrace();
			}
		}
		return list;

	}

	public void addMember(Member member) {
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "INSERT INTO MEMBERS(MID, PWD, NAME, GENDER, AGE, BIRTHDAY, PHONE, REGDATE)"
				+ " VALUES (?,?,?,?,?,?,?,SYSDATE)";
		con = getConn();

		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, member.getMid());
			ps.setString(2, member.getPwd());
			ps.setString(3, member.getName());
			ps.setString(4, member.getGender());
			ps.setInt(5, member.getAge());
			ps.setString(6, member.getBirthday());
			ps.setString(7, member.getPhone());

			int af = ps.executeUpdate();
			if(af ==1) {
				System.out.println("ȸ�����Կ� �����߽��ϴ�.");
			}

			ps.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("ȸ������ �� �����߻�");			
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("�������� ����");
				e.printStackTrace();
			}
		}


	}


	public void UpdateMember(Member m) 
	{
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "UPDATE MEMBERS SET GENDER=?, BIRTHDAY=?, PHONE=? WHERE MID=?";
		con = getConn();

		try 
		{
			ps = con.prepareStatement(sql); //ps���ٰ� sql������ �־���
			ps.setString(1, m.getGender());
			ps.setString(2, m.getBirthday());
			ps.setString(3, m.getPhone());
			ps.setString(4, m.getMid());

			int af = ps.executeUpdate(); //������Ʈ �ؾ� ���� ��
			if (af == 1) 
			{
				System.out.println("-> ȸ�� ������Ʈ�� �����Ͽ����ϴ�.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {   //������ �����ϴ� ����ó��
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("���������� �����Ͽ����ϴ�.");
				e.printStackTrace();
			}
		}      
	}

	public void delMember(Member m) 
	{
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "DELETE MEMBERS WHERE MID=?";
		con = getConn();

		try 
		{
			ps = con.prepareStatement(sql); //ps���ٰ� sql������ �־���
			ps.setString(1, m.getMid());

			int af = ps.executeUpdate();    //������Ʈ �ؾ� ���� ��
			if (af == 1) 
			{
				System.out.println("-> ȸ�� ������ �����Ͽ����ϴ�.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {   //������ �����ϴ� ����ó��
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("���������� �����Ͽ����ϴ�.");
				e.printStackTrace();
			}
		}      
	}

}
