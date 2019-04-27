package com.upxselling.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.text.*;
import com.google.gson.*;
import com.upxselling.servlets.pojo.*;
import com.upxselling.utility.*;

public class AddPatient extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
ResponseWrapper responseWrapper;
PrintWriter pw=null;
try
{
pw=response.getWriter();
response.setContentType("application/json");
InputStream is=request.getInputStream();
InputStreamReader isr=new InputStreamReader(is);
StringBuilder sb=new StringBuilder();
int x;
while(true)
{
x=isr.read();
if(x==-1) break;
sb.append((char)x);
}
String jsonString=sb.toString();
Gson gson=new Gson();
Patient patient=gson.fromJson(jsonString,Patient.class);
System.out.println(patient.getName()+" "+patient.getAge()+" "+patient.getEmail());
Connection connection=UPXSellingConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select id from patient where upper(name)=?");
preparedStatement.setString(1,patient.getName().toUpperCase().trim());
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new ServletException("Patient : "+patient.getName()+" exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into patient (name,age,email) values(?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,patient.getName());
preparedStatement.setInt(2,patient.getAge());
preparedStatement.setString(3,patient.getEmail());
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
patient.setId(resultSet.getInt(1));
resultSet.close();
preparedStatement.close();
connection.close();
responseWrapper=new ResponseWrapper();
responseWrapper.setResponse(patient);
pw.print(responseWrapper);
String message="Name : "+patient.getName()+"  "+"Age : "+patient.getAge()+"  "+"email :"+patient.getEmail();
String to[]={ patient.getEmail()};
sendEmail("admin@upxselling.com",to,"Feedback email",message);
}catch(Exception exception)
{
responseWrapper=new ResponseWrapper();
responseWrapper.setException(exception.getMessage());
pw.print(responseWrapper);
}
}

public void sendEmail(String from,String []to,String subject,String message)
{
PostMail pm=new PostMail();
pm.postMail(from,to,subject,message);
System.out.println("Email Sent");
return;
}


}
