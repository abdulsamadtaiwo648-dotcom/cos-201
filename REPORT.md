# Student Course Management System — Project Report

## What the system does

The Student Course Management System is a Java console application for keeping
track of courses taken during a semester. A course has a code, title, and
positive credit-unit value. The menu lets a student add a course, view all
courses, search by course code, calculate the total units, save the list, load
a saved list, or exit.

## How the features work

The `Course` class represents one course and validates its data. Course codes
are trimmed and converted to uppercase, while titles are trimmed. The
`CourseManager` stores the course objects in a list and provides methods to add,
find, display through the app, total, save, and load courses. Duplicate course
codes are rejected without regard to letter casing.

`CourseManagementApp` presents a repeating numbered menu. It checks each
selection and asks the student for the values needed by the selected operation.
The total is calculated by iterating over all stored courses. Search uses a
recursive method: it checks one course at a time and calls itself with the next
list position until it finds the requested code or reaches the end.

Saving writes one course per line to `courses.txt`, using the format
`courseCode|courseTitle|units`. Loading reads and validates the complete file
before replacing the in-memory list, so an invalid file does not partly replace
the current courses. The file is stored in the folder from which the program
was started.

## Input and error handling

The program rejects blank course codes and titles, non-positive units,
duplicate codes, and text containing the file separator or a line break.
Entering non-numeric units is handled with a `NumberFormatException` message.
File read/write problems and malformed file rows are reported rather than
silently ignored. Invalid menu choices display guidance and return to the menu.

## Challenges and recursion

One challenge is ensuring that data loaded from a file is valid and does not
leave the course list partly updated. The program first builds and validates a
temporary list, then replaces the current list only after every row succeeds.
Recursion is applied in course search: the method advances one list index per
call and stops when the code is found or the list is exhausted.

## Testing

`CourseManagerTest` checks adding courses, normalized search, unit totals,
save/load behavior, duplicate rejection, and preservation of the current list
when a malformed file is loaded. It is a standalone test program and requires
no additional testing library.
