@echo off
echo Checking and Creating Project Structure...
echo.

REM --- 1. Create Directories (Only if they don't exist) ---
if not exist "src\com\bugtracking\model" mkdir "src\com\bugtracking\model"
if not exist "src\com\bugtracking\controller" mkdir "src\com\bugtracking\controller"
if not exist "src\com\bugtracking\view\admin" mkdir "src\com\bugtracking\view\admin"
if not exist "src\com\bugtracking\view\tester" mkdir "src\com\bugtracking\view\tester"
if not exist "src\com\bugtracking\view\dev" mkdir "src\com\bugtracking\view\dev"
if not exist "src\com\bugtracking\view\pm" mkdir "src\com\bugtracking\view\pm"
if not exist "src\com\bugtracking\view\components" mkdir "src\com\bugtracking\view\components"
if not exist "data" mkdir "data"
if not exist "assets" mkdir "assets"

REM --- 2. Create Model Classes ---

set "FILE=src\com\bugtracking\model\User.java"
if not exist "%FILE%" (
    echo package com.bugtracking.model; > "%FILE%"
    echo public abstract class User {} >> "%FILE%"
    echo [CREATED] User.java
) else ( echo [SKIP] User.java already exists )

set "FILE=src\com\bugtracking\model\Admin.java"
if not exist "%FILE%" (
    echo package com.bugtracking.model; > "%FILE%"
    echo public class Admin extends User {} >> "%FILE%"
    echo [CREATED] Admin.java
) else ( echo [SKIP] Admin.java already exists )

set "FILE=src\com\bugtracking\model\Tester.java"
if not exist "%FILE%" (
    echo package com.bugtracking.model; > "%FILE%"
    echo public class Tester extends User {} >> "%FILE%"
    echo [CREATED] Tester.java
) else ( echo [SKIP] Tester.java already exists )

set "FILE=src\com\bugtracking\model\Developer.java"
if not exist "%FILE%" (
    echo package com.bugtracking.model; > "%FILE%"
    echo public class Developer extends User {} >> "%FILE%"
    echo [CREATED] Developer.java
) else ( echo [SKIP] Developer.java already exists )

set "FILE=src\com\bugtracking\model\ProjectManager.java"
if not exist "%FILE%" (
    echo package com.bugtracking.model; > "%FILE%"
    echo public class ProjectManager extends User {} >> "%FILE%"
    echo [CREATED] ProjectManager.java
) else ( echo [SKIP] ProjectManager.java already exists )

set "FILE=src\com\bugtracking\model\Bug.java"
if not exist "%FILE%" (
    echo package com.bugtracking.model; > "%FILE%"
    echo public class Bug {} >> "%FILE%"
    echo [CREATED] Bug.java
) else ( echo [SKIP] Bug.java already exists )

set "FILE=src\com\bugtracking\model\Notification.java"
if not exist "%FILE%" (
    echo package com.bugtracking.model; > "%FILE%"
    echo public class Notification {} >> "%FILE%"
    echo [CREATED] Notification.java
) else ( echo [SKIP] Notification.java already exists )

REM --- 3. Create Controller Classes ---

set "FILE=src\com\bugtracking\controller\FileManager.java"
if not exist "%FILE%" (
    echo package com.bugtracking.controller; > "%FILE%"
    echo public class FileManager {} >> "%FILE%"
    echo [CREATED] FileManager.java
) else ( echo [SKIP] FileManager.java already exists )

set "FILE=src\com\bugtracking\controller\AuthService.java"
if not exist "%FILE%" (
    echo package com.bugtracking.controller; > "%FILE%"
    echo public class AuthService {} >> "%FILE%"
    echo [CREATED] AuthService.java
) else ( echo [SKIP] AuthService.java already exists )

set "FILE=src\com\bugtracking\controller\EmailService.java"
if not exist "%FILE%" (
    echo package com.bugtracking.controller; > "%FILE%"
    echo public class EmailService {} >> "%FILE%"
    echo [CREATED] EmailService.java
) else ( echo [SKIP] EmailService.java already exists )

REM --- 4. Create View Classes ---

set "FILE=src\com\bugtracking\view\MainFrame.java"
if not exist "%FILE%" (
    echo package com.bugtracking.view; > "%FILE%"
    echo import javax.swing.JFrame; >> "%FILE%"
    echo public class MainFrame extends JFrame {} >> "%FILE%"
    echo [CREATED] MainFrame.java
) else ( echo [SKIP] MainFrame.java already exists )

set "FILE=src\com\bugtracking\view\LoginPanel.java"
if not exist "%FILE%" (
    echo package com.bugtracking.view; > "%FILE%"
    echo import javax.swing.JPanel; >> "%FILE%"
    echo public class LoginPanel extends JPanel {} >> "%FILE%"
    echo [CREATED] LoginPanel.java
) else ( echo [SKIP] LoginPanel.java already exists )

REM --- 5. Create Sub-View Classes ---

set "FILE=src\com\bugtracking\view\admin\AdminDashboard.java"
if not exist "%FILE%" (
    echo package com.bugtracking.view.admin; > "%FILE%"
    echo import javax.swing.JPanel; >> "%FILE%"
    echo public class AdminDashboard extends JPanel {} >> "%FILE%"
    echo [CREATED] AdminDashboard.java
) else ( echo [SKIP] AdminDashboard.java already exists )

set "FILE=src\com\bugtracking\view\admin\ManageUsersPanel.java"
if not exist "%FILE%" (
    echo package com.bugtracking.view.admin; > "%FILE%"
    echo import javax.swing.JPanel; >> "%FILE%"
    echo public class ManageUsersPanel extends JPanel {} >> "%FILE%"
    echo [CREATED] ManageUsersPanel.java
) else ( echo [SKIP] ManageUsersPanel.java already exists )

set "FILE=src\com\bugtracking\view\tester\TesterDashboard.java"
if not exist "%FILE%" (
    echo package com.bugtracking.view.tester; > "%FILE%"
    echo import javax.swing.JPanel; >> "%FILE%"
    echo public class TesterDashboard extends JPanel {} >> "%FILE%"
    echo [CREATED] TesterDashboard.java
) else ( echo [SKIP] TesterDashboard.java already exists )

set "FILE=src\com\bugtracking\view\tester\AddBugPanel.java"
if not exist "%FILE%" (
    echo package com.bugtracking.view.tester; > "%FILE%"
    echo import javax.swing.JPanel; >> "%FILE%"
    echo public class AddBugPanel extends JPanel {} >> "%FILE%"
    echo [CREATED] AddBugPanel.java
) else ( echo [SKIP] AddBugPanel.java already exists )

set "FILE=src\com\bugtracking\view\dev\DevDashboard.java"
if not exist "%FILE%" (
    echo package com.bugtracking.view.dev; > "%FILE%"
    echo import javax.swing.JPanel; >> "%FILE%"
    echo public class DevDashboard extends JPanel {} >> "%FILE%"
    echo [CREATED] DevDashboard.java
) else ( echo [SKIP] DevDashboard.java already exists )

set "FILE=src\com\bugtracking\view\dev\BugDetailsDialog.java"
if not exist "%FILE%" (
    echo package com.bugtracking.view.dev; > "%FILE%"
    echo import javax.swing.JDialog; >> "%FILE%"
    echo public class BugDetailsDialog extends JDialog {} >> "%FILE%"
    echo [CREATED] BugDetailsDialog.java
) else ( echo [SKIP] BugDetailsDialog.java already exists )

set "FILE=src\com\bugtracking\view\pm\PMDashboard.java"
if not exist "%FILE%" (
    echo package com.bugtracking.view.pm; > "%FILE%"
    echo import javax.swing.JPanel; >> "%FILE%"
    echo public class PMDashboard extends JPanel {} >> "%FILE%"
    echo [CREATED] PMDashboard.java
) else ( echo [SKIP] PMDashboard.java already exists )

set "FILE=src\com\bugtracking\view\components\HeaderPanel.java"
if not exist "%FILE%" (
    echo package com.bugtracking.view.components; > "%FILE%"
    echo import javax.swing.JPanel; >> "%FILE%"
    echo public class HeaderPanel extends JPanel {} >> "%FILE%"
    echo [CREATED] HeaderPanel.java
) else ( echo [SKIP] HeaderPanel.java already exists )

REM --- 6. Create com.bugtracking.main.Main & Data Files ---

set "FILE=src\com\bugtracking\com.bugtracking.main.Main.java"
if not exist "%FILE%" (
    echo package com.bugtracking; > "%FILE%"
    echo public class com.bugtracking.main.Main { public static void main(String[] args) {} } >> "%FILE%"
    echo [CREATED] com.bugtracking.main.Main.java
) else ( echo [SKIP] com.bugtracking.main.Main.java already exists )

if not exist "data\users.txt" (
    type nul > data\users.txt
    echo [CREATED] users.txt
) else ( echo [SKIP] users.txt already exists )

if not exist "data\bugs.txt" (
    type nul > data\bugs.txt
    echo [CREATED] bugs.txt
) else ( echo [SKIP] bugs.txt already exists )

if not exist "data\notifications.txt" (
    type nul > data\notifications.txt
    echo [CREATED] notifications.txt
) else ( echo [SKIP] notifications.txt already exists )

echo.
echo ==================================================
echo   DONE! Only missing files were created.
echo   Your existing code is safe.
echo ==================================================
pause