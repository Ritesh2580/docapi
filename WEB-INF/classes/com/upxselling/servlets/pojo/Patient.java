package com.upxselling.servlets.pojo;
import java.io.*;
public class Patient implements Serializable,Comparable<Patient>
{
private int id;
private String name;
private int age;
private String email;
public Patient()
{
this.id=0;
this.age=0;
this.name="";
this.email="";
}
public void setId(int id)
{
this.id=id;
}
public int getId()
{
return this.id;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setAge(int age)
{
this.age=age;
}
public int getAge()
{
return this.age;
}
public void setEmail(String email)
{
this.email=email;
}
public String getEmail()
{
return this.email;
}
public boolean equals(Object object)
{
if(!(object instanceof Patient)) return false;
Patient other=(Patient)object;
return this.id==other.id;
}
public int compareTo(Patient other)
{
if(this.id==other.getId())return  0;
else return 1;

}
public int hashCode()
{
return this.id;
}
}