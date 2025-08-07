import java.util.*;

class Process {

    int id, arrivalTime, burstTime, completionTime, turnAroundTime, waitingTime;

    boolean isCompleted = false;

    Process(int id, int at, int bt) {

        this.id = id;

        this.arrivalTime = at;

        this.burstTime = bt;

    }

}

public class SJF {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("-------- SHORTEST JOB FIRST (SJF) --------");

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

        int completed = 0, currentTime = 0;

        double totalTAT = 0, totalWT = 0;

        while (completed < n) {

            int idx = -1;

            int minBT = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {

                if (!processes[i].isCompleted && processes[i].arrivalTime <= currentTime) {

                    if (processes[i].burstTime < minBT) {

                        minBT = processes[i].burstTime;

                        idx = i;

                    } else if (processes[i].burstTime == minBT) {

                        if (processes[i].arrivalTime < processes[idx].arrivalTime) {

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

        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");

        for (Process p : processes) {

            System.out.println("P" + p.id + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" + p.completionTime + "\t" + p.turnAroundTime + "\t" + p.waitingTime);

        }

        System.out.printf("\nAverage Turnaround Time: %.2f", totalTAT / n);

        System.out.printf("\nAverage Waiting Time: %.2f\n", totalWT / n);

    }

}