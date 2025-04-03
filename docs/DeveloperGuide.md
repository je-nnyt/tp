# Developer Guide

### Table of Contents
- [Design](#design)
  - [Architecture](#architecture)
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
The architecture of the application is designed to be modular and easy to maintain.  
The main components are:

<img src="uml/architecture.png" alt="architecture-uml" width="300" height="300"/>  

The interaction for any command is similar to the following, for simplicity, we will use `add trip` as an example:

<img src="uml/main_flow.png" alt="main-flow-uml"/>

### Implementation

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

#### PlantUML

PlantUML is the software/code I used to generate the uml diagram.

##### How to use

1. Download the PlantUML plugin in your own IDE, it is available for both IntelliJ and VSCode
> ℹ️ Make sure you have also download the graphviz
> Check it by typing `brew info graphviz` in linux environment terminal
> Install it by typing `brew install graphviz` in linux environment terminal
2. Please refer to the official documents to start code it, you can check it [here](https://plantuml.com/en-dark/class-diagram). It is a generally powerful and easy tool not only to general uml, you may check there main page for more information
3. Make sure you end the uml document with `.puml` but  **not** `.uml`
