import java.util.*;

class Process {
    int pid, arrivalTime, burstTime, remainingTime, completionTime, turnAroundTime, waitingTime;
    boolean isCompleted = false;

    Process(int pid, int at, int bt) {
        this.pid = pid;
        this.arrivalTime = at;
        this.burstTime = bt;
        this.remainingTime = bt;
    }
}

public class RoundRobin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("------------ ROUND ROBIN SCHEDULING ------------");
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] processes = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for Process " + (i + 1) + ":");
            System.out.print("Arrival Time: ");
            int at = sc.nextInt();
            System.out.print("Burst Time: ");
            int bt = sc.nextInt();
            processes[i] = new Process(i + 1, at, bt);
        }

        System.out.print("\nEnter Time Quantum: ");
        int tq = sc.nextInt();

        Queue<Process> queue = new LinkedList<>();
        int currentTime = 0, completed = 0;
        double totalTAT = 0, totalWT = 0;
        boolean[] isInQueue = new boolean[n];

        while (completed < n) {
            // Add newly arrived processes to queue
            for (int i = 0; i < n; i++) {
                if (processes[i].arrivalTime <= currentTime && !isInQueue[i] && processes[i].remainingTime > 0) {
                    queue.add(processes[i]);
                    isInQueue[i] = true;
                }
            }

            if (queue.isEmpty()) {
                currentTime++;
                continue;
            }

            Process current = queue.poll();

            int execTime = Math.min(tq, current.remainingTime);
            current.remainingTime -= execTime;
            currentTime += execTime;

            // Add new arrivals during this time quantum
            for (int i = 0; i < n; i++) {
                if (processes[i].arrivalTime <= currentTime && !isInQueue[i] && processes[i].remainingTime > 0) {
                    queue.add(processes[i]);
                    isInQueue[i] = true;
                }
            }

            if (current.remainingTime == 0 && !current.isCompleted) {
                current.completionTime = currentTime;
                current.turnAroundTime = current.completionTime - current.arrivalTime;
                current.waitingTime = current.turnAroundTime - current.burstTime;

                totalTAT += current.turnAroundTime;
                totalWT += current.waitingTime;

                current.isCompleted = true;
                completed++;
            } else {
                queue.add(current); // Add back to queue if not finished
            }
        }

        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        for (Process p : processes) {
            System.out.println("P" + p.pid + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" +
                    p.completionTime + "\t" + p.turnAroundTime + "\t" + p.waitingTime);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f", totalTAT / n);
        System.out.printf("\nAverage Waiting Time: %.2f\n", totalWT / n);
    }
}