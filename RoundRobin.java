import java.util.*;

public class RoundRobin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("------------ ROUND ROBIN SCHEDULING ------------");
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] pid = new int[n];
        int[] at = new int[n];
        int[] bt = new int[n];
        int[] rt = new int[n]; // remaining time
        int[] ct = new int[n];
        int[] tat = new int[n];
        int[] wt = new int[n];
        boolean[] completed = new boolean[n];
        boolean[] inQueue = new boolean[n];

        // Input process details
        for (int i = 0; i < n; i++) {
            pid[i] = i + 1;
            System.out.println("\nEnter details for Process " + pid[i] + ":");
            System.out.print("Arrival Time: ");
            at[i] = sc.nextInt();
            System.out.print("Burst Time: ");
            bt[i] = sc.nextInt();
            rt[i] = bt[i];
        }

        System.out.print("\nEnter Time Quantum: ");
        int tq = sc.nextInt();

        Queue<Integer> queue = new LinkedList<>();
        int currentTime = 0, completedCount = 0;
        double totalTAT = 0, totalWT = 0;

        while (completedCount < n) {
            // Add newly arrived processes to the queue
            for (int i = 0; i < n; i++) {
                if (at[i] <= currentTime && !inQueue[i] && rt[i] > 0) {
                    queue.add(i);
                    inQueue[i] = true;
                }
            }

            if (queue.isEmpty()) {
                currentTime++;
                continue;
            }

            int idx = queue.poll(); // index of current process
            int execTime = Math.min(tq, rt[idx]);
            rt[idx] -= execTime;
            currentTime += execTime;

            // Add new arrivals during this time quantum
            for (int i = 0; i < n; i++) {
                if (at[i] <= currentTime && !inQueue[i] && rt[i] > 0) {
                    queue.add(i);
                    inQueue[i] = true;
                }
            }

            if (rt[idx] == 0 && !completed[idx]) {
                ct[idx] = currentTime;
                tat[idx] = ct[idx] - at[idx];
                wt[idx] = tat[idx] - bt[idx];

                totalTAT += tat[idx];
                totalWT += wt[idx];

                completed[idx] = true;
                completedCount++;
            } else {
                queue.add(idx); // put back into queue if not finished
            }
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
