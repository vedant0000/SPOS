import java.util.*;

public class FCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("------------ FIRST COME FIRST SERVE (FCFS) ------------");
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] pid = new int[n];
        int[] at = new int[n];
        int[] bt = new int[n];
        int[] ct = new int[n];
        int[] tat = new int[n];
        int[] wt = new int[n];

        // Input process details
        for (int i = 0; i < n; i++) {
            pid[i] = i + 1;
            System.out.println("\nEnter details for Process " + pid[i] + ":");
            System.out.print("Arrival Time: ");
            at[i] = sc.nextInt();
            System.out.print("Burst Time: ");
            bt[i] = sc.nextInt();
        }

        // Sort processes by arrival time
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (at[i] > at[j]) {
                    // Swap arrival time
                    int temp = at[i];
                    at[i] = at[j];
                    at[j] = temp;

                    // Swap burst time
                    temp = bt[i];
                    bt[i] = bt[j];
                    bt[j] = temp;

                    // Swap process ID
                    temp = pid[i];
                    pid[i] = pid[j];
                    pid[j] = temp;
                }
            }
        }

        int currentTime = 0;
        double totalTAT = 0, totalWT = 0;

        // Calculate times
        for (int i = 0; i < n; i++) {
            if (currentTime < at[i]) {
                currentTime = at[i]; // Wait for process to arrive
            }
            ct[i] = currentTime + bt[i];
            tat[i] = ct[i] - at[i];
            wt[i] = tat[i] - bt[i];

            totalTAT += tat[i];
            totalWT += wt[i];

            currentTime = ct[i];
        }

        // Output
        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + pid[i] + "\t" + at[i] + "\t" + bt[i] + "\t" +
                    ct[i] + "\t" + tat[i] + "\t" + wt[i]);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f", totalTAT / n);
        System.out.printf("\nAverage Waiting Time: %.2f\n", totalWT / n);
    }
}
