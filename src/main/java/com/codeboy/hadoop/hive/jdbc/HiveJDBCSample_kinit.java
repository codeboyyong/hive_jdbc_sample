package com.codeboy.hadoop.hive.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Please run kinit first! kinit -k -t /etc/security/keytabs/hive.service.keytab
 * hive/hdp.alpinenow.local@ALPINENOW.LOCAL
 * 
 * @author zhaoyong
 * 
 */
public class HiveJDBCSample_kinit {

	public static final String loginPrincipal = "hive/hdp.alpinenow.local@ALPINENOW.LOCAL";
	public static final String keyTabLocation = "/etc/security/keytabs/hive.service.keytab";
	
	public static final String tableName = "default.credit";
	// !connect
	// jdbc:hive2://hdp.alpinenow.local:10000/;principal=hive/hdp.alpinenow.local@ALPINENOW.LOCAL
	public static final String JDBC_URL = "jdbc:hive2://localhost:10000/;principal="
			+ loginPrincipal;

	public static String driverName = "org.apache.hive.jdbc.HiveDriver";

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws Exception {

		Class.forName(driverName);

		System.out.println("login");

		System.setProperty("java.security.krb5.realm", "ALPINENOW.LOCAL");
		System.setProperty("java.security.krb5.kdc", "kerberos.alpinenow.local");
		Connection conn = DriverManager.getConnection(JDBC_URL);
		System.out.println("login OK ======");
		printSomething(conn);

	}

	private static void printSomething(Connection con) throws SQLException {
		Statement stmt = con.createStatement();

		System.out.println("HiveClient2.main show table " + tableName);
		String sql = "show tables '" + tableName + "'";
		System.out.println("Running: " + sql);
		ResultSet res = stmt.executeQuery(sql);
		if (res.next()) {
			System.out.println(res.getString(1));
		}

		// HiveConnection x = HiveConnection();
		// describe table
		sql = "describe " + tableName;
		System.out.println("Running: " + sql);
		res = stmt.executeQuery(sql);
		while (res.next()) {
			System.out.println(res.getString(1) + "\t" + res.getString(2));
		}

		// load data into table
		// NOTE: filepath has to be local to the hive server
		// NOTE: /tmp/a.txt is a ctrl-A separated file with two fields per line
		/*
		 * String filepath = "/tmp/a.txt"; sql = "load data local inpath '" +
		 * filepath + "' into table " + tableName;
		 * System.out.println("Running: " + sql); stmt.execute(sql);
		 */

		// select * query
		sql = "select * from " + tableName;
		System.out.println("Running: " + sql);
		res = stmt.executeQuery(sql);
		while (res.next()) {
			System.out.println(String.valueOf(res.getString(1)) + "\t"
					+ res.getString(2));
		}

		// regular hive query
		sql = "select count(1) from " + tableName;
		System.out.println("Running: " + sql);
		res = stmt.executeQuery(sql);
		while (res.next()) {
			System.out.println(res.getString(1));
		}

	}
}
