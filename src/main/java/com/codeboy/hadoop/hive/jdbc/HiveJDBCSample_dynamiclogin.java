package com.codeboy.hadoop.hive.jdbc;

import java.io.IOException;
import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.hadoop.security.UserGroupInformation;

public class HiveJDBCSample_dynamiclogin {

 
	// !connect
	// jdbc:hive2://hdp.alpinenow.local:10000/;principal=hive/hdp.alpinenow.local@ALPINENOW.LOCAL

	private static String driverName = "org.apache.hive.jdbc.HiveDriver";

	/**
	 * @param args
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static void main(String[] args) throws SQLException, IOException {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("login");

		org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
		conf.set("hadoop.security.authentication", "Kerberos");
		UserGroupInformation.setConfiguration(conf);

		System.setProperty("java.security.krb5.realm", "ALPINENOW.LOCAL");
		System.setProperty("java.security.krb5.kdc", "kerberos.alpinenow.local");

		UserGroupInformation ugi = UserGroupInformation
				.loginUserFromKeytabAndReturnUGI(HiveJDBCSample_kinit.loginPrincipal, HiveJDBCSample_kinit.keyTabLocation);

		PrivilegedAction action = new PrivilegedAction(){

			@Override
			public Object run() {
				Connection conn;
				try {
					conn = DriverManager				.getConnection(HiveJDBCSample_kinit.JDBC_URL);
					System.out.println("login OK ======");

					printSomething(conn);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		}; 
		ugi.doAs(action) ;
		

	}

	private static void printSomething(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();

		System.out.println("HiveClient2.main show table " + HiveJDBCSample_kinit.tableName);
		String sql = "show tables '" + HiveJDBCSample_kinit.tableName + "'";
		System.out.println("Running: " + sql);
		ResultSet res = stmt.executeQuery(sql);
		if (res.next()) {
			System.out.println(res.getString(1));
		}

	}
}
