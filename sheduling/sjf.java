import java.util.*;

public class sjf {

    
    // Function to find the Completion Time for each process
    public static void findCompletionTime(int n, int[] at, int[] bt, int[] completion, int[] start) {
        // Pair of arrival time and process index
        ArrayList<int[]> processes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            processes.add(new int[]{at[i], i});
        }

        // Sort processes by arrival time
        processes.sort(Comparator.comparingInt(a -> a[0]));

        int currentTime = 0;
        boolean[] done = new boolean[n]; // Track if a process is completed

        for (int i = 0; i < n; i++) {
            int idx = -1;
            int minBurstTime = Integer.MAX_VALUE;

            // Find the process with the shortest burst time that has arrived
            for (int j = 0; j < n; j++) {
                int procIndex = processes.get(j)[1];
                if (!done[procIndex] && at[procIndex] <= currentTime && bt[procIndex] < minBurstTime) {
                    minBurstTime = bt[procIndex];
                    idx = procIndex;
                }
            }

            // If no process has arrived, skip to the next arrival time
            if (idx == -1) {
                currentTime = processes.get(i)[0];
                i--;
                continue;
            }

            // Set start and completion times for the selected process
            start[idx] = currentTime;
            completion[idx] = currentTime + bt[idx];
            currentTime = completion[idx];
            done[idx] = true;
        }
    }

    // Function to find the Turnaround Time for each process
    public static void findTurnAroundTime(int n, int[] at, int[] completion, int[] tat) {
        for (int i = 0; i < n; i++) {
            tat[i] = completion[i] - at[i]; // TAT = CT - AT
        }
    }

    // Function to find the Waiting Time for each process
    public static void findWaitingTime(int n, int[] bt, int[] tat, int[] wt) {
        for (int i = 0; i < n; i++) {
            wt[i] = tat[i] - bt[i]; // WT = TAT - BT
        }
    }

    // Function to print the Gantt Chart for the processes based on increasing time
    public static void printGanttChart(int n, int[] start, int[] bt, int[] completion) {
        System.out.println("\nGantt Chart:");
        System.out.println("-------------------------------------------------");

        // Sort processes by start time for time-based ordering in the Gantt chart
        ArrayList<int[]> timeline = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            timeline.add(new int[]{start[i], i});
        }
        timeline.sort(Comparator.comparingInt(a -> a[0]));

        // Top row with process numbers
        for (int i = 0; i < n; i++) {
            int idx = timeline.get(i)[1];
            System.out.print("|  P" + (idx + 1) + "  ");
        }
        System.out.println("|");

        System.out.println("-------------------------------------------------");

        // Bottom row with start and end times in time order
        for (int i = 0; i < n; i++) {
            int idx = timeline.get(i)[1];
            System.out.print(start[idx] + "      ");
        }
        System.out.println(completion[timeline.get(n - 1)[1]]);
    }

    // Function to find and display average waiting and turnaround times
    public static void findAvgTime(int n, int[] at, int[] bt) {
        int[] completion = new int[n], wt = new int[n], tat = new int[n], start = new int[n];

        findCompletionTime(n, at, bt, completion, start);
        findTurnAroundTime(n, at, completion, tat);
        findWaitingTime(n, bt, tat, wt);

        int totalWt = 0, totalTat = 0;

        System.out.println("\nProcess No.\tArrival Time\tBurst Time\tStart Time\tCompletion Time\tWait Time\tTurnaround Time");
        for (int i = 0; i < n; i++) {
            totalWt += wt[i];
            totalTat += tat[i];

            System.out.println("P" + (i + 1) + "\t\t" + at[i] + "\t\t" + bt[i] + "\t\t" + start[i] + "\t\t"
                    + completion[i] + "\t\t" + wt[i] + "\t\t" + tat[i]);
        }

        System.out.println("Average Waiting Time: " + ((float) totalWt / n));
        System.out.println("Average Turnaround Time: " + ((float) totalTat / n));

        // Print Gantt Chart
        printGanttChart(n, start, bt, completion);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        int[] at = new int[n], bt = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter the Arrival Time and Burst Time of process " + (i + 1) + ": ");
            at[i] = sc.nextInt();
            bt[i] = sc.nextInt();
        }

        findAvgTime(n, at, bt);
        sc.close();
    }
}
