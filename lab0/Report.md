# Lab 0: Opening a new business

----
## 1. Introduction 
The objective of this laboratory was to design a simple prototype
that reflects a realistic business
domain while also demonstrating sound object-oriented design 
principles. The prototype models a company that manages multiple 
businesses or branch locations, each with its own employee roles
and operational behavior. We had to implement at least 3 SOLID 
principles.

The final model is based on:

- Company as the central headquarters;
- Business as a branch or shop;
- Worker as the abstraction for employee behavior;
- BusinessFactory as the object creation mechanism;
- Output and ConsoleOutput as the presentation layer;
- Main as the execution entry point.

This structure mirrors a realistic business environment in which 
a central entity manages several branch locations, each of which 
has employees performing different tasks.

---
## 2. Architecture
The final architecture can be summarized as follows:
### 2.1 Company

The Company class represents the central organization or
headquarters. It manages multiple Business instances and acts
as the top-level coordinator for company-level operations. 
It stores all businesses and is responsible for registering 
and managing them.

### 2.2 Business

The Business class represents one branch or franchise shop.
It stores its: name, location, number of workers and revenue.

It can also: open; add workers; perform work; 
print a business summary.

### 2.3 BusinessFactory

The BusinessFactory class is the creation mechanism for new 
businesses. It validates user input and creates valid Business 
objects. This is the core factory structure in the application.
It also represents a Factory design pattern.

### 2.4 Worker

The Worker interface represents a general employee role. 
Concrete implementations such as Designer, Cashier, Cook, and ITist
implement this interface and define different behaviors. This 
also works as Strategy pattern because the behavior is selected 
through different
implementations of the same abstraction.

### 2.5 Output and ConsoleOutput

The Output interface abstracts console output. ConsoleOutput 
implements it. This keeps the business logic independent of 
terminal output and avoids direct dependency on `System.out` inside 
the domain classes.

### 2.6 Main

The Main class acts as the application entry point. It:

- reads data from the terminal;
- creates a Business using BusinessFactory;
- adds workers;
- registers the business in Company;
- starts the company-level operations.

---
## 3. SOLID Principles Respected

### 3.1 Single Responsibility Principle

The Single Responsibility Principle states that a class 
should have only one reason to change.

This architecture respects SRP because:

- BusinessFactory is responsible only for creation;
- Business is responsible for a single branch;
- Company is responsible for company management;
- Worker is responsible for employee behavior;
- Output is responsible for printing.

This separation makes the code easier to read, maintain, and extend.

### 3.2 Open/Closed Principle (OCP)

The Open/Closed Principle states that software entities
should be open for extension but closed for modification.

This design respects OCP because 
- new worker roles can be added without modifying the existing Worker interface
- a new business behavior can be introduced by extending a class rather than rewriting existing logic
- new functionality can be added in a localized manner

For example, adding Manager or SecurityGuard requires creating
a new class that implements Worker, without changing the existing 
worker hierarchy.

### 3.3 Liskov Substitution Principle (LSP)

The Liskov Substitution Principle states that objects of a 
derived class should be substitutable for objects of their base 
class without breaking the application.

This is respected because all worker implementations are 
valid substitutes for Worker. A Designer, Cashier, or Cook can 
all be used wherever a Worker is expected, and the business 
logic continues to work correctly.

### 3.4 Interface Segregation Principle (ISP)

The Interface Segregation Principle states that clients should 
not be forced to depend on methods they do not use.

This design respects ISP because:

- Worker has a single relevant method: doWork();
- Output exposes a single responsibility: println().

This avoids bloated interfaces and ensures that implementations 
are not burdened with unnecessary methods.

### 3.5 Dependency Inversion Principle (DIP)

The Dependency Inversion Principle states that higher-level 
modules should not depend on lower-level modules; both should
depend on abstractions.

This architecture respects DIP because:

- domain logic depends on the Worker abstraction rather
than concrete worker implementations;
- output is abstracted through Output;
- Main and Company are not tightly coupled to specific classes;
- concrete implementations can be swapped without rewriting the
core logic.

This improves flexibility and makes future maintenance easier.

---

## 4. UML Diagram
<a href="https://ibb.co/pvhNYLz2"><img src="https://i.ibb.co/nsP4xLcr/Business-Workforce-Management-Framework-2026-10-20-065450.png" alt="Business-Workforce-Management-Framework-2026-10-20-065450" border="0" /></a>

---
## 5. Conclusion
In this lab, we made a business model that respects all 5 SOLID
principles, however I think the best implemented principles are SRP
(Single Responsibility Principle), OCP (Open/Closed Principle) and
ISP (Interface Segregation Principle). I based this model off real
examples: Company class acts as the Headquarters of all the Business
classes, and each Business class (or each Business) acts mostly
independently from Company but obviously respects its rules,
policies, etc. Overall it was more complicated remembering all the 
SOLID principles and how to work in Java than designing the model
itself. 
