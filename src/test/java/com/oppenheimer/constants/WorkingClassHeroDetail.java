package com.oppenheimer.constants;

public class WorkingClassHeroDetail {
      String birthday;
      String gender;
      String name;
      String natid;
     String salary;
     String tax;

     public WorkingClassHeroDetail() { }

    public WorkingClassHeroDetail(String birthday, String gender, String name, String natid, String salary, String tax) {
        this.birthday=birthday;
        this.gender=gender;
        this.name=name;
        this.natid=natid;
        this.salary=salary;
        this.tax=tax;
    }


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNatid() {
        return natid;
    }

    public void setNatid(String natid) {
        this.natid = natid;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

}
