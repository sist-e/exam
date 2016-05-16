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
			System.out.println("�ŷ�������ȸ�߿� ���� �߻�");
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
				System.out.println("�ŷ����� ������ �����߽��ϴ�.");
			}

			ps.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("�ŷ� �� �����߻�");			
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
		return af;
		
	}

	public List<Deal> getDeals(String accountNum) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Deal> list = null; //��̸���Ʈ�� �ۼ�

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
			System.out.println("������ȸ�߿� ���� �߻�");
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

		return list;
	}

	public int transferDeal(Deal deal, Deal trdeal) {
		Connection con = null;
		PreparedStatement ps = null;

		//DEALNUM�� VARCHAR2�� TO_NUMBER(DEALNUM)�� ���ڷ� �ٲ������
		String sql = "INSERT INTO DEALS(DEALNUM, KIND, CONTENT, AMOUNT, BALANCE, DEALDATE, FACCOUNTNUM)"
				+ " VALUES ((SELECT NVL(MAX(TO_NUMBER(DEALNUM))+1,1) FROM DEALS),?,?,?,?,SYSDATE,?)";
		con = getConn();
		int af =0;
		try {
			con.setAutoCommit(false); //����Ŀ������
			
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
				System.out.println("�ŷ����� ������ �����߽��ϴ�.");
			}
			con.commit();
			
			ps.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("�ŷ� �� �����߻�");			
			e.printStackTrace();
			try {
				System.out.println("�ŷ��� ���� �ѹ��մϴ�.");
				con.rollback(); // ���� �߻��� �ѹ�ó��
			} catch (Exception e1) {
				System.out.println("�����߻� �� �ѹ� ����");
			} 

		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("�������� ����");
				e.printStackTrace();
			}
		}
		
		return af;
	}
}
