# VoyaTrip User Guide

## Introduction

VoyaTrip is a command-line application for managing trips. It also includes a budget system for keeping track of the budget of the trip.

## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `VoyaTrip` from  [Placeholder](https://).

## Features

In VoyaTrip, you may store multiple trips. Do note that the name of each trip have to be unique.

Each trip have a budget, starting date and ending date.
 The total budget will be divided equally to all days by default for the creation of the trip. There are three components: `itinerary`, `transportation` and `accommodation`.

The `itinerary` contains lists of activities for all the days. This is for you to plan what to do during the trip. Each day will have a budget for you to keep track of.

The `transportation` contain a list of transportation. This is for you to keep track of any important transportation during the trip like the flight. Each transportation will have a budget for you to adjust and keep track of.

The `accommodation` contain a list of accommodations. This is for you to keep track of the accommodation during the trip. Each accommodation will have a budget for you to adjust and keep track of.

The budget of a trip is the maximum budget you expected have for the trip. It is not the sum of all the budgets of all the components. But instead, VoyaTrip will give warning if the total sum of the budgets of all the components is greater than the budget of the trip. So that you can adjust the budgets of the components accordingly.

## The Directory System

The directory system is used to navigate between trips and different components (`ITINERARY`, `TRANSPORTATION` and `ACCOMMODATION`) of a trip. To make changes to the components of a trip, you have to be in the directory of the trip. 

Everytime the program starts at the `root`, shown in the program as: `~ >`. When working within a trip, the trip name and the target component is shown as: `~/TRIP_NAME/TARGET_NAME >`. For example: `~/My Trip/ITINERARY >`.

## Commands

A command follows the format: `<action> <target> <argument(s)>`. The keywords are not case-sensitive.

A command action is what the command does, such as `add` and `delete`. 

A command target is what group does the command affects, such as `trip` and `transportation`.

An argument consist of a double hyphen (`--`) immediately followed by a keyword and a value: `--<keyword> <value>`. For example: `--name my trip` will have the keyword `name` and the value `my trip`.

Note that some commands may not have a target or arguments. Also note that extra arguments that are not necessary or duplicated will be ignored.

The target keyword may be omitted if the correct directory is at the corresponding target component.

For example, if the current directory is `~/<trip name>/ITINERARY` and if `add` is entered without the target keyword, then since the only thing add can do in the current directory is to add an activity, the command target will be assumed to be `activity`.


## Changing Directory

The `cd` command is used to navigate between trips and different components.

### Changing the current trip currently working on

Target: `trip`

Required arguments: `index` or `name`

Special case for changing to the root directory `cd ..`, `cd trip` or `cd trip --n root`

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

### Changing the current component

Target: `itinerary`, `transportation` or `accommodation`

No required arguments

Example of usage: change directory to the `accommodation` component of the current trip

```
~/My Trip/ITINERARY >
cd accommodation

~/My Trip/ITINERARY >
cd accom
```

## Adding

`add` or `make` are commands that are used to add new items. Maybe shorten as `a` or `mk`.

### Adding new trip

Format:

`add trip --name TRIP_NAME --start START_DATE --end END_DATE --budget TOTAL_BUDGET`

Required arguments: `name`, `start`, `end` and `budget`

start date and end date should be in the format `d-M-yyyy` or `d-M` if the year is the current year. Note that entering 2-29 if the year is not leap year, the program will assume it means the last day of the month which is 2-28.

Name of the trip should be unique and cannot be "root" or "all".

Example of usage: 

```
~ >
add trip --name my trip --start 1-5 --end 7-5 --budget 1000

~ >
mk --n my trip --b 1000 --s 1-5 --e 7-5
```

### Adding new activity

Target: `activity`

Required arguments: `name`, `time`, `day`

Example of usage:

```
~/My Trip/ITINERARY >
add activity --name activity 1 --time 10:00 --day 1

~/My Trip/ITINERARY >
add --d 1 --t 10:00 --n my activity 1
```

### Adding new transportation

Target: `transportation`

Required arguments: `name`, `mode`, `budget`, `day`

