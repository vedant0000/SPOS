import java.util.*;

public class PriorityScheduling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("---------- PRIORITY SCHEDULING (Non-Preemptive) ----------");
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] pid = new int[n];
        int[] at = new int[n];
        int[] bt = new int[n];
        int[] prio = new int[n];
        int[] ct = new int[n];
        int[] tat = new int[n];
        int[] wt = new int[n];
        boolean[] completed = new boolean[n];

        // Input process details
        for (int i = 0; i < n; i++) {
            pid[i] = i + 1;
            System.out.println("\nEnter details for Process " + pid[i] + ":");
            System.out.print("Arrival Time: ");
            at[i] = sc.nextInt();
            System.out.print("Burst Time: ");
            bt[i] = sc.nextInt();
            System.out.print("Priority (lower number = higher priority): ");
            prio[i] = sc.nextInt();
        }

        int completedCount = 0, currentTime = 0;
        double totalTAT = 0, totalWT = 0;

        while (completedCount < n) {
            int idx = -1;
            int highestPriority = Integer.MAX_VALUE;

            // Find highest priority process that has arrived
            for (int i = 0; i < n; i++) {
                if (!completed[i] && at[i] <= currentTime) {
                    if (prio[i] < highestPriority) {
                        highestPriority = prio[i];
                        idx = i;
                    } else if (prio[i] == highestPriority) {
                        // Tie-breaker: earlier arrival
                        if (at[i] < at[idx]) {
                            idx = i;
                        }
                    }
                }
            }

            if (idx != -1) {
                ct[idx] = currentTime + bt[idx];
                tat[idx] = ct[idx] - at[idx];
                wt[idx] = tat[idx] - bt[idx];

                totalTAT += tat[idx];
                totalWT += wt[idx];

                currentTime = ct[idx];
                completed[idx] = true;
                completedCount++;
            } else {
                currentTime++; // CPU idle
            }
        }

        // Output
        System.out.println("\nPID\tAT\tBT\tPRIO\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + pid[i] + "\t" + at[i] + "\t" + bt[i] + "\t" +
                    prio[i] + "\t" + ct[i] + "\t" + tat[i] + "\t" + wt[i]);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f", totalTAT / n);
        System.out.printf("\nAverage Waiting Time: %.2f\n", totalWT / n);
    }
}
