# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}
### VoyaTrip Class

### Trip Class

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


### Transportation Class


## Product scope
### Target user profile

{Describe the target user profile}

- has a list of trips to manage
- can type fast
- comfortable with CLI
- prefers typing over using mouse

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
