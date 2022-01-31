import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DockerGenerator {
	public static void main(String[] args) throws IOException {
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
FileInputStream fin=new FileInputStream("C:\\Users\\MOSAIF\\BDD\\CucumberJava\\jenkinsprop.properties");
Properties prop=new Properties();
prop.load(fin);
String type=prop.getProperty("type");

System.out.println(type);

String feature=prop.getProperty("Feature_name");

System.out.println(feature);
String scenario=prop.getProperty("Scenario_name");

System.out.println(scenario);
String tags = null;
try {
	
	Class.forName("oracle.jdbc.driver.OracleDriver");  
	Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@Whf00axl:1521:USFEDDB","QAUTIL","QAUTIL");
	Statement stmt=conn.createStatement();  
	//System.out.println("Connected");
	//   
	//step4 execute query  
	ResultSet rs=stmt.executeQuery("select distinct tags from parametertable where type='"+type+"' and feature_name='"+feature+"' and scenario_id='"+scenario+"' ");  
while (rs.next())
{
	System.out.println("inside");
	tags=rs.getString(1);
}
tags=tags.replace(",", " and ");
System.out.println("Tags = "+tags);
}
catch(Exception e)
{
	e.printStackTrace();
}

System.out.println(tags);
FileWriter fout=new FileWriter("C:\\Users\\MOSAIF\\BDD\\CucumberJava\\Dockerfile");
fout.write("#base image of JDK(First Layer)\r\n"
		+ "FROM openjdk\r\n"
		+ "\r\n"
		+ "#Second layer in which the making the directory to run in.\r\n"
		+ "\r\n"
		+ "RUN mkdir /app\r\n"
		+ "\r\n"
		+ "\r\n"
		+ "COPY . /app\r\n"
		+ "\r\n"
		+ "\r\n"
		+ "WORKDIR /app/src/test/java/Step\r\n"
		+ "\r\n"
		+ "\r\n"
		+ "RUN javac TestRunner.java\r\n"
		+ "\r\n"
		+ "\r\n"
		+ "# base image for Maven Repositories\r\n"
		+ "\r\n"
		+ "From maven\r\n"
		+ "\r\n"
		+ "COPY . /app\r\n"
		+ "\r\n"
		+ "WORKDIR /app\r\n"
		+ "\r\n"
		+ "\r\n"
		+ "CMD mvn test -Dcucumber.filter.tags=\""+tags+"\"")  ;
fout.close();
	}

}
