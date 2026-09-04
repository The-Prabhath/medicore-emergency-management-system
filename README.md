# MediCore — Mini Hospital Emergency Management System

A JavaFX desktop application demonstrating four core data structures in a
realistic hospital emergency-management scenario.

> **Reference/inspiration build.** This project was generated as a study
> reference based on a CIT300-style brief. If you're using it for a real
> assignment: read every class, understand it, then re-implement it in your
> own words. Submitting this as-is would not satisfy an individual-work
> requirement and the marker will likely recognize it.

## Data structures

| Structure | File | Backs |
|---|---|---|
| Binary Search Tree | `datastructures/PatientBST.java` | Patient Records screen |
| Queue (custom linked FIFO) | `datastructures/EmergencyQueue.java` | Emergency Queue screen |
| Stack (custom array-backed LIFO) | `datastructures/TreatmentStack.java` | Treatment History screen |
| Singly Linked List | `datastructures/VisitLinkedList.java` | Visit History screen (per patient) |

Each structure is implemented from scratch (no `java.util.Stack`,
`java.util.Queue`, or `TreeMap`) so the mechanics are explicit and easy to
explain in a demo video.

## Running it

```bash
mvn clean javafx:run
```

Requires JDK 21+ and Maven. JavaFX dependencies are pulled automatically
via the `javafx-maven-plugin`.

## Running tests

```bash
mvn test
```

Unit tests cover insert/search/delete/traversal (BST), FIFO ordering and
empty-queue handling (Queue), LIFO ordering and empty-stack handling
(Stack), and add/search/remove across head/middle/tail (Linked List).

## Architecture

```
model/            Plain data classes (Patient, Visit, TreatmentRecord, Priority)
datastructures/   The four required structures, framework-free
service/          HospitalService — façade the controllers talk to
controller/       One JavaFX controller per screen
util/             SceneNavigator (screen switching), etc.
resources/fxml/   One .fxml per screen
resources/css/    Theme + shared component styles
```

Controllers never touch `PatientBST` / `EmergencyQueue` / `TreatmentStack`
/ `VisitLinkedList` directly — everything goes through `HospitalService`,
which keeps the data structures independently testable and the UI layer
swappable.

## Screens

- **Dashboard** — live stats, queue preview, recent treatments
- **Patients** — BST insert / search / delete / in-order list
- **Emergency Queue** — enqueue / dequeue (call next patient)
- **Treatments** — push (complete treatment) / pop (undo last)
- **Visit History** — per-patient linked list: add / search / remove / display
- **Settings** — about screen summarizing the data-structure choices
