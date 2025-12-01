package com.bugtracking.model; 

public class ProjectManager extends User {
    public ProjectManager(String id, String u, String p, String e) { super(id, u, p, e, "PM"); }
    public String getDashboardName() { return "PMDashboard"; }
}
