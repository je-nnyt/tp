# VoyaTrip User Guide

## Introduction

VoyaTrip is a command-line application for managing trips.

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `VoyaTrip` from  [Placeholder](https://).

## Features 

{Give detailed description of each feature}

### Directory

Since there are multiple trips, what is currently being worked on is stored by the mean of directory. Everytime the program starts at the `root`, shown in the program as: `~ >`. When working within a trip, the trip name and the target name is shown as: `~/TRIP_NAME/TARGET_NAME >`. For example: `~/My Trip/Itinerary >`.

### Commands

The commands are usually composed of three main elements:
- Action
- Target
- Argument(s)

A command action is what the command does. Examples are:
- Adding: `add`
- Deleting: `delete`
- Modifying: `modify`
- Listing: `list`
- Changing directory: `cd`
- Exiting the program: `exit`

A command target is what the command affects. Examples are:
- `trip`
- `activity` or `act` for short
- `transportation` or `tran` for short
- `accommodation` or `accom` for short
- `itinerary` or `itin` for short
- Special case for changing to the root directory `..`

Note that the target keyword may be omitted. The program will then use the default target according to the current directory.

An argument consist of a double hyphen (`--`) immediately followed by a keyword and a value: `--<keyword> <value>`. For example: `--name my trip` will have the keyword `name` and the value `my trip`.

List of arguments:
- `--name <trip name>` or `--n`
- `--index <trip index>` or `--i`
- `--budget <total budget>` or `--b`
- `--start <start date>` or `--s`
- `--end <end date>` or `--e`
- `--day <day number>` or `--d`
- `--time <time>` or `--t`
- `--mode <transportation mode>` or `--m`

### Adding new trip

Target: `trip`

Action: `add`

Required arguments: `name`, `start`, `end` and `budget`

start date and end date should be in the format `d-M-yyyy` or `d-M` if the year is the current year.

Example of usage: 

```
~ >
add trip --name my trip --start 1-5 --end 7-5 --budget 1000

~ >
add --n my trip --b 1000 --s 1-5 --e 7-5
```

### Modifying

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

### Deleting a trip

Target: `trip`

Action: `delete`

Required arguments: `index` or `name`

Example of usage:

```
~ >
delete trip --index 1

~ >
delete --n my trip
```

### Changing the current trip currently working on

Target: `trip`

Action: `cd`

Required arguments: `index` or `name`

Special case for changing to the root directory `cd ..`, `cd trip`, `cd --root` or `cd trip --n root`

Example of usage:

```
~ >
cd trip --index 1

~ >
cd --n my trip
```

### Adding new activity

Action: `add`

Target: `activity`

Required arguments: `name`, `time`, `day`

Example of usage:

```
~/My Trip/Itinerary >
add activity --name activity 1 --time 10:00 --day 1

~/My Trip/Itinerary >
add --d 1 --t 10:00 --n my activity 1
```

### Adding new transportation

Action: `add`

Target: `transportation`

Required arguments: `name`, `mode`, `budget`, `start day number`, `end day number`

Example of usage:

```
~/My Trip/Transportation >
add transportation --name airplane --mode air --budget 350 --start 1 --end 2

~/My Trip/Transportation >
add --n airplane --b 350 --m air --s 1 --e 2
```

## Accommodation
Note that for all operations related to accommodation, you should ensure that you are in the directory/trip 
which you want to make these operations in, or else you should first change directory to the trip. For example, 
if you want to add/delete accommodation in your 2nd trip named 'Another Trip', and your current directory is 
not starting by ~/Another Trip/, please change directory by `cd trip --i 2` or `cd trip --n Another Trip`.

### Adding new accommodation

Action: `add`

Target: `accommodation`

Required arguments: `name`, `budget`, `start` (start day of accommodation), `end` (end day of accommodation)

The start day should be at least 1 and the end day should not be over the 
total no. of days of the trip.

Example of usage:

```
~/My Trip/Accommodation >
add accommodation --name Hilton Hotel --budget 500 --start 1 --end 4

~/My Trip/Accommodation >
add --n Hilton Hotel --b 500 --s 1 --e 4
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

### Deleting an accommodation

Action: `delete`

Target: `accommodation`

Required arguments: `index` or `name`

Example of usage:

```
~/My Trip/Accommodation >
delete accom --index 1

~/My Trip/Accommodation >
delete --n Hilton Hotel
```

### Listing accommodation(s)

Action: `list`

Target: `accommodation`

Required arguments: `index` or `name`

Special case for listing all accommodations of the current trip `list accommodation --n all`

Example of usage:

```
~/My Trip/Accommodation >
list accom --index 1

~/My Trip/Accommodation >
list --n Hilton Hotel
```

## Listing

### Listing trips

Action: `list`

Target: `trip`

Required arguments: `index` or `name`

Special case for listing all trips `list trip --all`

Example of usage:

```
~ >
list trip --index 1

~ >
list --n my trip
```

### Listing transportations

Action: `list`

Target: `trip`

Required arguments: `index` or `name`

Special case for listing all transportations `list trip --n all`

Example of usage:

```
~ >
list transportation --index 1

~ >
list transportation --n all
```


### Exiting the program

Action: `exit`

## FAQ

**Q**: Will extra spaces be ignored?

**A**: Yes, unless those extra spaces are within the argument.

**Q**: Are the commands case-sensitive?

**A**: The case of the keywords in the commands (action, target and argument keywords) are not case-sensitive.

**Q**: Do the arguments have a specific order to follow?

**A**: No, the arguments can be arranged in any order as long as it is after the command action and target.

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
* Add transportation `add transportaion --n NAME --m MODE --b BUDGET --s START_DAY_NUMBER --e END_DAY_NUMBER`