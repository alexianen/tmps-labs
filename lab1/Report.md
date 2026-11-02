# Lab 1: Adding Singleton, Builder and Factory Method

----

## 1. Introduction
In this laboratory work, I have continued using the
Company Manager idea from the previous 
lab. More specifically, I added/improved
3 design patterns: Singleton, Builder
and Factory Method. In the previous lab
I had a very basic/primitive version of the
Singleton and Factory Method, which I improved
here, and I added the Builder design pattern.

The purpose of this project is to model a simple
company management system where a
user can register businesses,
each business can contain multiple departments, and each department 
can have a manager, social media site,
workers, etc.
When the user finishes, the program
prints a summary of every business
and runs the work of each business's
employees.

The implemented design patterns can be
explained more in detail:

- Singleton, which guarantees
a single `Company` that owns all businesses.
- Factory Method, which lets each
type of business be created by its own factory.
- Builder, which assembles `Department`
objects step by step from required and optional values.

Besides these three, the design relies on
several supporting ideas: a template
method inside the factory, polymorphism through
the `Worker` interface, an `Output` abstraction that 
separates the model from the console, 
and validation inside the domain
classes. This report describes how 
the code is organised, how each pattern
is implemented, and why those choices
were made.

---

## 2. Architecture


### 2.1 Package structure

The code is split into three packages plus an entry point.

| Package | Contents | Responsibility |
| --- | --- | --- |
| `model` | `Company`, `Business`, `Restaurant`, `GameShop`, `ITCompany`, `GeneralBusiness`, `Department`, `Worker`, `Cook`, `Cashier`, `Designer`, `Itist` | Domain objects and business rules |
| `factory` | `BusinessFactory`, `RestaurantFactory`, `GameShopFactory`, `ITCompanyFactory`, `GeneralBusinessFactory` | Creation of `Business` objects |
| `output` | `Output`, `ConsoleOutput` | Abstraction over where text is written |
| (default) | `Main` | User interaction: reads input and coordinates the other packages |

`Main` depends on all three packages, `factory` depends on `model`, and `model` depends only on `output`. There are no circular dependencies.

### 2.2 Singleton: `Company`

`Company` is the root of the application: it holds the list of every
registered business. Only one such object makes sense, so it is implemented
as a Singleton.

- The class is `final` and its constructor is `private`, so it cannot be subclassed or instantiated from outside.
- The single instance is stored in a `private static volatile` field and obtained through `getInstance()`.
- `getInstance()` uses **double-checked locking**: the field is checked once without a lock for speed, then checked again inside a `synchronized` block so that only one thread can create the instance. The `volatile` keyword is essential here, because without it another thread could observe a partially constructed object.
- The mutable state (the list of businesses) is protected as well: `registerBusiness` and `printBusinesses` are `synchronized`, so thread safety covers the data and not only the creation of the instance.
- `registerBusiness` rejects `null`, so the list can never hold an invalid entry.

The benefit is a single, globally reachable access point. `Main` and any other class obtain the same `Company` through `Company.getInstance()` and never pass it around manually.

### 2.3 Factory Method: `BusinessFactory` and its subclasses

The application has several kinds of business that share a common structure but differ in their type label and in the employees they start with. Creating them is delegated to the Factory Method pattern.

- **Product:** `Business` is an abstract class. Its concrete subclasses are `Restaurant` (a `Cook` and a `Cashier`), `GameShop` (a `Cashier`), `ITCompany` (an `Itist` and a `Designer`) and `GeneralBusiness` (no employees). Each subclass sets its own type in its constructor.
- **Creator:** `BusinessFactory` is an abstract class declaring the factory method `protected abstract Business createBusinessInstance()`.
- **Concrete creators:** `RestaurantFactory`, `GameShopFactory`, `ITCompanyFactory` and `GeneralBusinessFactory` each override the factory method to return their own product.

The public method `createBusiness(name, location, numWorkers, revenue)` is declared `final` and acts as a **Template Method**. It fixes the creation steps (create the instance, then set name, location, worker count and revenue) while leaving the one varying step, which concrete class to instantiate, to the subclass. Because it is `final`, subclasses can change *what* is created but not *how* it is initialised.

Making `Business` abstract strengthens the design: a bare `Business` cannot be created, so a factory is the only route to a business object. `Main` chooses a factory from the user's menu choice and then works only with the `BusinessFactory` and `Business` types. Adding a new kind of business means adding one product class and one factory, with no change to the existing ones.

### 2.4 Builder: `Department`

A department has two essential values (its name and the number of workers it may hold) and three optional ones (manager, budget and social media). Using a constructor with five parameters would be hard to read and error-prone, so `Department` uses the Builder pattern.

