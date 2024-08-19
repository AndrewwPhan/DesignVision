
# MVC Architecture Design Plan

Model, View, Controller (MVC)  

## Condensed summary

**tldr:**

  1. Domain Breakdown: Identify core functionalities and domain entities.
  1. Model Design: Create Model classes representing data and relationships.
  1. View Design: Design user interface (UI) structure and interaction flows.
  1. Controller Design: Define Controllers to handle interactions, bridge Models and Views.
  1. Work Packet Categories: Assign development tasks.
  1. Communication and Collaboration: Code review pull requests.

Also consider scalability and error handling.

## The Plan

**Objective:** Define the initial steps and delegate tasks for building our application using the MVC architecture.
**Assumptions:** Concept and scope has been discussed. Minimum viable product and stretch goal divisions made. (MoSCoW)

1. Domain Breakdown:

    **Task A**: Identify the core functionalities of the application.
        What are the main things our application does? (e.g., User Management, Palette Management, Export Processing, etc)
        What are the key data concepts involved? (e.g., User, Palette, Palette set?)  
    **Task B (Team Discussion)**: Discuss and refine domain breakdown. Relationships between entities (e.g., Users can create Palettes, Palettes contain colours, UI element is a "Patch"?).

2. Model Design:

    **Task C (Backend):**
        For each identified domain entity, design a corresponding Model class.
        Define the attributes and relationships of each Model class.
        Determine persistence mechanisms (e.g., database) for storing Model data.

3. View Design:

    **Task D (UI/UX)**:  
        Define the overall user interface (UI) structure for the application.  
        Sketch different functionalities (e.g., Login screen, Palette list/set,  Palette details).  
        User interaction flow.

4. Controller Design:

    **Task E**:  
        Based on functionalities and UI elements, design Controller classes.  
        Define methods in Controllers to handle user interactions with the UI.  
        These will interact with Models (retrieve/update data) and Views (update UI).

5. Work Packet Categories:

    **Task F:**  
        Model implementation and data persistence.
        UI/UX mockups and reusable UI components.
        Controller logic, user interaction handling, and integrating Models and Views.
        Post issue (story, task, subtask) with assignee, story points and due dates in backlog (Jira).
        Rank issue and bring into Sprint scope at sprint planning.

6. Communication and Collaboration:

    **Task G:** Collaboration strategies.
    Regular meetings: Discuss progress, identify roadblocks and drags, update backlog.
    Code reviews: Keep in touch with other segments of work by reviewing pull requests.
    Give feedback, ask questions, get help when needed!

Next:

  Unit testing strategies for Models and Controllers.

Notes:

  **Scalability:** Plan simple with potential for growth.  
  **Error Handling:** Gracefully handle unexpected situations, especially on external input (user).
