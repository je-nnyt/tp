# Developer Guide (7 Apr)

## Table of Contents
<!-- TOC -->
* [Developer Guide (7 Apr)](#developer-guide-7-apr)
  * [Table of Contents](#table-of-contents)
* [Acknowledgements](#acknowledgements)
  * [Get Started](#get-started)
    * [Setting up](#setting-up)
* [Design](#design)
  * [Architecture](#architecture)
    * [Storage Component](#storage-component)
    * [Ui Component](#ui-component)
    * [Logic Component](#logic-component)
    * [Model Component](#model-component)
* [Implementation](#implementation)
  * [Storage [Implemented]](#storage-implemented)
    * [How it works](#how-it-works)
      * [Loading](#loading-)
      * [Saving](#saving-)
    * [UML Sequence Diagram](#uml-sequence-diagram)
  * [Modify Accommodation [Implemented]](#modify-accommodation-implemented)
    * [Implementation Details](#implementation-details)
    * [Example Workflow](#example-workflow)
      * [Scenario](#scenario)
      * [Input Arguments](#input-arguments)
      * [Execution](#execution)
  * [Modify Transportation [Implemented]](#modify-transportation-implemented)
    * [Implementation Details](#implementation-details-1)
  * [List Transportation [Implemented]](#list-transportation-implemented)
    * [Implementation Details](#implementation-details-2)
    * [Execution](#execution-1)
* [Git Workflow](#git-workflow)
* [Appendix A: Product Scope](#appendix-a-product-scope)
  * [Target user profile](#target-user-profile)
  * [Value Proposition](#value-proposition)
* [Appendix B: User Stories](#appendix-b-user-stories)
* [Appendix C: Non-Functional Requirements](#appendix-c-non-functional-requirements)
* [Appendix D: Glossary](#appendix-d-glossary)
* [Appendix E: Instructions for manual testing](#appendix-e-instructions-for-manual-testing)
  * [PlantUML](#plantuml)
    * [How to use](#how-to-use)
* [Documentation Standard](#documentation-standard)
  * [Important notes](#important-notes)
  * [Branch](#branch)
* [Code Standard](#code-standard)
  * [Primitive Type](#primitive-type)
  * [Switch](#switch)
<!-- TOC -->

# Acknowledgements
- [PlantUML](https://plantuml.com/) for the UML diagrams
- [JSON-java](https://github.com/stleary/JSON-java/tree/master?tab=readme-ov-file) for json formatting

## Get Started

### Setting up

> ℹ️ This section is only for the developer who want to run the project locally, 
> if you are not a developer, please refer to [User Guide](UserGuide.md) instead.

> ℹ️ Logger information is hidden by default,
> if you wish to enable logger for debugging purpose, 
> please pass argument `--verbose` when running the jar file.
> ie java -jar VoyaTrip.jar --verbose

## Design

### Architecture
The architecture of the application is designed to be modular and easy to maintain.  

**Main Components**:

> ℹ️ The components referring here are not particular classes 
> instead high level ideas or packages we can say

- **Main**: The entry point of the application, 
responsible for initializing the application and handle program specific parameters
- **Ui**: The user interface component,
handle all the user inputs and program outputs
- **Storage**: The storage component,
handle all the file reading and writing
- **Logic**: The logic component,
handle all the actual execution of the commands
- **Model**: The model component,
handle all the data structure and data manipulation

Simple diagram is included to show the relationship among these components.

<img src="uml/architecture.png" alt="architecture-uml" width="300" height="300"/>  

The interaction for any command is similar to the following, for simplicity, we will use `add trip` as an example:

#### Storage Component

<img src="uml/storage_package.png">

#### Ui Component

<img src="uml/ui_package.png">

#### Logic Component

#### Model Component

<img src="uml/main_flow.png" alt="main-flow-uml"/>

## Implementation
This section describes some notable details on how certain features are implemented/going to be implemented.

### Storage [Implemented]

- This is implemented by using the `Storage` class, 
which is responsible for reading and writing data to and from the file system.

- Diagram is included at the end to show the relationship
between the `Storage` class and other classes in the system.

> ℹ️ The `Storage` class is implemented with external library `org.json`
> to handle the JSON file format.

#### How it works

##### Loading 

1. The `Storage` class reads the JSON file from the file system.
2. It parses the JSON data into Java objects using the `org.json` library.
3. The parsed data is then passed into static fromJson() of 
`TripList` which then invoke the same methods of related `Trip`, `Day`, `Activity`, `Transportation` and `Accommodation` classes 
to create the corresponding objects recursively.
4. The created objects are then returned to the `Main` class,
and being assigned to the `tripList` variable if needed.

> ⚠️ This method will return a `Optional<TripList>` object 
> instead of the triplist directly.

##### Saving 

1. `TripList` object is passed into the `Storage` class.
2. The `Storage` class then calls `toJson()` method of `TripList` class, 
which again invoke the same methods of the related objects to 
return a JSON object 
3. The JSON object is then converted to string 
with indentation of 4 spaces for better json format

> ⚠️ This method will create/ overwrite the file if it does not exist,
> and will delete the files if the triplist object is empty.

#### UML Sequence Diagram
<img src="uml/storage.png"/> 

### Modify Accommodation [Implemented]
The 'modify accommodation' feature is facilitated by calling `modifyAccommodation` in the `Trip` class, which is responsible 
for updating the details of existing accommodation in the `accommodations` list, based on the modifications provided. 
Here is the overview of the whole modification operation:

<img src="uml/modify_accommodation_interaction.png"/>

#### Implementation Details

1. **Role of `Parser`**:
  - The `Parser` class takes the input from the user and transforms it into a `AccommodationCommand` object using the `parse` method.

2. **Role of `handleCommand`**:
  - After receiving the `AccommodationCommand` object, `handleCommand(command)` is called within the `Executor`, which determines the appropriate action (like invoking `modifyAccommodation`).

3. **Role of `Trip`**:
  - The `modifyAccommodation(...)` method is called to perform the changes to the trip accommodation data.

4. **`Ui` Feedback**:
  - After all successful updates, the `Trip` class calls the `printModifyAccommodationMessage` method on the `Ui` class
      to provide clear visual feedback to the user about the successful modification.

#### Example Workflow of `modifyAccommodation` in `Trip`

##### Scenario:
A trip named 'My Trip' has three accommodations stored in the `accommodations` list. We want to modify the second accommodation by:
- Changing its name to `"Courtyard Resort"`.
- Updating its budget to `$500`.
- Leaving the booked days unchanged (`null` for `accommodationDays`).

##### Input Arguments:
```
~/My Trip/Accommodation >
modify accom --index 2 --n Courtyard Resort --b 500
```
##### Execution:
1. `accommodationName != null`: Updates the name to `"Courtyard Resort"` by calling `setName` of the accommodation.
2. `accommodationBudget != null`: Updates the budget to `500` by calling `setBudget` of the accommodation.
3. `accommodationDays == null`: Days remain unchanged.
4. After modifications, `Ui.printModifyAccommodationMessage` is called to display a confirmation message.

### Modify Transportation [Implemented]
The modify transportation feature is facilitated by calling `executeModifyTransportation` who in turns calls `modifyTransportation`.
This feature allows the user to modify the information of any given transportation from the transportation list of a given trip.

#### Implementation Details
- The user is required to provide the associated transportation index and at least one parameter they wish to modify . 
- The user may perform `list transportaion -- all` to view all the transportations, and their associated index, contained in the current trip.
- Logging messages are printed  before and after modifying each desired parameter.
- If the user modifies the day parameter, `validateTransportationDay` is called to verify that the day is within the trip duration and is greater than 1.
  If the day is not validated, `InvalidDayException` is thrown

### List Transportation [Implemented]
The list transportation feature is facilitated by calling the `executeListTransportation` who in turns calls `listTransportation`.
This listing feature allows the user to list a transportation information by simply inputting its associated index or by its name. 
This feature also enables the user to list all the transportations and their index from a given trip.

#### Implementation Details
- The user is required to provide the associated transportation index or name to list the transportation information.
- The user may provide `list transportation -- all` to view all the transportations, and their associated index, contained in the current trip.
- The user may perform `list transportaion -- all` to view all the transportations, and their associated index, contained in the current trip.
- Logging messages are printed before the operation and upon successful completion
- Prior to calling the `printListTransportation` method, depending on the provided parameter, 
  the program verifies if the transportation at a given index or name exists in the list of transportation.
- If the transportation is not found, `InvalidIndexException` is thrown in the case that the index is invalid, 
  TransportationNotFoundException in the case that the provided name does not exist


## Git Workflow

## Appendix A: Product Scope
### Target user profile
This product is designed for exchange students whom 
- has a desire to manage the important details of their trips, including budgeting
- prefer desktop program over other types
- is comfortable using the command-line interface (CLI) 
- can type fast on a keyboard

### Value Proposition
This product provides a simple and efficient way for an exchange student to manage the important details of their trips which includes 
information on its itinerary, transportation, accommodation and budget.

## Appendix B: User Stories

| Version | As a ...       | I want to ...                                | So that I can ...                                        |
|---------|----------------|----------------------------------------------|----------------------------------------------------------|
| v1.0    | user           | add a trip                                   | keep track of the trip information                       |
| v1.0    | user           | add an activity                              | keep track of the activities                             |
| v1.0    | user           | add a transportation                         | keep track on how I will be travelling                   |
| v1.0    | user           | add an accommodation                         | keep track on where I will be staying during my trip     |
| v1.0    | user           | delete a trip                                | remove any cancelled trips                               |
| v1.0    | user           | delete an activity                           | remove any cancelled activities                          |
| v1.0    | user           | delete a transportation                      | remove any cancelled transportation                      |
| v1.0    | user           | delete an accommodation                      | remove any cancelled accommodation                       |
| v2.0    | forgetful user | see the itinerary                            | view the itinerary of the trip and all related activities |
| v2.0    | user           | see the accommodation                        | view the accommodations of a given trip       |
| v2.0    | user           | see the transportation                       | view thetransportation of a given trip       | |
| v2.0    | user           | modify the accommodation                     | update it with any new information                       |
| v2.0    | user           | modify the transportation                    | update it with any new information                       |
| v2.0    | user           | modify the trip                              | update it with any new information                       |
 v2.0     | user           | include a day to my transportation           | keep track of my transportation dates                    |
| v2.0    | forgetful user | include a day to my accommodation            | keep track of accommodation dates                        |
| v2.1    | user           | view the activities in a chronological order | organize my trip itinerary                 |
| v2.1    | user           | see all my trips                             | keep track of all my trips and their related cost        |
| v2.1    | forgetful user | save the trips                               | access my trips' information at anytime                  |
|

## Appendix C: Non-Functional Requirements

* Should work on any _mainstream_ OS as long as it has Java `17` or above installed
* Should be packaged as a JAR file
* User should have an average typing speed 
* Concise error messages are provided when wrong commands are entered
* Should handle unexpected inputs without crashing
* Should provide logging and debugging capabilities


## Appendix D: Glossary

* *Multistep command* - A feature which requires the user to go through several steps to complete.
* 

## Appendix E: Instructions for manual testing
This section provides instructions for manual testing of VoyaTrip

1. Launch the program
   1. Download the jar file
   2. Open a terminal and run the following command `java -jar JAR_FILE_NAME.jar`
2. Enter the following test cases, one by one
  - `add trip --n vietnam --s 1-2 --e 1-3 --b 1000`
  - Expected: Display trip information

  - `list trip --all`
  - Expected: Display the trip information and its index

  - `cd trip --i 1`
  - Expected: The current directory is now the newly added trip

  - `add activity --n Sand Dunes --d 1 --t 6:00`
  - Expected: Display the activity information

  -  `list --n all`
  - Expected: Display the itinerary for each day 

  - `add transportation --n Scoot --m air --d 1 --b 200`
  - Expected: Display the transportation information

  - `cd accommodation `
  - Expected: The current directory is now Accommodation

  - `add  --n Hotel M --s 1 --e 2 --b 100`
  - Expected: Display the newly added accommodation information

  -  `list transportation --all`
  - Expected: Display all the transportation under current trip with their index

  - `delete transportation --i 1`
  - Expected: Display the information of the deleted transportation

  -  `modify accommodation --i 1 --n Hotel Mao` 
  - Expected: Display the information of the newly modified accommodation
  

### PlantUML

PlantUML is the software/code I used to generate the uml diagram.

#### How to use

1. Download the PlantUML plugin in your own IDE, it is available for both IntelliJ and VSCode
> ℹ️ Make sure you have also download the graphviz
> Check it by typing `brew info graphviz` in linux environment terminal
> Install it by typing `brew install graphviz` in linux environment terminal
2. Please refer to the official documents to start code it, you can check it [here](https://plantuml.com/en-dark/class-diagram). It is a generally powerful and easy tool not only to general uml, you may check there main page for more information
3. Make sure you end the uml document with `.puml` but  **not** `.uml`


## Documentation Standard

### Important notes

- Please don't **remove** the old documentation, but instead **~~strikethrough~~** or append (deprecated) at the end
- After update the documentation, please make sure to list the latest version on the [table of contents](#table-of-contents)

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