Name cannot be "all".

Example of usage:

```
~/My Trip/Transportation >
add transportation --name airplane --mode air --budget 350 --day 1

~/My Trip/Transportation >
add --n airplane --b 350 --m air --d 1
```


## Modifying

Generally speaking, modify the item specified by index. The argument that are not index are the parameters to be changed to. Day number is also required for modifying activity. User do not need to specify the trip index if they are already in the trip they want to modify.

### Modifying the trip

Target: `trip`

Action: `modify`

Required arguments: `index`

Arguments: `name`, `start`, `end` and `budget`

start date and end date should be in the format `d-M-yyyy` or `d-M` if the year is the current year.

Note that the user do not need to specify the index of the trip if they want to modify the current trip they are in.

Example of usage: changing the current trip name to "new my trip" and the total budget to 1200

```
~ >
modify trip --index 1 --name new my trip --budget 1200

~/my trip/itinerary >
modify trip --n new my trip --b 1200
```

### Modifying the accommodation

Action: `modify`

Target: `accommodation`

Required arguments: `index`

Arguments: `name`, `budget`, `start` (start day of accommodation), `end` (end day of accommodation)

Note that if you are changing the days of accommodation of a saved accommodation, 
you should provide both the new start and end days.

Example of usage: for the 1st accommodation of the current trip, change the 
accommodation name to "Lotte Hotel" and the days of accommodation to day 3 to 6

```
~/My Trip/Accommodation >
modify accom --index 1 --n Lotte Hotel --s 3 --e 6
```


## Deleting

The `delete` or `remove` command will delete the item specified with the argument `index` or `name` in the specified target. Maybe shorten as `d` or `rm`.

### Deleting a trip

Target: `trip`

Required arguments: `index` or `name`

Example of usage: deleting trip named "my trip" with index 1

```
~ >
delete trip --index 1

~ >
rm --n my trip
```

### Deleting an accommodation

Target: `accommodation`

Required arguments: `index` or `name`

Example of usage: deleting accommodation named "Hilton Hotel" with index 1

```
~/My Trip/ITINERARY >
delete accom --index 1

~/My Trip/ACCOMMODATION >
delete --n Hilton Hotel
```

## Listing

List command will list the item(s) in the target specified by index or name. In the case of wanting to list all the item in the target, use the special argument `--all` that does not take any argument value or use `all` as the argument value for the argument `name`.

Note that if you give any argument value for the argument `--all`, the values will be ignored.

### Listing accommodation(s)

Target: `accommodation`

Required arguments: `index` or `name` or `all`

Special case for listing all accommodations of the current trip `list accommodation --all` or `list accommodation --n all`

Example of usage:

```
~/My Trip/ACCOMMODATION >
list accom --index 1

~/My Trip/ACCOMMODATION >
list --n Hilton Hotel
```

### Listing trips

Target: `trip`

Required arguments: `index` or `name` or `all`

Special case for listing all trips `list trip --all`

Example of usage:

```
~ >
list trip --index 1

~ >
list --n my trip
```

### Listing transportations

Target: `trip`

Required arguments: `index` or `name` or `all`

Special case for listing all transportations `list transportation --name all` or `list transportation --all`

Example of usage:

```
~ >
list transportation --index 1

~ >
list transportation --n all
```


### Exiting the program

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

* Add transportation `add transportaion --n NAME --m MODE --b BUDGET --s START_DAY_NUMBER --e END_DAY_NUMBER`

## Command Error Summary

Note that when there are multiple possible errors in the same command, one of them will be shown.

| Error message            | Description                        |
|--------------------------|------------------------------------|
| Invalid argument keyword | Given argument keyword is invalid. |
| Invalid argument value   | Given argument value is invalid.   |
| Invalid command action   | Given command action is invalid.   |
| Invalid command target   | Given command target is invalid.   |
| Invalid date format      | Given date format is invalid.      |
| Invalid number format    | Given number format is invalid.    |
| Missing argument         | There is missing argument.         |
| Missing command keyword  | There is missing command keyword.  |
