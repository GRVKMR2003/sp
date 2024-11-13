


import java.util.*;

public class fcfs {

    // Method to calculate Completion Times
    public static void findCompletionTime(int n, int[] at, int[] bt, int[] completion, int[] start) {
        // List to store arrival time and process index
        List<int[]> processes = new ArrayList<>();
        
        // Store arrival time and process index
        for (int i = 0; i < n; i++) {
            processes.add(new int[]{at[i], i});
        }
        
        // Sort processes based on arrival time
        processes.sort(Comparator.comparingInt(p -> p[0]));

        // Set the start and completion time for the first process
        start[processes.get(0)[1]] = at[processes.get(0)[1]];
        completion[processes.get(0)[1]] = start[processes.get(0)[1]] + bt[processes.get(0)[1]];

        // Calculate start and completion times for the rest of the processes
        for (int i = 1; i < n; i++) {
            start[processes.get(i)[1]] = Math.max(completion[processes.get(i - 1)[1]], at[processes.get(i)[1]]);
            completion[processes.get(i)[1]] = start[processes.get(i)[1]] + bt[processes.get(i)[1]];
        }
    }

    // Method to calculate Turnaround Times
    public static void findTurnAroundTime(int n, int[] at, int[] completion, int[] tat) {
        for (int i = 0; i < n; i++) {
            tat[i] = completion[i] - at[i]; // TAT = Completion Time - Arrival Time
        }
    }

    // Method to calculate Waiting Times
    public static void findWaitingTime(int n, int[] bt, int[] tat, int[] wt) {
        for (int i = 0; i < n; i++) {
            wt[i] = tat[i] - bt[i]; // WT = Turnaround Time - Burst Time
        }
    }

    // Method to print the Gantt Chart
    public static void printGanttChart(int n, int[] start, int[] bt, int[] completion) {
        System.out.println("\nGantt Chart:");
        System.out.println("-------------------------------------------------");

        // Sort by start times to print the processes in correct order
        List<int[]> GT = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            GT.add(new int[]{start[i], i});
        }
        GT.sort(Comparator.comparingInt(p -> p[0]));

        // Top row with process numbers
        for (int i = 0; i < n; i++) {
            System.out.print("|  P" + (GT.get(i)[1] + 1) + "  ");
        }
        System.out.println("|");

        System.out.println("-------------------------------------------------");

        // Bottom row with start and end times
        for (int i = 0; i < n; i++) {
            System.out.print(start[GT.get(i)[1]] + " ");
        }
        System.out.println(completion[n - 1]); // Last completion time
    }

    // Method to calculate and display average waiting time, turnaround time, and print Gantt chart
    public static void findAvgTime(int n, int[] at, int[] bt) {
        int[] completion = new int[n], wt = new int[n], tat = new int[n], start = new int[n];

        findCompletionTime(n, at, bt, completion, start);
        findTurnAroundTime(n, at, completion, tat);
        findWaitingTime(n, bt, tat, wt);

        int tavg = 0, wavg = 0;

        System.out.println("\nProcess No. Arrival Time Burst Time Start Time Completion Time Wait Time Turnaround Time");
        for (int i = 0; i < n; i++) {
            wavg += wt[i];
            tavg += tat[i];

            System.out.println("P" + (i + 1) + "\t\t" + at[i] + "\t\t" + bt[i] + "\t\t" + start[i] + "\t\t" + completion[i] + "\t\t" + wt[i] + "\t\t" + tat[i]);
        }

        System.out.println("Average Waiting Time: " + (float) wavg / n);
        System.out.println("Average Turnaround Time: " + (float) tavg / n);

        // Print Gantt Chart
        printGanttChart(n, start, bt, completion);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        int[] at = new int[n], bt = new int[n];
        
        // Input Arrival and Burst times
        for (int i = 0; i < n; i++) {
            System.out.print("Enter the Arrival Time and Burst Time of process " + (i + 1) + ": ");
            at[i] = scanner.nextInt();
            bt[i] = scanner.nextInt();
        }

        // Call the method to calculate and print the results
        findAvgTime(n, at, bt);

        scanner.close();
    }
}
