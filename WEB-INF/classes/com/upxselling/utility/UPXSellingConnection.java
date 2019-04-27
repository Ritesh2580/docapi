package com.upxselling.utility;
import java.sql.*;
import com.upxselling.exceptions.UPXSellingException;
public class UPXSellingConnection
{
public static Connection getConnection()throws UPXSellingException
{
try
{
Class.forName("com.mysql.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/upxselling","upxselling","upxselling");
return connection;
}
catch(Exception e)
{
e.printStackTrace();
return null;
}

}
}