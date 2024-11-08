# Speedrun Simulation

This is the simulation software for the TransitRuns speedrunning website. This tool is to be used by site moderation to verify accuracy of attempted transit speedruns.

Note before the rest of the readme - this tool was not created by a professional programmer. I'm just a college GIS student who just happens to know a little bit of coding. Take caution if you attempt to look at the code behind this.

## Included Files

`Simulation.java` and `Simulation.class` - I coded this in Java, here's the raw code and compiled file.

`calendar.txt`, `stop_times.txt`, `stops.txt`, `trips.txt` - Files from a testing GTFS feed not representative of any transit agency.

`run_0001.txt` - Example file for run route format that simulation reads. Segments are on separate lines and note the start and end stop ID for each segment

## Software Input

Run ID: Comes from the file name of run route, e.g. `run_0001.txt` contains route info for run ID 0001.

Date: Date run was attempted on.

Day of Week: Day of week run was attempted on. Takes a number from 0 to 6, 0 representing Monday, 6 representing Sunday.

Start Time: Time run was started. Required to be at least 1 minute before first train departure.

Software will print out expected start and ending times based off the GTFS schedule for the day the run was attempted.
