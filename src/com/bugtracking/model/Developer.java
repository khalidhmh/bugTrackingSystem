package com.bugtracking.model; 

public class Developer extends User {
    public Developer(String id, String u, String p, String e) { super(id, u, p, e, "DEV"); }
    public String getDashboardName() { return "DevDashboard"; }
}
