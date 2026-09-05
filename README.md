# MediCore — Mini Hospital Emergency Management System

A JavaFX desktop application that simulates emergency-department patient
flow registration, triage queueing, treatment logging, and per-patient
visit history built around four classic data structures, each
implemented from scratch.

![Java](https://img.shields.io/badge/Java-21%2B-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue)
![Build](https://img.shields.io/badge/build-Maven-red)
![Tests](https://img.shields.io/badge/tests-JUnit%205-green)



---

## Contents

- [Overview](#overview)
- [Why these data structures](#why-these-data-structures)
- [Architecture](#architecture)
- [Project structure](#project-structure)
- [Screens](#screens)
- [Getting started](#getting-started)
- [Running the tests](#running-the-tests)
- [Design system](#design-system)
- [Known limitations](#known-limitations)
- [Roadmap ideas](#roadmap-ideas)

---

## Overview

MediCore manages the lifecycle of an emergency-department patient:

```
Registered  →  Waiting in queue  →  Called for treatment  →  Treatment completed  →  Visit logged
   (BST)            (Queue)              (dequeue)               (Stack)          (Linked List)
```

Every one of those transitions is backed by a specific data structure,
chosen because its behavior matches the real-world constraint:

| Stage | Structure | Why |
|---|---|---|
| Patient records | **Binary Search Tree** | Ordered by Patient ID; O(log n) average search/insert/delete; in-order traversal gives sorted output for free |
| Waiting room | **Queue (FIFO)** | Fairness — first patient in is the first called, no exceptions |
| Completed treatments | **Stack (LIFO)** | "Undo the last thing that happened" is a natural pop; most recent treatment is always on top |
| Per-patient history | **Singly Linked List** | Chronological, append-heavy, no need for random access or shrinking arrays |

## Why these data structures

None of the four use `java.util.Stack`, `java.util.Queue`/`LinkedList`, or
`TreeMap`/`TreeSet`. They're hand-built (`PatientBST`, `EmergencyQueue`,
`TreatmentStack`, `VisitLinkedList`) so the underlying mechanics — node
pointers, rotations on delete, chaining — are visible and explainable,
rather than hidden behind a standard-library call.

## Architecture

Classic MVC, with a thin service façade so the UI layer never touches the
data structures directly:

```
┌─────────────┐      ┌───────────────┐      ┌──────────────┐
│    View     │◄────►│   Controller   │◄────►│   Service    │
│ (FXML + CSS)│      │  (JavaFX ctrl) │      │  (façade)    │
└─────────────┘      └───────────────┘      └──────┬───────┘
                                                     │
                                            ┌────────▼────────┐
                                            │  Data structures │
                                            │  BST / Queue /   │
                                            │  Stack / LinkedList │
                                            └──────────────────┘
```

- **Model** (`model/`) — plain Java objects: `Patient`, `Visit`,
  `TreatmentRecord`, `Priority`. Zero JavaFX imports, fully unit-testable
  on their own.
- **Data structures** (`datastructures/`) — the four required structures.
  Framework-free, same reasoning as above.
- **Service** (`service/HospitalService.java`) — the only class that owns
  and mutates the data structures. Controllers call methods like
  `registerPatient()`, `callNextPatient()`, `completeTreatment()` — never
  `PatientBST.insert()` directly.
- **Controller** (`controller/`) — one controller per screen, wired to
  FXML via `@FXML` and `fx:id`.
- **View** (`resources/fxml/`, `resources/css/`) — layout and styling,
  no business logic.

## Project structure

```
medicore/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/com/medicore/
│   │   │   ├── MainApp.java
│   │   │   ├── model/              Patient, Visit, TreatmentRecord, Priority
│   │   │   ├── datastructures/     PatientBST, EmergencyQueue, TreatmentStack, VisitLinkedList
│   │   │   ├── service/            HospitalService (façade)
│   │   │   ├── controller/         One controller per screen
│   │   │   └── util/               SceneNavigator
│   │   └── resources/com/medicore/
│   │       ├── fxml/               dashboard, patient-records, emergency-queue,
│   │       │                       treatment-history, visit-history, settings, main-layout
│   │       └── css/                theme-light.css, components.css
│   └── test/java/com/medicore/datastructures/
│       ├── PatientBSTTest.java
│       ├── EmergencyQueueTest.java
│       ├── TreatmentStackTest.java
│       └── VisitLinkedListTest.java
```

## Screens

| Screen | What it does |
|---|---|
| **Dashboard** | Live stats (total patients, waiting, treated today), queue preview, recent treatments |
| **Patients** | Register (BST insert), search by ID, delete, sorted list (in-order traversal) |
| **Emergency Queue** | Enqueue an existing patient, "Call next patient" (dequeue), live waiting list |
| **Treatments** | Log a completed treatment (push), "Undo last" (pop), most-recent-first history |
| **Visit History** | Per-patient timeline: add / search / remove a visit, load by Patient ID |
| **Settings** | About screen explaining the data-structure choices |

## Getting started

**Requirements:** JDK 21+, Maven 3.9+ (or use VS Code's bundled Maven
support via the *Extension Pack for Java*).

```bash
git clone <your-repo-url>
cd medicore

# First build — downloads JavaFX + JUnit from Maven Central
mvn clean install

# Run the app
mvn javafx:run
```

If you're on Apple Silicon or hit a JavaFX native-module error, make sure
your Maven settings aren't pinned to an old JavaFX classifier — the
`javafx-maven-plugin` should resolve the correct platform artifacts
automatically from Maven Central.

## Running the tests

```bash
mvn test
```

Coverage focuses on the graded core:

- **BST** — duplicate-ID rejection, search hit/miss, in-order ordering, delete (leaf and two-children/successor cases)
- **Queue** — empty-queue exception, strict FIFO ordering, non-destructive `displayAll()`
- **Stack** — empty-stack exception, strict LIFO ordering, most-recent-first display
- **Linked list** — chronological append, search hit/miss, remove at head/middle/tail

## Design system

Apple-inspired: one accent color (`#0A84FF`), neutral gray/white surfaces,
12px card radii, soft shadows instead of hard borders, Segoe UI/Inter
typography. Tokens live in `theme-light.css`; shared component styles
(cards, buttons, nav items, list rows) live in `components.css`.

## Known limitations

- `PatientBST` is not self-balancing — worst case (patients registered in
  strictly sorted ID order) degrades to O(n).
- No persistence yet — state resets on restart (see roadmap).
- Single-window desktop app only; no authentication/multi-user support.

## Roadmap ideas

- Swap in an AVL or red-black variant of `PatientBST` for guaranteed
  O(log n).
- Add a `RepositoryInterface` + SQLite/JSON backing so data survives
  restarts, without touching the core data-structure classes.
- Priority-aware queue variant (still FIFO within a priority tier).
- Exportable end-of-shift PDF report.

---

*Generated as a concept/reference build from a CIT300-style assignment
brief. Study it, understand it, then build your own.*
