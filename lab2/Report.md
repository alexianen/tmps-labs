# Lab 2: Composite, Decorator and Facade design patterns

----

## 1. Introduction

In this laboratory, I continued upgrading the Company Manager from the previous
2 labs, which is a console application written in Java. The user can open several 
businesses (a restaurant, a game shop, an IT company or a general business), describe
each one with a name, location, worker count and revenue, and attach departments to it.
At the end, the program prints a report of every business and runs the work of its
employees.

In lab 1, I used three **creational** patterns: **Singleton** (`Company`), 
**Factory Method** (`BusinessFactory` and its subclasses) and **Builder** 
(`Department.Builder`). This report describes the third stage of the project, 
in which three **structural** patterns were added:

- **Composite**, so that a single worker and a whole team of workers 
can be treated in the same way.
- **Decorator**, so that extra behaviour (timestamps, indentation) can be added 
- o the program's output without changing the classes that produce it.
- **Facade**, so that the user-interface code has one simple entry point to the 
rest of the system.

Each pattern was chosen because it solves a concrete weakness of the previous design, 
and each one reuses something that already existed in the code: the `Worker` interface,
the `Output` interface and the overloaded `Main` class.

----

## 2. Architecture

### 2.1 Overview and package structure

The code is divided into four packages and an entry point.

| Package | Contents | Responsibility |
|---|---|---|
| `facade` | `CompanyFacade`, `BusinessType` | A single entry point to the system for the user interface |
| `factory` | `BusinessFactory`, `RestaurantFactory`, `GameShopFactory`, `ITCompanyFactory`, `GeneralBusinessFactory` | Creation of `Business` objects |
| `model` | `Company`, `Business` and its subclasses, `Department`, `Worker`, `WorkerTeam`, `Cook`, `Cashier`, `Designer`, `Itist` | Domain objects and business rules |
| `output` | `Output`, `ConsoleOutput`, `OutputDecorator`, `TimestampOutput`, `PrefixOutput` | Everything about where and how text is written |
| (default) | `Main` | Reads user input and calls the facade |

Dependencies flow in one direction only, so there are no cycles:
`Main` uses `facade`; `facade` uses `factory`, `model` and `output`; 
`factory` uses `model`; and `model` uses `output`. The `facade` package is 
separate from `model` on purpose. The facade depends on the factories, and the 
factories depend on the model, so placing the facade inside `model` would make the 
two packages depend on each other.

### 2.2 Recap: the creational patterns

The structural patterns build on the first three, which are summarised here.

- **Singleton.** `Company` is a `final` class with a private constructor 
and a `volatile` static instance obtained through `getInstance()`, which uses 
double-checked locking. Its `registerBusiness` and `printBusinesses` methods are 
`synchronized`, so both the creation of the instance and its state are thread-safe.
- **Factory Method.** `BusinessFactory` is an abstract creator with the `final` 
template method `createBusiness(...)` and the abstract factory method 
`createBusinessInstance()`. Each concrete factory returns its own product 
(`Restaurant`, `GameShop`, `ITCompany`, `GeneralBusiness`), and each product sets its
own type and workers.
- **Builder.** `Department` has a private constructor and an immutable set of fields.
`Department.Builder` collects the required values (name, number of workers) and the
optional ones (manager, budget, social media) through fluent setters, and `build()` 
validates and creates the object.

### 2.3 Composite: `Worker` and `WorkerTeam`

**Problem.** A `Business` stores its employees in a list. A flat list cannot express 
a real workplace, where employees belong to teams and teams contain smaller teams.
A "Kitchen team" is not a single worker, but the business should still be able to ask
it to work.

**Solution.** The Composite pattern lets individual objects and groups of objects
be handled through the same interface. In this project:

- `Worker` is the **component** interface. It declares `doWork(Output output)`.
- `Cook`, `Cashier`, `Designer` and  the new classes `DevOps`, `FullStackEngineer`,
`TechnicalArchitect`, `BusinessAnalyst`, and `ProductManager` are the **leaves**. 
- ach implements `doWork` by printing its own message.
- `WorkerTeam` is the **composite**. It implements `Worker`, holds a `List<Worker>` 
of members (leaves or other teams), and implements `doWork` by announcing the team 
and then asking every member to work.

`Business` did not have to change to support this. It still stores a `List<Worker>`
and calls `doWork` on each element, without knowing whether an element is a single 
worker or a team. The product classes use the pattern when they are constructed:

- `Restaurant` has a *Kitchen* team with two `Cook`s, plus a `Cashier`.
- `ITCompany` has a *Product team* that contains a *Development* team. This is a 
team inside a team, which is exactly the situation a flat list cannot represent.

`WorkerTeam.add(...)` returns the team itself so that members can be chained,
and it rejects `null` and a team that tries to contain itself.

**Result.** Any depth of nesting can be built without modifying `Business`, `Company` 
or the existing worker classes. A new kind of worker only needs to implement `Worker`.

### 2.4 Decorator: `OutputDecorator`, `TimestampOutput`, `PrefixOutput`

