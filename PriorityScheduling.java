import java.util.*;

class Process {
    int pid, arrivalTime, burstTime, priority;
    int completionTime, turnAroundTime, waitingTime;
    boolean isCompleted = false;

    Process(int pid, int at, int bt, int prio) {
        this.pid = pid;
        this.arrivalTime = at;
        this.burstTime = bt;
        this.priority = prio;
    }
}

public class PriorityScheduling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("---------- PRIORITY SCHEDULING (Non-Preemptive) ----------");
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] processes = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for Process " + (i + 1) + ":");
            System.out.print("Arrival Time: ");
            int at = sc.nextInt();
            System.out.print("Burst Time: ");
            int bt = sc.nextInt();
            System.out.print("Priority (lower number = higher priority): ");
            int prio = sc.nextInt();
            processes[i] = new Process(i + 1, at, bt, prio);
        }

        int completed = 0, currentTime = 0;
        double totalTAT = 0, totalWT = 0;

        while (completed < n) {
            int idx = -1;
            int highestPriority = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                Process p = processes[i];
                if (!p.isCompleted && p.arrivalTime <= currentTime) {
                    if (p.priority < highestPriority) {
                        highestPriority = p.priority;
                        idx = i;
                    } else if (p.priority == highestPriority) {
                        if (p.arrivalTime < processes[idx].arrivalTime) {
                            idx = i;
                        }
                    }
                }
            }

            if (idx != -1) {
                Process p = processes[idx];
                p.completionTime = currentTime + p.burstTime;
                p.turnAroundTime = p.completionTime - p.arrivalTime;
                p.waitingTime = p.turnAroundTime - p.burstTime;

                totalTAT += p.turnAroundTime;
                totalWT += p.waitingTime;

                currentTime = p.completionTime;
                p.isCompleted = true;
                completed++;
            } else {
                currentTime++; // CPU is idle
            }
        }

        System.out.println("\nPID\tAT\tBT\tPRIO\tCT\tTAT\tWT");
        for (Process p : processes) {
            System.out.println("P" + p.pid + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" +
                    p.priority + "\t" + p.completionTime + "\t" + p.turnAroundTime + "\t" + p.waitingTime);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f", totalTAT / n);
        System.out.printf("\nAverage Waiting Time: %.2f\n", totalWT / n);
    }
}
