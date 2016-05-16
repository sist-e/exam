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
			Class.forName(driver); //드라이브 로드 
		} catch (ClassNotFoundException e) { // 로드한 클래스가 없으면 
			System.out.println("드라이버 로드에 실패하였습니다.");
			e.printStackTrace();
		}

		try {
			//연결하고나서 class member를 만들어서 member테이블에 넣어줄 것.
			con = DriverManager.getConnection(url,user, pwd);
		} catch (SQLException e) {
			System.out.println("연결에에 실패하였습니다.");
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
			System.out.println("회원조회 중에 오류 발생");
			e.printStackTrace();
		} finally {//예외가 발생해도 무조건 실행
			//예외발생해도 닫아줘야한다
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("접속해제 실패");
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
				m = new Member();            //Members에 값을 넣는 과정
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
			System.out.println("회원조회중에 오류가 발생했습니다.");
			e.printStackTrace();
		} finally {   //무조건 실행하는 예외처리
			try {
				rs.close();
				st.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("접속해제에 실패하였습니다.");
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
				System.out.println("회원가입에 성공했습니다.");
			}

			ps.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("회원가입 중 오류발생");			
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("접속해제 실패");
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
			ps = con.prepareStatement(sql); //ps에다가 sql구문을 넣어줌
			ps.setString(1, m.getGender());
			ps.setString(2, m.getBirthday());
			ps.setString(3, m.getPhone());
			ps.setString(4, m.getMid());

			int af = ps.executeUpdate(); //업데이트 해야 적용 됨
			if (af == 1) 
			{
				System.out.println("-> 회원 업데이트에 성공하였습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {   //무조건 실행하는 예외처리
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("접속해제에 실패하였습니다.");
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
			ps = con.prepareStatement(sql); //ps에다가 sql구문을 넣어줌
			ps.setString(1, m.getMid());

			int af = ps.executeUpdate();    //업데이트 해야 적용 됨
			if (af == 1) 
			{
				System.out.println("-> 회원 삭제에 성공하였습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {   //무조건 실행하는 예외처리
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("접속해제에 실패하였습니다.");
				e.printStackTrace();
			}
		}      
	}

}
