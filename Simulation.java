import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.time.*;
import java.io.File;
import java.io.FileNotFoundException;

// transitruns simulation gtfs

public class Simulation
{
    public static void main(String [] args)
    {  
        Scanner in = new Scanner(System.in);

        // import data
        ArrayList<String> serviceIDcal = new ArrayList<String>();
        ArrayList<Integer> calStart = new ArrayList<Integer>();
        ArrayList<Integer> calEnd = new ArrayList<Integer>();
        ArrayList<String> [] days = new ArrayList[7];
        for (int i = 0; i < days.length; i++)
        {
            days[i] = new ArrayList<String>();
        }
        try
        {
            Scanner s = new Scanner(new File("calendar.txt"));
            int z = 0; // skip first
            while (s.hasNextLine())
            {
                if (z == 0)
                {
                    s.nextLine();
                    z++;
                }
                else
                {
                    String data = s.nextLine();
                    serviceIDcal.add(data.substring(0, data.indexOf(",")));
                    data = data.substring(data.indexOf(",") + 1);
                    days[0].add(data.substring(0, data.indexOf(",")));
                    data = data.substring(data.indexOf(",") + 1);
                    days[1].add(data.substring(0, data.indexOf(",")));
                    data = data.substring(data.indexOf(",") + 1);
                    days[2].add(data.substring(0, data.indexOf(",")));
                    data = data.substring(data.indexOf(",") + 1);
                    days[3].add(data.substring(0, data.indexOf(",")));
                    data = data.substring(data.indexOf(",") + 1);
                    days[4].add(data.substring(0, data.indexOf(",")));
                    data = data.substring(data.indexOf(",") + 1);
                    days[5].add(data.substring(0, data.indexOf(",")));
                    data = data.substring(data.indexOf(",") + 1);
                    days[6].add(data.substring(0, data.indexOf(",")));
                    data = data.substring(data.indexOf(",") + 1);
                    calStart.add(Integer.parseInt(data.substring(0, data.indexOf(","))));
                    data = data.substring(data.indexOf(",") + 1);
                    calEnd.add(Integer.parseInt(data));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error - no calendar.txt.");
        }

        ArrayList<String> serviceIDtrip = new ArrayList<String>();
        ArrayList<String> tripIDtrip = new ArrayList<String>();
        try
        {
            Scanner s = new Scanner(new File("trips.txt"));
            int z = 0; // skip first
            while (s.hasNextLine())
            {
                if (z == 0)
                {
                    s.nextLine();
                    z++;
                }
                else
                {
                    String data = s.nextLine();
                    data = data.substring(data.indexOf(",") + 1);
                    serviceIDtrip.add(data.substring(0, data.indexOf(",")));
                    data = data.substring(data.indexOf(",") + 1);
                    tripIDtrip.add(data.substring(0, data.indexOf(",")));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error - no trips.txt.");
        }

        ArrayList<String> stopID = new ArrayList<String>();
        ArrayList<String> stopName = new ArrayList<String>();
        try
        {
            Scanner s = new Scanner(new File("stops.txt"));
            int z = 0; // skip first
            while (s.hasNextLine())
            {
                if (z == 0)
                {
                    s.nextLine();
                    z++;
                }
                else
                {
                    String data = s.nextLine();
                    stopID.add(data.substring(0, data.indexOf(",")));
                    data = data.substring(data.indexOf(",") + 1);
                    stopName.add(data.substring(0, data.indexOf(",")));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error - no stops.txt.");
        }

        ArrayList<String> tripIDtimes = new ArrayList<String>();
        ArrayList<String> departuretimes = new ArrayList<String>();
        ArrayList<String> stopIDtimes = new ArrayList<String>();
        try
        {
            Scanner s = new Scanner(new File("stop_times.txt"));
            int z = 0; // skip first
            while (s.hasNextLine())
            {
                if (z == 0)
                {
                    s.nextLine();
                    z++;
                }
                else
                {
                    String data = s.nextLine();
                    tripIDtimes.add(data.substring(0, data.indexOf(",")));
                    data = data.substring(data.indexOf(",") + 1);
                    departuretimes.add(data.substring(0, data.indexOf(",")));
                    data = data.substring(data.indexOf(",") + 1);
                    stopIDtimes.add(data.substring(0, data.indexOf(",")));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error - no stop_times.txt.");
        }

        /*
            run_0001 simulation info
            trips 9 and 3
            start 06:45, end 07:15
            transfer at stop 1
        */

        int date = 20240101; /// yyyymmdd - placeholder
        int dayOfWeek = 0; // 0-6, mon-sun - placeholder

        String startTimeInput = "00:00"; // must be 24 hour format
        
        String runId = "no run";
        ArrayList<String> legStart = new ArrayList<String>();
        ArrayList<String> legEnd = new ArrayList<String>();

        // get run info (prompt for run id)

        System.out.print("Enter Run ID: ");
        runId = in.nextLine();

        try
        {
            Scanner s = new Scanner(new File("runs.txt"));
            while (s.hasNextLine())
            {
                String data = s.nextLine();
                if (data.substring(0, data.indexOf(",")).equals(runId))
                {
                    data = data.substring(data.indexOf(",") + 1); // skip past run ID
                    date = Integer.parseInt(data.substring(0, data.indexOf(",")));
                    data = data.substring(data.indexOf(",") + 1);
                    dayOfWeek = Integer.parseInt(data.substring(0, data.indexOf(",")));
                    data = data.substring(data.indexOf(",") + 1);
                    startTimeInput = data.substring(0, data.indexOf(","));
                    data = data.substring(data.indexOf(",") + 1);
                    while (data.indexOf(",") != -1)
                    {
                        legStart.add(data.substring(0, data.indexOf(",")));
                        data = data.substring(data.indexOf(",") + 1);
                        if (data.indexOf(",") != -1)
                        {
                            legEnd.add(data.substring(0, data.indexOf(",")));
                            data = data.substring(data.indexOf(",") + 1);
                        }
                        else
                        {
                            legEnd.add(data);
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            // nothing for now, but this is error
        }

        // prompt for day of week (this is something ran internally by staff, so don't stress with formatting)
        // prompt for start time (run_0001 test time is 06:49)

        // stop_times and trip imports based on what is running that day
        ArrayList<String> runningServices = new ArrayList<String>();
        ArrayList<String> runningTrips = new ArrayList<String>();
        ArrayList<String> runningDepTimes = new ArrayList<String>();
        ArrayList<String> runningStopTimes = new ArrayList<String>();
        ArrayList<String> runningTripTimes = new ArrayList<String>();

        for (int i = 0; i < serviceIDcal.size(); i++)
        {
            if (date >= calStart.get(i) && date <= calEnd.get(i))
            {
                if (days[dayOfWeek].get(i).equals("1"))
                {
                    runningServices.add(serviceIDcal.get(i));
                }
            }
        }

        // find trips running during service
        for (int i = 0; i < runningServices.size(); i++)
        {
            for (int j = 0; j < serviceIDtrip.size(); j++)
            {
                if (serviceIDtrip.get(j).equals(runningServices.get(i)))
                {
                    runningTrips.add(tripIDtrip.get(j));
                }
            }
        }

        // stop times during running trips
        int timeSize = tripIDtimes.size();
        for (int i = 0; i < timeSize; i++)
        {
            if (runningTrips.contains(tripIDtimes.get(i)))
            {
                runningDepTimes.add(departuretimes.get(i));
                runningStopTimes.add(stopIDtimes.get(i));
                runningTripTimes.add(tripIDtimes.get(i));
            }

            if ((i % 100000) == 0)
            {
                System.out.println("Progress: " + i);
            }
        } 

        String ogStartTime = "no data"; // for end of program
        String endTime = "no data"; // only used for last leg

        for (int j = 0; j < legStart.size(); j++)
        {
            LocalTime startTime = LocalTime.parse(startTimeInput);
            
            ArrayList<String> possibleTimes = new ArrayList<String>();

            for (int i = 0; i < runningStopTimes.size(); i++)
            {
                if (runningStopTimes.get(i).equals(legStart.get(j)))
                {
                    // times after midnight
                    if (Integer.parseInt(runningDepTimes.get(i).substring(0, 2)) >= 24)
                    {
                        runningDepTimes.set(i, "0" + (Integer.parseInt(runningDepTimes.get(i).substring(0, 2)) - 24) + runningDepTimes.get(i).substring(2));
                    }
                    LocalTime compareTime = LocalTime.parse(runningDepTimes.get(i));
                    // 0 is equal time, 1 is time before, -1 is time after
                    int compValue = startTime.compareTo(compareTime);
                    if (compValue <= 0)
                    {
                        possibleTimes.add(compareTime.toString());
                    }
                }
            }

            Collections.sort(possibleTimes);

            // find trip that works
            String currentTrip = "no data";
            for (int k = 0; k < possibleTimes.size(); k++)
            {
                String tripTime = possibleTimes.get(k);
                if (j == 0) // only first run, for end of program
                {
                    ogStartTime = tripTime;
                }

                // find trip id
                for (int i = 0; i < runningStopTimes.size(); i++)
                {
                    if (runningStopTimes.get(i).equals(legStart.get(j)))
                    {
                        if (tripTime.substring(0,5).equals(runningDepTimes.get(i).substring(0,5)))
                        {
                            currentTrip = runningTripTimes.get(i);
                            break;
                        }
                    }
                }

                // stops on trip
                ArrayList<String> tripStops = new ArrayList<String>();
                for (int i = 0; i < runningStopTimes.size(); i++)
                {
                    if (runningTripTimes.get(i).equals(currentTrip))
                    {
                        tripStops.add(runningStopTimes.get(i));
                    }
                }

                if (tripStops.contains(legEnd.get(j)))
                {
                    for (int l = 0; l < stopName.size(); l++)
                    {
                        if (legStart.get(j).equals(stopID.get(l)))
                        {
                            System.out.println(stopName.get(l) + " (dep.) - " + possibleTimes.get(k).toString());
                        }
                    }
                    break;
                }
            }

            // record time at leg end stop
            for (int i = 0; i < runningStopTimes.size(); i++)
            {
                if (runningStopTimes.get(i).equals(legEnd.get(j)))
                {
                    if (currentTrip.equals(runningTripTimes.get(i)))
                    {
                        if (j == (legEnd.size() - 1))
                        {
                            for (int k = 0; k < stopName.size(); k++)
                            {
                                if (runningStopTimes.get(i).equals(stopID.get(k)))
                                {
                                    System.out.println(stopName.get(k) + " (arr.) - " + runningDepTimes.get(i).toString());
                                }
                            }
                            endTime = runningDepTimes.get(i);
                        }
                        else
                        {
                            for (int k = 0; k < stopName.size(); k++)
                            {
                                if (runningStopTimes.get(i).equals(stopID.get(k)))
                                {
                                    System.out.println(stopName.get(k) + " (arr.) - " + runningDepTimes.get(i).toString());
                                }
                            }
                            startTimeInput = runningDepTimes.get(i);
                        }
                    }
                }
            }

            possibleTimes.clear();
        }

        System.out.println("Expected Start Time: " + ogStartTime);
        System.out.println("Expected End Time: " + endTime);
    }
}