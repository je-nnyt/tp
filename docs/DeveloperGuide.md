# Developer Guide

## Acknowledgements
[AddressBook-Level3 developer guide](https://se-education.org/addressbook-level3/DeveloperGuide.html#proposed-undoredo-feature)

### Table of Contents
- [Design](#design)
- [Implementation](#implementation)
    - [Storage [Proposed]](#storage-proposed)
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

### Design

#### Architecture

### Implementation

#### list transportation

The list transportation feature is implemented by Trip class and is executed by calling `executeListTransportation` from `VoyaTrip`.
The `executeListTransportation` receives a Transportation command as a parameter.
Note: The user may need to call listTransportations to view the associated index to each transportation prior to using this feature

The list transportation is implemented as followed:
- Accepts `command.getIndex()` as a parameter
- Calls the get method of the transportations array list of the current trip and takes `index - 1` as a parameter to account for the array list starting at position 0
- Calls `toString()` method of the retrieved transportation to print its associated information
- Handles any potential error related to IndexOutOfBounds by throwing a `TransportationException` and printing an error message


#### modify transportation

The modify transportation is implemented by `Trip` class and is executed by calling `executeModifyTransportation` from `VoyaTrip`.
The executeModifyTransportation receives a Transportation command as a parameter.

The modify transportation is implemented as followed:

- Accepts `command.getName()`, `command.getMode()`, `command.getBudget()` as parameters
- Assigns the attributes of the transportation object to the new values using transportation setters function
- Prints a confirmation message with the updated transportation information
- Throws a `TransportationException` if the budget is a negative value

#### Storage [Proposed]

### Git Workflow

#### Branch

Please try to use consistent branch name style if applicable
- developing a new feature: `dev/<issueNumber>-<description>`, ie `dev/8-add-delete-trip
- fixing a bug: `fix/<issueNumber>-<description>`, ie `fix/8-incorrect-output`

### Code Standard

Basically follow the same as cs2113 guideline yet here will have some more specific guideline if not found in cs2113 website.

> ℹ️ This documentation will only add new thing from time to time, but will **not** change pre-defined standard, hence please check for any updates from time to time. (deprecated)

> ℹ️ For easy workflow, this issue's name will ends with last updated date for the reference.
> And the latest update will be denoted by * in table of contents. (deprecated)

#### Primitive Type

Please use encapsulated primitive type, ie `Integer` instead of `integer`

#### Switch

Please use the lambda style of switch statement to avoid the tedious break statement, ie

```java
switch (x) {
case "a" -> System.out.println("a");
case "b" -> System.out.println("b");
default -> System.out.println("others");
}
```

### Documentation Standard

#### important notes

- please dont **remove** the old documentation, but instead **~~strikethrough~~** or append (deprecated) at the end
- after update the documentation, please make sure to list the latest version on the [table of contents](#table-of-contents), ie Code Standard (15 Mar)

### Appendix
## Product scope
### Target user profile

{Describe the target user profile}

- has a list of trips to manage
- can type fast
- comfortable with CLI
- prefers typing over using mouse

### Value proposition

helps the users who prefer CLI-based tools track their travel plans and budget

## User Stories

|Version| As a ... | I want to ...             | So that I can ...|
|--------|----------|---------------------------|------------------|
|v1.0|new user| add a trip                |refer to them when I forget how to use the application|
|v2.0|user| find a to-do item by name |locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}

#### PlantUML

PlantUML is the software/code I used to generate the uml diagram.

##### How to use

1. Download the PlantUML plugin in your own IDE, it is available for both IntelliJ and VSCode
> ℹ️ Make sure you have also download the graphviz
> Check it by typing `brew info graphviz` in linux environment terminal
> Install it by typing `brew install graphviz` in linux environment terminal
2. Please refer to the official documents to start code it, you can check it [here](https://plantuml.com/en-dark/class-diagram). It is a generally powerful and easy tool not only to general uml, you may check there main page for more information
3. Make sure you end the uml document with `.puml` but  **not** `.uml`
