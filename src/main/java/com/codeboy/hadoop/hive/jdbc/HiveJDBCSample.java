package com.codeboy.hadoop.hive.jdbc;

import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.hadoop.security.UserGroupInformation;


public class HiveJDBCSample  {
 
	  static final String loginPrincipal = "hive/hadoop.alpinenow.local@ALPINENOW.LOCAL";
	  static final String keyTabLocation = "/Users/zhaoyong/hive.keytab";

    
    	  private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    	    /**
    	     * @param args
    	     * @throws SQLException
    	     */
    	    public static void main(String[] args) throws SQLException {
    	        try {
    	            Class.forName(driverName);
    	        } catch (ClassNotFoundException e) {
    	            // TODO Auto-generated catch block
    	            e.printStackTrace();
    	            System.exit(1);
    	        }

    	      
     
    	        System.out.println("login");

     	        try {
      	        	org.apache.hadoop.conf.Configuration conf = new     
     	        	org.apache.hadoop.conf.Configuration();
     	        	conf.set("hadoop.security.authentication", "Kerberos");
     	        	UserGroupInformation.setConfiguration(conf);
    	          UserGroupInformation ugi= UserGroupInformation.loginUserFromKeytabAndReturnUGI(loginPrincipal, keyTabLocation);



     	         UserGroupInformation proxyUser = ugi.createProxyUser("hive", ugi);  
     	        PrivilegedAction action = new PrivilegedAction(){

					@Override
					public Object run() {
		    	        Connection con = null;
						try {
							con = DriverManager.getConnection("jdbc:hive2://localhost:10000/default;principal=" + loginPrincipal);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		      	        System.out.println("login OK");
						return con;
					}
     	        	
     	        }; 
				proxyUser.doAs(action);
//    	          subject =ugi.getSubject();
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }


    	        //replace "hive" here with the name of the user the queries should run as
    	        //Connection con = DriverManager.getConnection("jdbc:hive2://10.0.0.195:10000/default", "hive", "");
    	        //Connection con = DriverManager.getConnection("jdbc:hive2://172.16.0.3:10000/default", "hive", "");
 //    	        System.setProperty("java.security.auth.login.config","gss-jaas.conf");
//    	        System.setProperty("sun.security.jgss.debug","true");
//    	        System.setProperty("javax.security.auth.useSubjectCredsOnly","false");
//    	        System.setProperty("java.security.krb5.conf","/etc/krb5.conf");
//
//    	        System.setProperty("java.security.krb5.conf","/etc/krb5.conf");
//    	        System.setProperty("java.security.krb5.conf","/etc/krb5.conf");
//    	        java.security.krb5.realm=ALPINENOW.LOCAL -Djava.security.krb5.kdc=172.16.0.97

 //    	         Connection con = DriverManager.getConnection("jdbc:hive2://10.0.0.195:10000/default;principal=hive/cdh5hakerberoscm.alpinenow.local@ALPINE;auth=kerberos;kerberosAuthType=fromSubject", "hive", "");
//    	        Statement stmt = con.createStatement();
//    	        String tableName = "default.credit";
//    	/**
//    	 * CREATE TABLE credit (id int, times90dayslate int, revolving_util DOUBLE, debt_ratio DOUBLE, credit_lines int, monthly_income DOUBLE, times30dayslate_2years int, srsdlqncy int) ROW FORMAT  delimited fields terminated by ',' stored as textfile;
//
//    	 LOAD DATA INPATH '/datasets/credit2.csv' OVERWRITE INTO TABLE credit;
//    	 */
//    	/*
//
//
//    	        System.out.println("HiveClient2.main drop table " + tableName);
//    	        stmt.execute("drop table if exists " + tableName);
//    	        System.out.println("HiveClient2.main create table " + tableName);
//    	        stmt.execute("create table " + tableName + " (key int, value string)");
//    	        // show tables
//    	        System.out.println("HiveClient2.main show table " + tableName);
//    	        String sql = "show tables '" + tableName + "'";
//    	        System.out.println("Running: " + sql);
//    	        ResultSet res = stmt.executeQuery(sql);
//    	        if (res.next()) {
//    	            System.out.println(res.getString(1));
//    	        }
//    	*/
//    	        //HiveConnection x = HiveConnection();
//    	        // describe table
//    	        String sql = "describe " + tableName;
//    	        System.out.println("Running: " + sql);
//    	        ResultSet res = stmt.executeQuery(sql);
//    	        while (res.next()) {
//    	            System.out.println(res.getString(1) + "\t" + res.getString(2));
//    	        }
//
//    	        // load data into table
//    	        // NOTE: filepath has to be local to the hive server
//    	        // NOTE: /tmp/a.txt is a ctrl-A separated file with two fields per line
//    	/*
//    	        String filepath = "/tmp/a.txt";
//    	        sql = "load data local inpath '" + filepath + "' into table " + tableName;
//    	        System.out.println("Running: " + sql);
//    	        stmt.execute(sql);
//    	*/
//
//    	        // select * query
//    	        sql = "select * from " + tableName;
//    	        System.out.println("Running: " + sql);
//    	        res = stmt.executeQuery(sql);
//    	        while (res.next()) {
//    	            System.out.println(String.valueOf(res.getString(1)) + "\t" + res.getString(2));
//    	        }
//
//    	        // regular hive query
//    	        sql = "select count(1) from " + tableName;
//    	        System.out.println("Running: " + sql);
//    	        res = stmt.executeQuery(sql);
//    	        while (res.next()) {
//    	            System.out.println(res.getString(1));
//    	        }
      }
}