- `Department` has a `private` constructor, so objects can only be created through `Department.Builder`, a `static` nested class.
- Each builder method (`depName`, `manager`, `numWorkers`, `budget`, `socialMedia`) stores a value and returns the builder, giving a readable fluent call chain that ends in `build()`.
- All fields of `Department` are `final`, so a department is **immutable** once built.
- Validation happens when the object is constructed: the name must not be empty, and the worker count and budget must not be negative. An invalid department can therefore never exist.
- Optional text fields default to an empty string in the builder, so printing code does not have to deal with missing values in the normal case.

In `Main`, a department is built with all five values and then passed to `Business.addDepartment`. The whole operation sits inside a `try` block, so a validation failure is reported to the user instead of terminating the program.

### 2.5 Supporting design decisions

**Polymorphism through `Worker`.** `Worker` is an interface with a single method, `doWork()`. `Cook`, `Cashier`, `Designer` and `Itist` implement it. A `Business` stores a `List<Worker>` and calls `doWork()` on each element without knowing the concrete type. The factory products decide which workers each type of business starts with.

**Output abstraction.** `Output` is an interface with `println(String)`, and `ConsoleOutput` implements it by writing to standard output. `Company` and `Business` print through `Output` instead of calling `System.out` directly, so the model is independent of the console and could be tested with a different implementation. This follows the dependency inversion principle: high-level classes depend on an abstraction, not on a concrete device.

**Encapsulation and validation.** Fields in `Business` are private. Validation lives in the setters (non-empty name and location, non-negative worker count and revenue), so every way of changing a business goes through the same rules. The worker-count setter also refuses to go below the number of workers already assigned to departments, and `addDepartment` refuses to exceed the business's worker count. These rules keep the object consistent at all times.

**Composition.** A `Business` owns its departments and workers as lists, and `Company` owns its businesses. Behaviour such as printing a summary is delegated down this chain: `Company` asks each `Business` to print itself, and each `Business` reports its departments.

### 2.6 Typical execution flow

1. `Main` obtains the `Company` singleton.
2. The user chooses a business type; `Main` instantiates the matching `BusinessFactory` subclass.
3. `createBusiness(...)` creates the product through the factory method and applies the validated values.
4. The new business is registered with `Company`.
5. For each department the user adds, `Department.Builder` constructs an immutable `Department`, which `Business.addDepartment` checks against the worker limit.
6. At the end, `Company.printBusinesses(output)` prints every business with its departments and runs each business's workers.


----

## 3. UML Diagram
The diagram after the table draws the classes of the application and their relationships. The main relationships are:

| Relationship | Meaning |
| --- | --- |
| `Company` 1 → \* `Business` | The singleton holds all registered businesses (aggregation) |
| `Business` 1 → \* `Department` | A business owns its departments (composition) |
| `Business` 1 → \* `Worker` | A business holds its workers (aggregation) |
| `Restaurant`, `GameShop`, `ITCompany`, `GeneralBusiness` ▷ `Business` | Concrete products extend the abstract product |
| `Cook`, `Cashier`, `Designer`, `Itist` ▷ `Worker` | Workers implement the interface |
| `RestaurantFactory`, `GameShopFactory`, `ITCompanyFactory`, `GeneralBusinessFactory` ▷ `BusinessFactory` | Concrete creators extend the abstract creator |
| `BusinessFactory` ⇢ `Business` | The factory method returns a `Business` |
| `RestaurantFactory` ⇢ `Restaurant` (and so on) | Each concrete factory instantiates its own product |
| `Department.Builder` ⇢ `Department` | The nested builder creates the immutable product |
| `ConsoleOutput` ▷ `Output` | The console implementation of the output interface |
| `Company`, `Business` ⇢ `Output` | The model prints through the abstraction |

&#91;embedded content: UML class diagram · Singleton, Factory Method and Builder highlighted\]

`Company`, `BusinessFactory` and `Department.Builder` are the cores of the Singleton, Factory Method and Builder patterns. Each concrete creator instantiates the concrete product of the same name.

<a href="https://ibb.co/gL4Rz2hP"><img src="https://i.ibb.co/sdWVyfz6/image1.png" alt="image1" border="0" /></a>

---

## 4. Conclusion
In this lab, we have demonstrated three creational patterns working together 
in a small but realistic domain. The Singleton gives the application one 
thread-safe `Company` as a single entry point. The Factory Method moves the 
choice of concrete business type out of the client and into dedicated creators,
with a final template method guaranteeing consistent initialisation. 
The Builder makes the construction of `Department` readable and produces
immutable, always-valid objects. There are a few improvements that can
be made or limitations removed, but that shall be for the future lab.
