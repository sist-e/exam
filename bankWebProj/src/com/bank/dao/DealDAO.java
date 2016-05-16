package com.bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.vo.Deal;

public class DealDAO {
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

	public Deal searchDeal(String accountNum) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Deal deal = null;

		String sql = "SELECT BALANCE FROM (SELECT * FROM DEALS ORDER BY DEALDATE DESC) WHERE ROWNUM = 1 AND FACCOUNTNUM = ?";
		con = getConn();
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, accountNum);
			rs = ps.executeQuery();
			if(rs.next()) {
				deal = new Deal();
				deal.setBalance(rs.getInt("balance"));	
			}

		} catch (SQLException e) {
			System.out.println("거래내역조회중에 오류 발생");
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
		
		return deal;

	}

	public int insertDeal(Deal deal) {
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "INSERT INTO DEALS(DEALNUM, KIND, CONTENT, AMOUNT, BALANCE, DEALDATE, FACCOUNTNUM)"
				+ " VALUES ((SELECT NVL(MAX(TO_NUMBER(DEALNUM))+1,1) FROM DEALS),?,?,?,?,SYSDATE,?)";
		con = getConn();

		int af=0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, deal.getKind());
			ps.setString(2, deal.getContent());
			ps.setInt(3, deal.getAmount());
			ps.setInt(4, deal.getBalance());
			ps.setString(5, deal.getFaccountNum());

			af = ps.executeUpdate();
			if(af ==1) {
				System.out.println("거래내역 생성에 성공했습니다.");
			}

			ps.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("거래 중 오류발생");			
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

	public List<Deal> getDeals(String accountNum) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Deal> list = null; //어레이리스트로 작성

		String sql = "SELECT * FROM DEALS WHERE FACCOUNTNUM = ? ORDER BY DEALDATE";
		con = getConn();
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, accountNum);
			rs = ps.executeQuery();
			
			list = new ArrayList<Deal>();
			while(rs.next()) {
				Deal deal = new Deal();
				deal.setDealNum(rs.getString("dealNum"));
				deal.setKind(rs.getString("kind"));
				deal.setContent(rs.getString("content"));
				deal.setAmount(rs.getInt("amount"));
				deal.setBalance(rs.getInt("balance"));	
				deal.setDealdate(rs.getString("dealdate"));
				deal.setFaccountNum(rs.getString("faccountNum"));
				
				list.add(deal);
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

	public int transferDeal(Deal deal, Deal trdeal) {
		Connection con = null;
		PreparedStatement ps = null;

		//DEALNUM이 VARCHAR2라서 TO_NUMBER(DEALNUM)로 숫자로 바꿔줘야함
		String sql = "INSERT INTO DEALS(DEALNUM, KIND, CONTENT, AMOUNT, BALANCE, DEALDATE, FACCOUNTNUM)"
				+ " VALUES ((SELECT NVL(MAX(TO_NUMBER(DEALNUM))+1,1) FROM DEALS),?,?,?,?,SYSDATE,?)";
		con = getConn();
		int af =0;
		try {
			con.setAutoCommit(false); //오토커밋해제
			
			ps = con.prepareStatement(sql);
			ps.setString(1, deal.getKind());
			ps.setString(2, deal.getContent());
			ps.setInt(3, deal.getAmount());
			ps.setInt(4, deal.getBalance());
			ps.setString(5, deal.getFaccountNum());
			af = ps.executeUpdate();
			
			ps.setString(1, trdeal.getKind());
			ps.setString(2, trdeal.getContent());
			ps.setInt(3, trdeal.getAmount());
			ps.setInt(4, trdeal.getBalance());
			ps.setString(5, trdeal.getFaccountNum());
			af = af + ps.executeUpdate();	
			if(af == 2) {
				System.out.println("거래내역 생성에 성공했습니다.");
			}
			con.commit();
			
			ps.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("거래 중 오류발생");			
			e.printStackTrace();
			try {
				System.out.println("거래를 전부 롤백합니다.");
				con.rollback(); // 예외 발생시 롤백처리
			} catch (Exception e1) {
				System.out.println("오류발생 후 롤백 에러");
			} 

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
}
