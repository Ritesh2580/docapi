package com.upxselling.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.text.*;
import com.google.gson.*;
import com.upxselling.servlets.pojo.*;
import com.upxselling.utility.*;

public class getAllPatient extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
ResponseWrapper responseWrapper;
PrintWriter pw=null;
try
{
int from,to;
try
{
from=Integer.parseInt(request.getParameter("from"));
to=Integer.parseInt(request.getParameter("to"));
}
catch(Exception e)
{
from=-1; 
to=-1;
}
java.util.List<Patient> patients=new java.util.LinkedList<>();
Connection connection=UPXSellingConnection.getConnection();
if(connection==null)
{
responseWrapper=new ResponseWrapper();
responseWrapper.setException("Record not exist");
pw=response.getWriter();
pw.print(responseWrapper);
}
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select * from patient");
Patient patient;
while(resultSet.next())
{
patient=new Patient();
patient.setId(resultSet.getInt("id"));
patient.setName(resultSet.getString("name"));
patient.setAge(resultSet.getInt("age"));
patient.setEmail(resultSet.getString("email"));
patients.add(patient);
}
resultSet.close();
connection.close();
statement.close();
java.util.Collections.sort(patients);
java.util.List<Patient> subList=null;
if(from!=-1&&to!=-1)
{
if(to>patients.size())to=patients.size();
subList=patients.subList(from,to);
pw=response.getWriter();
response.setContentType("application/json");
responseWrapper=new ResponseWrapper();
responseWrapper.setResponse(subList);
pw.print(responseWrapper);
return;
}
if(from==-1&&to==-1)
{
pw=response.getWriter();
response.setContentType("application/json");
responseWrapper=new ResponseWrapper();
responseWrapper.setResponse(patients);
pw.print(responseWrapper);
}
}catch(Exception exception)
{
responseWrapper=new ResponseWrapper();
responseWrapper.setException("Record not exist");
pw.print(responseWrapper);
}
}
}