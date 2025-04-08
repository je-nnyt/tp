# VoyaTrip User Guide

## Introduction

VoyaTrip is a command-line application for managing trips optimized for users that can type fast on a keyboard.
It also includes a budget system for keeping track of the budget of the trip.


## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `VoyaTrip` from  [Placeholder](https://).
3. Open a terminal and `cd` to where the jar file is located
4. Run the following command:  `java -jar JAR_FILE_NAME`
5. Enter any command listed from the features section
2. Download the latest version of `VoyaTrip` from our [release page](https://github.com/AY2425S2-CS2113-F14-3/tp/releases).

## Features

In VoyaTrip, you may store multiple trips. Do note that the name of each trip have to be unique.

Each trip have a budget, starting date and ending date.
 The total budget will be divided equally to all days by default for the creation of the trip. There are three components: `itinerary`, `transportation` and `accommodation`.

The `itinerary` contains lists of activities for all the days. This is for you to plan what to do during the trip. Each day will have a budget for you to keep track of.

A `trip` contain a list of `transportation`. This is for you to keep track of any important transportation during the trip like the flight. Each transportation will have a budget for you to adjust and keep track of.

A `trip` contain a list of `accommodation`. This is for you to keep track of the accommodation during the trip. Each accommodation will have a budget for you to adjust and keep track of.

The budget of a trip is the maximum budget you expect to have for the trip. It is not the sum of all the budgets of all the components. But instead, VoyaTrip will give a warning if the total sum of the budgets of all the components is greater than the budget of the trip. So that you can adjust the budgets of the components accordingly.

### The Directory System

The directory system is used to navigate between trips and different components (`ITINERARY`, `TRANSPORTATION` and `ACCOMMODATION`) of a trip. To make changes to the components of a trip, you have to be in the directory of the trip. 

Everytime the program starts at the `root`, shown in the program as: `~ >`. When working within a trip, the trip name and the target component is shown as: `~/TRIP_NAME/TARGET_NAME >`. For example: `~/My Trip/ITINERARY >`.

### Note about the commands

A command follows the format: `<action> <target> <argument(s)>`. The keywords are not case-sensitive.

A command action is what the command does, such as `add` and `delete`. 

A command target is what group does the command affects, such as `trip` and `transportation`. The target keyword may be omitted if the correct directory is at the corresponding target component. For example, if the current directory is `~/<trip name>/ITINERARY` and if `add` is entered without the target keyword, then since the only thing add can do in the current directory is to add an activity, the command target will be assumed to be `activity`.

An argument consist of a double hyphen (`--`) immediately followed by a keyword and a value: `--<keyword> <value>`. For example: `--name my trip` will have the keyword `name` and the value `my trip`. The order of the different arguments does not matter.

Note that some commands may not have a target or arguments. Also note that extra arguments that are not necessary or duplicated will be ignored.


### Changing Directory

The `cd` command is used to navigate between trips and different components.

#### Changing the current trip currently working on

Target: `trip`

Required arguments:
- `index` or `name`

Special case for changing to the root directory `cd ..`, `cd trip`, `cd --root` or `cd trip --n root`

Example of usage: change directory to trip named "my trip" with index 1

```
~ >
cd trip --index 1

~ >
cd --n my trip
```

Example of usage: change directory to the root directory
```
~/my trip/ITINERARY >
cd ..

~/my trip/ITINERARY >
cd trip
```

#### Changing the current component

Target: `itinerary`, `transportation` or `accommodation`

No required arguments

Example of usage: change directory to the `accommodation` component of the current trip

```
~/My Trip/ITINERARY >
cd accommodation

~/My Trip/ITINERARY >
cd accom
```
⚠️ Note that the user must be in the curren trip directory prior to changing to the current component

### Adding

`add` or `make` are commands that are used to add new items. Maybe shorten as `a` or `mk`.

#### Adding new trip

Format:

`add trip --name TRIP_NAME --start START_DATE --end END_DATE --budget TOTAL_BUDGET`

Required arguments:
- `name`
- `start`
- `end`
- `budget`

start date and end date should be in the format `d-M-yyyy` or `d-M` if the year is the current year. Note that entering 2-29 if the year is not leap year, the program will assume it means the last day of the month which is 2-28.

Name of the trip should be unique and cannot be "root" or "all".

Example of usage: 

```
~ >
add trip --name my trip --start 1-5 --end 7-5 --budget 1000

~ >
mk --n my trip --b 1000 --s 1-5 --e 7-5
```

> ⚠️ Note that the trip's duration should be smaller than 366 days.

### Adding new activity

Target: `activity`

Required arguments:
- `name`
- `time`
- `day`

Name of the activity should be unique among the activities on the same day.

time must be in the `H:mm` format, where `H` can be 1 or 2 digits from 0 to 23 and `mm` must be 2 digits from 00-59

Example of usage:

```
~/My Trip/ITINERARY >
add activity --name activity 1 --time 9:00 --day 1

~/My Trip/ITINERARY >
add --d 1 --t 9:00 --n my activity 1
```

#### Adding new transportation

Target: `transportation`

Required arguments:
- `name`
- `mode`
- `budget`
- `day`

Note: 
- Name should be unique and cannot be "all"
- Day is an integer greater than 1
- Duplicate transportation is not allowed

Example of usage: 

```
~/My Trip/ITINERARY >
add transportation --name airplane --mode air --budget 350 --day 1

~/My Trip/TRANSPORTATION >
add --n airplane --b 350 --m air --d 1
```


### Adding new accommodation

Target: `accommodation`

Required arguments:
- `name`
- `budget`
- `start` (check-in day of accommodation)
- `end` (check-out day of accommodation)

Name should be unique and cannot be "all".
> ⚠️ Note that checking-in and checking-out at an accommodation on the same day is considered invalid, but checking-out at Hotel A 
and checking-in at Hotel B on the same day is valid. If users are to stay at the same accommodation in two (or more) different/separated 
time periods, they are advised to use different name or add symbols like (2) for distinguishing purpose, otherwise it would be classified as having duplicate names for accommodations.

Example of usage: 

```
~/My Trip/ITINERARY >
add accommodation --name hotel ABC  --budget 800 --start 1 --end 3

~/My Trip/ACCOMMODATION >
add --n hotel ABC --b 800 --s 1 --e 3
```

## Modifying

The command `modify` modify the item specified by index. The argument that are not index are the parameters to be changed to.

#### Modifying the trip

Target: `trip`

Action: `modify`

Required arguments: 
- `index` if not in the trip to be modified

Arguments: 
- `name`
- `start`
- `end`
- `budget`

Start date and end date should be in the format `d-M-yyyy` or `d-M` if the year is the current year.

Note that the user do not need to specify the index of the trip if they want to modify the current trip they are in.

Example of usage: changing the current trip name to "new my trip" and the total budget to 1200

```
~ >
modify trip --index 1 --name new my trip --budget 1200

~/my trip/itinerary >
modify trip --n new my trip --b 1200
```

#### Modifying the accommodation

Target: `accommodation`

Required arguments: 
- `index`

Arguments: 
- `name`
- `budget`
- `start` (check-in day of accommodation)
- `end` (check-out day of accommodation)

> ⚠️ Note that changing the name of an accommodation to the same name it had is considered as having duplicate names, no modification(s) will be done.

> ⚠️ Note that if you are changing the days of accommodation of a saved accommodation, both the new check-in and check-out day should be provided, even if one of them stays the same.

> ⚠️ See the special note at listing accommodation(s) for more details about accommodation indexes.

Example of usage: for the 1st accommodation of the current trip, change the 
accommodation name to "Lotte Hotel" and the days of accommodation to day 3 to 6

```
~/My Trip/ACCOMMODATION >
modify accom --index 1 --n Lotte Hotel --s 3 --e 6

```

#### Modifying the transportation

Target: `transportation`

Action: `modify`

Required arguments: 
- `index`

Arguments: 
- `name`
- `budget`
- `day` (day of transportation)

Note: The start and end day is expressed as a single integer and  must be a number greater than 1

Example of usage:

```
~/My Trip/ITINERARY >
modify transportation --index 1 --budget 1200 --day 3

~/My Trip/ITINERARY >
modify transportation --i 1 --d 3 --b 1200
```


### Deleting

The `delete` or `remove` command will delete the item specified with the argument `index` or `name` in the specified target. Maybe shorten as `d` or `rm`.

#### Deleting a trip
Note that if you give argument values for both argument `--index` and `--name`, the priority will be given to `--name`, i.e. delete by name.

Target: `trip`

Required arguments: 
- `index` or `name`

Note that you cannot delete the trip you are currently in.

Example of usage: deleting trip named "my trip" with index 1

```
~ >
delete trip --index 1

~ >
rm --n my trip
```

#### Deleting an activity

Target: `activity`

Required arguments: `index` or `name`, and `day`

Example of usage: deleting trip named "my trip" with index 1

```
~ >
delete trip --index 1

~ >
rm --n my trip
```

#### Deleting a transportation

Target: `transportation`

Required arguments: `index` or `name`

Example of usage: deleting transportation named "Scoot" with index 1

```
~/My Trip/ITINERARY >
delete transportation --index 1

~/My Trip/TRANSPORTATION >
delete --n Scoot
```

#### Deleting an accommodation

Target: `accommodation`

Required arguments:
- `index` or `name`
- `all`

Special case for deleting all accommodations of the current trip `delete accommodation --all` or `delete accommodation --n all`

Example of usage: deleting accommodation named "Hilton Hotel" with index 1

> ⚠️ Note that if you give any argument value for the argument `--all`, the values will be ignored.

> ⚠️ See the special note at listing accommodation(s) for more details about accommodation indexes.

```
~/My Trip/ITINERARY >
delete accom --index 1

~/My Trip/ACCOMMODATION >
delete --n Hilton Hotel
```

### Deleting a transportation

Target: `transportation`

Required arguments: 
- `index` or `name`

Example of usage: deleting transportation named "plane" with index 1

```
~/My Trip/ITINERARY >
delete transportation --index 1

~/My Trip/TRANSPORTATION >
delete --n plane
```

### Deleting an activity

Target: `activity`

Required arguments: 
- `day`
- `index` or `name`

Example of usage: deleting activity named "shopping" in day 1 with index 1

```
~/My Trip/ACCOMMODATION >
delete activity --day 1 --index 1

~/My Trip/ITINERARY >
delete --d 1 --n shopping
```

## Listing

List command will list the item(s) in the target specified by index or name (except for itinerary). In the case of wanting to list all the item in the target, use the special argument `--all` that does not take any argument value or use `all` as the argument value for the argument `name`.

Note that if you give argument values for both argument `--index` and `--name`, the priority will be given to `--name`, i.e. list by name.

Note that if you give any argument value for the argument `--all`, the values will be ignored.

### Listing trips

Target: `trip`

Required arguments:
- `index` or `name` or `all`

Special case for listing all trips `list trip --all`

Example of usage:

```
~ >
list trip --index 1

~ >
list --n my trip
```

### Listing itinerary

Target: `itinerary`

No required arguments.

Example of usage:

```
~/My Trip/ITINERARY >
list itinerary

~/My Trip/ITINERARY >
list
```

### Listing accommodation(s)

Target: `accommodation`

Required arguments:
- `index` or `name` or `all`

Special case for listing all accommodations of the current trip `list accommodation --all` or `list accommodation --n all`

> ⚠️ Note that the accommodations are sorted in ascending order of the check-in days. Therefore, after adding a new 
accommodation/modifying a saved accommodation, the indexes of the accommodations might update. Users are advised to 
utilise the 'listing all' feature to check for the most updated accommodations' indexes before deleting/modifying.

Example of usage:

```
~/My Trip/ACCOMMODATION >
list accom --index 1

~/My Trip/ACCOMMODATION >
list --n Hilton Hotel
```

### Listing transportation(s)

Target: `trip`

- `index` or ' `name` or `all`

Special case for listing all transportationsL: `list transportation --all`
- By listing all transportations, the user will be able to view the associated index for each transportation.

Example of usage:

```
~ >
list transportation --index 1

~ >
list transportation -- all
```


#### Exiting the program

`exit` or `quit` or `bye`

Does not require any target or arguments

## FAQ

**Q**: Will extra spaces be ignored?

**A**: Yes, unless those extra spaces are within the argument value.

**Q**: Are the commands case-sensitive?

**A**: The case of the keywords in the commands (action, target and argument keywords) are not case-sensitive.

**Q**: Do the arguments have a specific order to follow?

**A**: No, the arguments can be arranged in any order as long as it is after the command action and target.

**Q**: Why enter only `cd` do not give error? That is, why `cd` is a valid command?

**A**: By entering only the command action `cd` and omitting the command target, the program will use the default target. So input `cd` means asking to program to "change the directory to the current directory".

**Q**: How can I see the activity I added?

**A**: You can see all the activity added by listing the itinerary. But there is no way to list the activity individually.

## Command Summary

{Give a 'cheat sheet' of commands here}

Command action:
- Adding: `add` or `a` or `make` or `mk`
- Deleting: `delete` or `d` or `remove` or `rm`
- Modifying: `modify` or `mod` or `m`
- Listing: `list` or `l`
- Changing directory: `cd`
- Exiting the program: `exit` or `quit` or `bye`

Command target:
- `trip`
- `activity` or `act`
- `transportation` or `tran`
- `accommodation` or `accom`
- `itinerary` or `itin`

List of arguments:
- `--name <trip name>` or `--n`
- `--index <trip index>` or `--i`
- `--budget <total budget>` or `--b`
- `--start <start date or day>` or `--s`
- `--end <end date or day>` or `--e`
- `--day <day number>` or `--d`
- `--time <time>` or `--t`
- `--mode <transportation mode>` or `--m`
- `--all`
- `--root`

## Command Error

Below are some common error messages. The actual reason might be different from or even not included in those given below. Note that when there are multiple possible errors in the same command, only one of them will be shown.

### Accommodation not found
The given accommodation name is not found in the trip.  Possible reason:
- the word given in the name argument is misspelled 
- argument after (if any) has an incorrect format (like extra spaces between the hyphen and the keyword or missing a hyphen)

### Activity not found
The given activity name is not found in the trip.  Possible reason:
- the word given in the name argument is misspelled
- argument after (if any) has an incorrect format (like extra spaces between the hyphen and the keyword or missing a hyphen)
- 
### Duplicated name
The given name is duplicated.

### Invalid argument keyword
The given argument keyword is incorrect.  Possible reason:
- the word immediately after the double hyphen "--" is misspelled

### Invalid argument value
The given argument value is incorrect.  Possible reason:
- argument value is empty

### Invalid budget
The given budget is invalid.  Possible reason:
- budget given is smaller than 0
- argument after (if any) has an incorrect format (like extra spaces between the hyphen and the keyword or missing a hyphen)

### Invalid command action
The given command action is incorrect. Possible reason:
- the first word is misspelled
- empty command inputted

### Invalid command target
The given command target is incorrect.  Possible reason:
- the second word is misspelled

### Invalid date
The given date is invalid. Possible reasons:
- format is incorrect, (should be d-M-yyyy or d-M)
- start date is after the end date.
- argument after (if any) has an incorrect format (like extra spaces between the hyphen and the keyword or missing a hyphen)

### Invalid day
The given day is invalid. Possible reasons:
- input day is not within the range of the day of the current trip
- start day is after the end day
- overlapping with other accommodations
- argument after (if any) has an incorrect format (like extra spaces between the hyphen and the keyword or missing a hyphen)

### The activity already exists
Duplicated activity name on the same day.

### Invalid index
The given index is invalid. Possible reasons:
- the given index is out of range
- the given index of the trip to be deleted is the trip you are currently in
- argument after (if any) has an incorrect format (like extra spaces between the hyphen and the keyword or missing a hyphen)

### Invalid name
The given name is invalid. Possible reasons:
- the given name is not allowed like "all" or "root" for trip and "all" for accommodation and transportation
- the given name of the trip to be deleted is the trip you are currently in

### Invalid number format
The given number is invalid. Possible reasons:
- the given number is not an integer
- the given number is too large
- argument after (if any) has an incorrect format (like extra spaces between the hyphen and the keyword or missing a hyphen)

### Invalid time format
The given time is invalid. Possible reasons:
- the given time is not in the format of HH:MM
- argument after (if any) has an incorrect format (like extra spaces between the hyphen and the keyword or missing a hyphen)

### Missing argument
There is missing argument. Possible reasons:
- the required argument is missing
- the argument input are in incorrect format (like extra spaces between the hyphen and the keyword or missing a hyphen)

### Extra argument
There is an extra argument non supported by the program. Possible reason:
- The user might be trying to cd to either transportation and accommodation using an index

### Transportation not found
The given transportation name is not found in the trip.  Possible reason:
- the word given in the name argument is misspelled
- argument after (if any) has an incorrect format (like extra spaces between the hyphen and the keyword or missing a hyphen)

### Trip not found
The given trip name is not found.  Possible reason:
- the word given in the name argument is misspelled
- argument after (if any) has an incorrect format (like extra spaces between the hyphen and the keyword or missing a hyphen)
