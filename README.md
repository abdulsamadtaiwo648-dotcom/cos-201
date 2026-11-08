# Student Course Management System

A Java console program for recording a student's courses for one semester. It
supports adding and listing courses, searching by course code, totaling credit
units, and saving/loading the list.

## Requirements

- Java Development Kit (JDK) 8 or later
- No external libraries

## Compile and run

Open Command Prompt or PowerShell in this project folder:

```text
javac -d out src\Course.java src\CourseManager.java src\CourseManagementApp.java
java -cp out CourseManagementApp
```

Choose **5. Save to File** to write `courses.txt` in the current folder. Choose
**6. Load from File** to replace the current in-memory list with the saved list.
The save file uses one course per line in `code|title|units` format. Course
codes and titles cannot contain the `|` character or line breaks.

## Run the tests

```text
javac -d out src\Course.java src\CourseManager.java test\CourseManagerTest.java
java -cp out CourseManagerTest
```

## Screenshot for submission

Run the application, add a few courses, and display them with **2. View All
Courses**. Capture the terminal window using **Win + Shift + S** and save the
image in this folder for submission.
