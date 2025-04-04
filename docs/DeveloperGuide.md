# Developer Guide

## Table of Contents
- [Design](#design)
  - [Architecture](#architecture)
- [Implementation](#implementation)
    - [Storage [Proposed]](#storage-proposed)
    - [Modify Accommodation [Implemented]](#modify-accommodation-implemented)
- [Git Workflow (15 Mar)](#git-workflow)
    - [Branch](#branch)
- [Code Standard (15 Mar)](#code-standard)
    - [Primitive Type](#primitive-type)
    - [Switch](#switch)
- [Documentation Standard (3 Apr)](#documentation-standard)
    - [Sytnax](#sytnax)
- [Appendix](#appendix)
    - [PlantUML](#plantuml)
        - [How to use](#how-to-use)

## Design

### Architecture
The architecture of the application is designed to be modular and easy to maintain.  
The main components are:

<img src="uml/architecture.png" alt="architecture-uml" width="300" height="300"/>  

The interaction for any command is similar to the following, for simplicity, we will use `add trip` as an example:

<img src="uml/main_flow.png" alt="main-flow-uml"/>

## Implementation
This section describes some notable details on how certain features are implemented/going to be implemented.

### Storage [Proposed]
This is purposed to save the trip data in a file, 
so that the data can be retrieved when the program is restarted.
<img src="uml/storage.png"/> 

### Modify Accommodation [Implemented]
The 'modify accommodation' feature is facilitated by calling `modifyAccommodation` in the `Trip` class, which is responsible 
for updating the details of existing accommodation in the `accommodations` list, based on the modifications provided.

### Implementation Details
1. **Index Adjustment**:
  - The code adjusts the user-provided 1-based `index` (common for user interfaces) to the 0-based indexing of Java lists via `index - 1`.

2. **Logger Messages**:
  - The `logger.log` statements keep track of the progress of each modification for debugging and auditing purposes.
  - **INFO level** logs are used for successful operations, while **WARNING level** logs are added for any potential issues, like an invalid index.

3. **Accommodation Updates**:
  - Each modification operates on the corresponding `Accommodation` object in the `accommodations` list, invoking setters like `setName`, `setBudget`, and `setDays`, which ensure encapsulated updates to the object's state.

4. **Error Handling**:
  - An invalid index (e.g., `index < 1` or `index > accommodations.size()`) triggers an `IndexOutOfBoundsException`, which is caught and handled by throwing a custom `InvalidCommand` exception. This approach provides a cleaner, more user-friendly error interface for clients of this method.

5. **UI Feedback**:
  - After all successful updates, the `Ui.printModifyAccommodationMessage` call provides clear visual feedback to the user about the successful modification.

### Example Workflow
#### Scenario:
A trip named 'My Trip' has three accommodations stored in the `accommodations` list. We want to modify the second accommodation by:
- Changing its name to `"Resort Paradise"`.
- Updating its budget to `$500`.
- Leaving the booked days unchanged (`null` for `accommodationDays`).

#### Input Arguments:
```
~/My Trip/Accommodation >
modify accom --index 2 --n Resort Paradise --b 500
```

#### Execution:
1. `accommodationName != null`: Updates the name to `"Resort Paradise"` by calling `setName` of the accommodation.
2. `accommodationBudget != null`: Updates the budget to `500` by calling `setBudget` of the accommodation.
3. `accommodationDays == null`: Days remain unchanged.
4. After modifications, `Ui.printModifyAccommodationMessage` is called to display a confirmation message.


## Git Workflow

### Branch

Please try to use consistent branch name style if applicable
- developing a new feature: `dev/<issueNumber>-<description>`, ie `dev/8-add-delete-trip
- fixing a bug: `fix/<issueNumber>-<description>`, ie `fix/8-incorrect-output`

## Code Standard

Basically follow the same as cs2113 guideline yet here will have some more specific guideline if not found in cs2113 website.

> ℹ️ This documentation will only add new thing from time to time, but will **not** change pre-defined standard, hence please check for any updates from time to time. (deprecated)

> ℹ️ For easy workflow, this issue's name will ends with last updated date for the reference.
> And the latest update will be denoted by * in table of contents. (deprecated)

### Primitive Type

Please use encapsulated primitive type, ie `Integer` instead of `integer`

### Switch

Please use the lambda style of switch statement to avoid the tedious break statement, ie

```java
switch (x) {
case "a" -> System.out.println("a");
case "b" -> System.out.println("b");
default -> System.out.println("others");
}
```

## Documentation Standard

### important notes

- please dont **remove** the old documentation, but instead **~~strikethrough~~** or append (deprecated) at the end
- after update the documentation, please make sure to list the latest version on the [table of contents](#table-of-contents), ie Code Standard (15 Mar)

## Appendix

### PlantUML

PlantUML is the software/code I used to generate the uml diagram.

#### How to use

1. Download the PlantUML plugin in your own IDE, it is available for both IntelliJ and VSCode
> ℹ️ Make sure you have also download the graphviz
> Check it by typing `brew info graphviz` in linux environment terminal
> Install it by typing `brew install graphviz` in linux environment terminal
2. Please refer to the official documents to start code it, you can check it [here](https://plantuml.com/en-dark/class-diagram). It is a generally powerful and easy tool not only to general uml, you may check there main page for more information
3. Make sure you end the uml document with `.puml` but  **not** `.uml`
