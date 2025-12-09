# Bug Tracking System - Simplified Project Architecture Plan

## Project Overview
A Java Swing desktop application for bug tracking with role-based access (Admin, Developer, Tester, Project Manager) using text files as the database. **No project management** - just a simple project name field when creating bugs.

---

## 📁 Proposed Folder Structure

```
bugTrackingSystem/
│
├── src/
│   │
│   ├── main/
│   │   └── Main.java                    # Application entry point
│   │
│   ├── config/
│   │   └── AppConfig.java               # Configuration constants (paths, settings)
│   │
│   ├── models/                          # Data models (POJOs)
│   │   ├── User.java                    # User entity (id, name, email, role, password)
│   │   ├── Bug.java                     # Bug entity (id, title, description, projectName, status, priority, assignee)
│   │   └── Role.java                    # Enum for roles (ADMIN, DEVELOPER, TESTER, PROJECT_MANAGER)
│   │
│   ├── dao/                             # Data Access Objects (File I/O operations)
│   │   ├── BaseDAO.java                 # Base interface/abstract class for CRUD
│   │   ├── UserDAO.java                 # User file operations
│   │   └── BugDAO.java                  # Bug file operations
│   │
│   ├── services/                        # Business logic layer
│   │   ├── AuthService.java             # Login/logout/authentication
│   │   ├── UserService.java             # User management logic
│   │   └── BugService.java              # Bug management logic
│   │
│   ├── utils/                           # Utility/Helper classes
│   │   ├── FileUtils.java               # File reading/writing utilities
│   │   ├── ValidationUtils.java         # Input validation helpers
│   │   ├── SessionManager.java          # Current logged-in user session
│   │   └── IDGenerator.java             # Generate unique IDs
│   │
│   └── ui/                              # User Interface (Swing Forms)
│       │
│       ├── common/                      # Shared UI components
│       │   ├── LoginForm.java           # Login screen
│       │   ├── LoginForm.form
│       │   └── BaseFrame.java           # Base frame with common functionality
│       │
│       ├── admin/                       # Admin module UI
│       │   ├── AdminDashboard.java      # Admin main dashboard
│       │   ├── AdminDashboard.form
│       │   ├── UserManagementPanel.java # Panel to list/view users (grid/table)
│       │   ├── UserManagementPanel.form
│       │   ├── AddUserForm.java         # Form to add new user
│       │   ├── AddUserForm.form
│       │   ├── EditUserForm.java        # Form to edit existing user
│       │   └── EditUserForm.form
│       │
│       ├── projectmanager/              # Project Manager module UI
│       │   ├── PMDashboard.java         # PM main dashboard (bug overview)
│       │   ├── PMDashboard.form
│       │   ├── BugOverviewPanel.java    # Overview of all bugs
│       │   ├── BugOverviewPanel.form
│       │   ├── AssignBugForm.java       # Assign bugs to developers
│       │   └── AssignBugForm.form
│       │
│       ├── developer/                   # Developer module UI
│       │   ├── DeveloperDashboard.java  # Developer main dashboard
│       │   ├── DeveloperDashboard.form
│       │   ├── AssignedBugsPanel.java   # List of bugs assigned to developer
│       │   ├── AssignedBugsPanel.form
│       │   ├── BugDetailsForm.java      # View/update bug status (mark as done)
│       │   └── BugDetailsForm.form
│       │
│       └── tester/                      # Tester module UI
│           ├── TesterDashboard.java     # Tester main dashboard
│           ├── TesterDashboard.form
│           ├── ReportBugForm.java       # Form to report/create new bug (with project name field)
│           ├── ReportBugForm.form
│           ├── BugListPanel.java        # List of reported bugs
│           ├── BugListPanel.form
│           ├── VerifyBugForm.java       # Verify fixed bugs
│           └── VerifyBugForm.form
│
├── data/                                # Text file database storage
│   ├── users.txt                        # Stores user records
│   └── bugs.txt                         # Stores bug records (includes projectName as a field)
│
└── resources/                           # Static resources
    ├── icons/                           # UI icons
    └── images/                          # Images/logos
```

---

## 📋 Module Responsibilities

### **1. Admin Module**
| Feature | Description |
|---------|-------------|
| Dashboard | Overview of system stats (total users, bugs) |
| User Management | View all users in a table/grid |
| Add User | Create new user with role assignment |
| Edit User | Modify existing user details |
| Delete User | Remove user from system |

### **2. Project Manager Module**
| Feature | Description |
|---------|-------------|
| Dashboard | Summary of all bugs and their status |
| Bug Overview | See all bugs (filter by project name, status) |
| Assign Bugs | Assign bugs to developers |

### **3. Developer Module**
| Feature | Description |
|---------|-------------|
| Dashboard | Overview of assigned bugs |
| Assigned Bugs | List of bugs assigned to the developer |
| Bug Details | View bug info and update status |
| Mark as Done | Change bug status to "Fixed" |

### **4. Tester Module**
| Feature | Description |
|---------|-------------|
| Dashboard | Overview of testing activities |
| Report Bug | Create new bug (enter project name as text field) |
| Bug List | View all reported bugs |
| Verify Bug | Verify if fixed bug is actually resolved |

---

## 📄 Data File Formats (Suggested)

### `users.txt`
```
id|name|email|password|role
```

### `bugs.txt`
```
id|title|description|projectName|status|priority|reporterId|assigneeId|createdDate|updatedDate
```

> **Note:** `projectName` is just a simple text field - no separate project management needed!

---

## 🔐 Roles & Permissions Matrix

| Action | Admin | Project Manager | Developer | Tester |
|--------|-------|-----------------|-----------|--------|
| Manage Users | ✅ | ❌ | ❌ | ❌ |
| View All Bugs | ✅ | ✅ | ❌ | ✅ |
| Assign Bugs | ✅ | ✅ | ❌ | ❌ |
| Report Bugs | ❌ | ❌ | ❌ | ✅ |
| Fix Bugs | ❌ | ❌ | ✅ | ❌ |
| Verify Bugs | ❌ | ❌ | ❌ | ✅ |
| View Assigned Bugs | ❌ | ✅ | ✅ | ✅ |

---

## 🔄 Application Flow

```
┌─────────────┐
│   Login     │
└──────┬──────┘
       │
       ▼
┌─────────────────────────────────────────────┐
│           Role-Based Routing                │
├─────────┬─────────┬───────────┬─────────────┤
│  Admin  │   PM    │ Developer │   Tester    │
│Dashboard│Dashboard│ Dashboard │  Dashboard  │
└─────────┴─────────┴───────────┴─────────────┘
```

---

## ✅ Next Steps for Your Team

1. **Create the folder structure** as outlined above
2. **Create empty Java files** with basic class declarations
3. **Create empty .form files** for NetBeans GUI Builder
4. **Create data folder** with empty .txt files
5. **Assign modules** to team members:
   - Team Member 1: Admin module + DAO layer
   - Team Member 2: Project Manager module + Services
   - Team Member 3: Developer module + Models
   - Team Member 4: Tester module + Utils + Common UI

---

Would you like me to **create this entire folder/file structure** in your NetBeans project now?