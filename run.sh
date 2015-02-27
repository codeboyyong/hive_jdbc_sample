mvn clean
mvn package

mvn exec:java  -Dexec.mainClass="com.codeboy.hadoop.hive.jdbc.HiveJDBCSample"  -Djava.security.krb5.realm=ALPINENOW.LOCAL -Djava.security.krb5.kdc=172.16.0.97

 