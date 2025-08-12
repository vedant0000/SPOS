import java.util.*;

public class SJF {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("-------- SHORTEST JOB FIRST (SJF) --------");
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] pid = new int[n];
        int[] at = new int[n];
        int[] bt = new int[n];
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
        }

        int completedCount = 0, currentTime = 0;
        double totalTAT = 0, totalWT = 0;

        // SJF Scheduling
        while (completedCount < n) {
            int idx = -1;
            int minBT = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (!completed[i] && at[i] <= currentTime) {
                    if (bt[i] < minBT) {
                        minBT = bt[i];
                        idx = i;
                    } else if (bt[i] == minBT) {
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
                currentTime++;
            }
        }

        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + pid[i] + "\t" + at[i] + "\t" + bt[i] + "\t" +
                    ct[i] + "\t" + tat[i] + "\t" + wt[i]);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f", totalTAT / n);
        System.out.printf("\nAverage Waiting Time: %.2f\n", totalWT / n);
    }
}