**Problem.** The program already printed through the `Output` interface. The report 
needed extra features (timestamps, indentation), but modifying `ConsoleOutput` would
affect every use of it, and creating a subclass for every combination ("console with
timestamp", "console with timestamp and prefix", and so on) would not scale.

**Solution.** The Decorator pattern adds behaviour to an object by wrapping it in 
another object that has the same interface.

- `Output` is the **component** interface, with a single method `println(String)`.
- `ConsoleOutput` is the **concrete component** and writes to standard output.
- `OutputDecorator` is the abstract **decorator**. It implements `Output` and holds 
another `Output` in a protected field `wrapped`.
- `TimestampOutput` and `PrefixOutput` are **concrete decorators**. Each one modifies
the message (adds `[HH:mm:ss]`, or adds a fixed prefix) and then forwards it to the 
wrapped `Output`. Both leave empty lines untouched, so blank separator lines stay clean.

Because a decorator *is* an `Output`, it can be passed to any code that expects one.
`Company`, `Business` and the workers were not modified to support it. Decorators can
also be stacked in any order, for example
`new TimestampOutput(new PrefixOutput(console, "[LOG] "))`.

In `Main`, the final report is printed through `new TimestampOutput(console)`, while 
the interactive prompts use the plain console. `WorkerTeam` uses `PrefixOutput` 
internally: before asking its members to work, it wraps the output in a two-space 
prefix. Each nested team adds one more level, so the nesting of the Composite is 
visible in the output.

**Result.** New output features are added by writing one small class, with no change
to existing code.

### 2.5 Facade: `CompanyFacade`

**Problem.** In the earlier version, `Main` knew far too much about the system. 
To open a business it had to choose a factory, call `createBusiness`, register the 
result with the `Company` singleton, and then build departments with the `Builder`. 
The user-interface code was tightly coupled to the factories, the builder and the
singleton.

**Solution.** The Facade pattern provides one simplified interface to a group of
classes. `CompanyFacade` exposes three methods:

- `openBusiness(type, name, location, numWorkers, revenue)` selects the correct 
factory for the `BusinessType`, creates the business and registers it with the `Company`.
- `addDepartment(business, name, numWorkers, manager, budget, socialMedia)` uses
`Department.Builder` to create a validated department and adds it to the business.
- `printReport()` asks the `Company` to print all businesses through the output it was
given.

`BusinessType` is a small enumeration (`RESTAURANT`, `GAME_SHOP`, `IT_COMPANY`, `OTHER`)
that replaces raw menu strings in the facade's interface.

`Main` now only reads input, converts menu choices to values, and calls the facade.
It no longer imports the `factory` package. It also handles invalid input: 
numbers are re-requested until valid, and `IllegalArgumentException`s thrown by the 
facade are caught and reported to the user.

**Result.** The way businesses are created or registered can change inside the facade
without touching `Main`. The facade is also easy to reuse from a different user interface.

### 2.6 How the patterns work together

The six patterns are not isolated. They cooperate in a single flow:

1. `Main` creates a `CompanyFacade` and gives it a `TimestampOutput` that wraps the 
console (**Facade**, **Decorator**).
2. The facade obtains the `Company` singleton and uses a concrete factory to create
a business (**Singleton**, **Factory Method**).
3. The product's constructor assembles its workers into teams (**Composite**).
4. Departments are built with `Department.Builder` and attached to the business 
(**Builder**).
5. When the report is printed, each team indents its members through a `PrefixOutput`,
and every line receives a timestamp (**Composite**, **Decorator**).

A real run produces the following report:

<a href="https://imgbb.com/"><img src="https://i.ibb.co/Hwjh3DB/tmps-output.png" alt="tmps-output" border="0" /></a>

The timestamps come from the Decorator, and the indentation and nesting come from 
the Composite combined with a second Decorator.

----

## 3. UML Diagrams

The diagrams are written in Mermaid, which GitHub, GitLab, VS Code and most Markdown viewers render automatically. One diagram is given for each structural pattern.

### 3.1 Composite

<a href="https://ibb.co/8Ly09DjN"><img src="https://i.ibb.co/hJGyVRKL/composite.png" alt="composite" border="0" /></a>

`WorkerTeam` both implements `Worker` and contains `Worker`s. This recursive relationship
is the defining feature of the Composite pattern.

### 3.2 Decorator

<a href="https://ibb.co/mCMPX24t"><img src="https://i.ibb.co/kgWP9wX0/decorator.png" alt="decorator" border="0" /></a>

`OutputDecorator` is at the same time an `Output` (inheritance) and the holder of an 
`Output` (aggregation). This is what allows decorators to be stacked.

### 3.3 Facade

<a href="https://ibb.co/39M65RKv"><img src="https://i.ibb.co/9kVKmZPh/facade.png" alt="facade" border="0" /></a>
`Main` depends only on `CompanyFacade` and `BusinessType`. The factories, the builder and 
the singleton are hidden behind the facade.

----

## 4. Conclusion

Adding Composite, Decorator and Facade to Company Manager made the design more flexible without rewriting it. Each pattern solved a specific problem and reused something that already existed:

| Pattern | Problem in the earlier design | What changed | What it gained |
|---|---|---|---|
| Composite | A flat list of workers could not express teams or nested teams | `WorkerTeam` implements `Worker` and contains other `Worker`s | Arbitrary team structures with no change to `Business` |
| Decorator | Output features could only be added by editing `ConsoleOutput` or multiplying subclasses | `OutputDecorator`, `TimestampOutput`, `PrefixOutput` wrap any `Output` | New output behaviour by adding one small class, freely stackable |
| Facade | `Main` knew about factories, the builder and the singleton | `CompanyFacade` exposes three simple methods | A decoupled, simpler user-interface layer |

Together with the creational patterns, the project now demonstrates six patterns that 
cooperate: the facade uses the factories, the builder and the singleton; the composite 
relies on a decorator to show its nesting; and the decorator works anywhere an `Output` 
is accepted.