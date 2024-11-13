
import java.util.*;

public class srtf {

    // Function to calculate Shortest Remaining Time First scheduling
    public static void calculateSRTF(int n, int[] at, int[] bt, int[] start, int[] completion, int[] tat, int[] wt) {
        int[] remainingTime = new int[n];
        int currentTime = 0;
        int completed = 0;

        // Initialize remaining time for each process
        for (int i = 0; i < n; i++) {
            remainingTime[i] = bt[i];
        }

        while (completed < n) {
            int idx = -1;
            int minRemainingTime = Integer.MAX_VALUE;

            // Find process with the shortest remaining time among the arrived processes
            for (int i = 0; i < n; i++) {
                if (at[i] <= currentTime && remainingTime[i] > 0) {
                    if (remainingTime[i] < minRemainingTime) {
                        minRemainingTime = remainingTime[i];
                        idx = i;
                    }
                    // If remaining times are the same, choose process with earlier arrival time
                    if (remainingTime[i] == minRemainingTime && at[i] < at[idx]) {
                        idx = i;
                    }
                }
            }

            if (idx != -1) {
                // Set start time of the process if it's the first time being executed
                if (remainingTime[idx] == bt[idx]) {
                    start[idx] = currentTime;
                }

                remainingTime[idx]--; // Execute process for one unit of time
                currentTime++;

                // If process is completed
                if (remainingTime[idx] == 0) {
                    completion[idx] = currentTime;
                    tat[idx] = completion[idx] - at[idx];
                    wt[idx] = tat[idx] - bt[idx];
                    completed++;
                }
            } else {
                // If no process is ready, increment the current time
                currentTime++;
            }
        }
    }

    // Function to print results and calculate averages
    public static void printResults(int n, int[] at, int[] bt, int[] start, int[] completion, int[] tat, int[] wt) {
        float totalTAT = 0, totalWT = 0;

        System.out.println("\nProcess\tArrival Time\tBurst Time\tStart Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (int i = 0; i < n; i++) {
            totalTAT += tat[i];
            totalWT += wt[i];
            System.out.printf("P%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t%d%n", i + 1, at[i], bt[i], start[i], completion[i], tat[i], wt[i]);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f%n", (totalTAT / n));
        System.out.printf("Average Waiting Time: %.2f%n", (totalWT / n));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        int[] at = new int[n]; // Arrival times
        int[] bt = new int[n]; // Burst times
        int[] start = new int[n]; // Start times
        int[] completion = new int[n]; // Completion times
        int[] tat = new int[n]; // Turnaround times
        int[] wt = new int[n]; // Waiting times

        // Input arrival and burst times for each process
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Arrival Time and Burst Time for Process " + (i + 1) + ": ");
            at[i] = scanner.nextInt();
            bt[i] = scanner.nextInt();
        }

        // Calculate SRTF
        calculateSRTF(n, at, bt, start, completion, tat, wt);

        // Print results
        printResults(n, at, bt, start, completion, tat, wt);

        scanner.close();
    }
}
