package com.bugtracking.model; 

public class Tester extends User {
    public Tester(String id, String u, String p, String e) { super(id, u, p, e, "TESTER"); }
    public String getDashboardName() { return "TesterDashboard"; }
}
