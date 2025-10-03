# Luna Chatbot User Guide

<p align="center">
  <img src="https://img.shields.io/badge/Type-CLI%20Task%20Manager-lightgrey.svg" alt="Type">
</p>

**Luna** is a simple yet powerful **Command Line Interface (CLI)** task management application. It helps you organize your daily todos, deadlines, and events efficiently. All tasks are saved automatically and loaded upon startup.

---

## 🚀 Key Features and Commands

### 1. Adding Tasks

Luna supports three types of tasks. After successful addition, Luna will display the total number of tasks in your list.

| Task Type | Command Format | Example | Description |
| :--- | :--- | :--- | :--- |
| **Todo** | `todo DESCRIPTION` | `todo read the CS2113 guide` | A simple task with no time constraints. |
| **Deadline** | `deadline DESCRIPTION /by DATETIME` | `deadline submit report /by 2025-10-31 2359` | A task that must be completed by a specific date/time. |
| **Event** | `event DESCRIPTION /from START /to END` | `event Project meeting /from 2pm /to 4pm` | A task that happens within a specific time range. |

### 2. Viewing Tasks

Display all tasks currently stored in your list, showing their type and completion status.

| Command | Format | Output Example |
| :--- | :--- | :--- |
| **list** | `list` | `1.[T][ ] read book`<br>`2.[D][X] return book (by: June 6th)` |

### 3. Marking and Unmarking

Change the completion status of a task using its **1-based index** (obtained from the `list` command).

| Command | Format | Description |
| :--- | :--- | :--- |
| **mark** | `mark INDEX` | Marks the specified task as done (`[X]`). |
| **unmark** | `unmark INDEX` | Marks the specified task as not done (`[ ]`). |

### 4. Deleting Tasks

Permanently removes a task from your list using its 1-based index.

| Command | Format | Description |
| :--- | :--- | :--- |
| **delete** | `delete INDEX` | Removes the task at the specified index. |

### 5. Finding Tasks

Search for tasks by providing a keyword. Luna will list all tasks whose description contains the provided keyword. **The search is case-insensitive.**

| Command | Format | Example |
| :--- | :--- | :--- |
| **find** | `find KEYWORD` | `find report` |

### 6. Exiting the Program

Saves all current tasks and exits the application.

| Command | Format |
| :--- | :--- |
| **bye** | `bye` |

---

## 🛠️ Usage Notes and Constraints

* **Case Sensitivity:** All command keywords (`todo`, `list`, `bye`, etc.) are **case-insensitive**.
* **Time/Date Format:** Luna accepts dates and times in any reasonable format (e.g., "6pm", "June 6th", "2025-06-06").
* **Data Persistence:** All data is automatically saved to `./data/luna.txt` upon task modification (add, mark, delete) or exiting the program.