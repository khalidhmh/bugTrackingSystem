# Bug Tracking System

A Java Swing-based bug tracking application that allows teams to manage, track, and resolve software bugs efficiently.

## Prerequisites

To run this project, you need:

- **Java Development Kit (JDK) 11 or higher** (JDK 17 recommended)
  - Download from: https://adoptium.net/ or https://www.oracle.com/java/technologies/downloads/

### Verify Java Installation

Open a terminal or command prompt and run:

```bash
java -version
javac -version
```

Both commands should display version information (11 or higher).

## Project Structure

```
bugTrackingSystem/
├── src/
│   └── com/bugtracking/
│       ├── main/           # Main application entry point
│       ├── model/          # Data models (User, Bug, etc.)
│       ├── controller/     # Business logic (AuthService, FileManager)
│       └── view/           # GUI components (Swing panels and frames)
├── data/                   # Data storage directory (created on first run)
├── assets/                 # Application assets
└── setup.bat               # Windows setup script
```

## How to Run

### Option 1: Using Command Line

1. **Clone the repository:**
   ```bash
   git clone https://github.com/khalidhmh/bugTrackingSystem.git
   cd bugTrackingSystem
   ```

2. **Compile the source code:**
   ```bash
   # Create output directory
   mkdir -p out

   # Compile all Java files
   javac -d out -sourcepath src $(find src -name "*.java")
   ```

   On Windows:
   ```cmd
   mkdir out
   dir /s /b src\*.java > sources.txt
   javac -d out -sourcepath src @sources.txt
   del sources.txt
   ```

3. **Run the application:**
   ```bash
   java -cp out com.bugtracking.main.Main
   ```

### Option 2: Using IntelliJ IDEA (Recommended)

1. **Download and install IntelliJ IDEA:**
   - Community Edition (free): https://www.jetbrains.com/idea/download/

2. **Open the project:**
   - Launch IntelliJ IDEA
   - Select `File` → `Open`
   - Navigate to the `bugTrackingSystem` folder and click `OK`

3. **Run the application:**
   - Open `src/com/bugtracking/main/Main.java`
   - Click the green play button ▶ next to the `main` method, or
   - Press `Shift + F10` (Windows/Linux) or `Ctrl + R` (Mac)

### Option 3: Using Eclipse

1. **Download and install Eclipse:**
   - Eclipse IDE for Java Developers: https://www.eclipse.org/downloads/

2. **Import the project:**
   - Launch Eclipse
   - Select `File` → `Import`
   - Choose `General` → `Existing Projects into Workspace`
   - Browse to the `bugTrackingSystem` folder

3. **Run the application:**
   - Right-click on `Main.java`
   - Select `Run As` → `Java Application`

### Option 4: Using Visual Studio Code

1. **Install VS Code and Java Extension Pack:**
   - Download VS Code: https://code.visualstudio.com/
   - Install the "Extension Pack for Java" from the Extensions marketplace

2. **Open and run:**
   - Open the `bugTrackingSystem` folder in VS Code
   - Open `src/com/bugtracking/main/Main.java`
   - Click `Run` above the `main` method

## Windows Setup Script

For Windows users, a `setup.bat` script is provided that creates the basic project structure:

```cmd
setup.bat
```

This will create any missing directories and placeholder files if they don't exist.

## Features

- **Multi-role User System:** Admin, Tester, Developer, and Project Manager roles
- **Bug Management:** Create, track, and resolve bugs
- **User Authentication:** Secure login system
- **Dashboard Views:** Role-specific dashboards for each user type
- **File-based Storage:** Data persistence using text files

## User Roles

| Role | Description |
|------|-------------|
| Admin | Manage users and system configuration |
| Tester | Report and track bugs |
| Developer | View and fix assigned bugs |
| Project Manager | Oversee project and bug resolution |

## Troubleshooting

### "java is not recognized" error
- Ensure JDK is installed and `JAVA_HOME` environment variable is set
- Add `%JAVA_HOME%\bin` to your system PATH

### "Could not find or load main class" error
- Ensure you're running from the project root directory
- Verify the compilation was successful (check `out` folder for `.class` files)

### GUI not displaying
- Ensure you have a graphical environment (not running in a headless server)
- On Linux, ensure X11 or Wayland is properly configured

## License

This project is for educational purposes.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request
