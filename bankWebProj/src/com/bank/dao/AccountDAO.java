package com.bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.vo.Account;

public class AccountDAO {
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

	//리스트로 고치기 전부다 뜰수 있게.
	public List<Account> getAccounts(String mid) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Account> list = null; //어레이리스트로 작성

		String sql = "SELECT * FROM ACCOUNTS WHERE OWNER = ?";
		con = getConn();
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, mid);
			rs = ps.executeQuery();
			
			list = new ArrayList<Account>();
			while(rs.next()) {
				Account account = new Account();
				account.setAccountNum(rs.getString("accountnum"));
				account.setPwd(rs.getString("pwd"));
				account.setKind(rs.getString("kind"));
				account.setLocations(rs.getString("locations"));
				account.setRegdate(rs.getString("regdate"));	
				account.setOwner(rs.getString("owner"));
				
				list.add(account);
			}

		} catch (SQLException e) {
			System.out.println("계좌조회중에 오류 발생");
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

		return list;
		
	}

	public int addAccount(Account account) {
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "INSERT INTO ACCOUNTS(ACCOUNTNUM, PWD, KIND, LOCATIONS,REGDATE,OWNER)"
				+ " VALUES (?,?,?,?,SYSDATE,?)";
		con = getConn();
		
		int af=0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, account.getAccountNum());
			ps.setString(2, account.getPwd());
			ps.setString(3, account.getKind());
			ps.setString(4, account.getLocations());
			ps.setString(5, account.getOwner());

			af = ps.executeUpdate();
			if(af ==1) {
				System.out.println("계좌생성에 성공했습니다.");
			}

			ps.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("계좌생성 중 오류발생");			
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
		return af;
	}

	public Account searchAccount(String accountNum) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;

		String sql = "SELECT * FROM ACCOUNTS WHERE ACCOUNTNUM = ?";
		con = getConn();
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, accountNum);
			rs = ps.executeQuery();
			if(rs.next()) {
				account = new Account();
				account.setAccountNum(rs.getString("accountnum"));
				account.setPwd(rs.getString("pwd"));
				account.setKind(rs.getString("kind"));
				account.setLocations(rs.getString("locations"));
				account.setRegdate(rs.getString("regdate"));	
				account.setOwner(rs.getString("owner"));

			}

		} catch (SQLException e) {
			System.out.println("계좌조회중에 오류 발생");
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

		return account;
	}

}
