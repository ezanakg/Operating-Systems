import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler {
    public static void main(String[] args) {
        List<Process> processes = readProcesses("processes.txt");

        System.out.println("=== First-Come, First-Served (FCFS) ===");
        simulateFCFS(new ArrayList<>(processes));

        System.out.println("\n=== Shortest Job First (SJF) ===");
        simulateSJF(new ArrayList<>(processes));
    }

    public static List<Process> readProcesses(String filename) {
        List<Process> processes = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {
            sc.nextLine(); // Skip header
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().trim().split("\\s+");
                int pid = Integer.parseInt(line[0]);
                int arrival = Integer.parseInt(line[1]);
                int burst = Integer.parseInt(line[2]);
                int priority = Integer.parseInt(line[3]);
                processes.add(new Process(pid, arrival, burst, priority));
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return processes;
    }

    public static void simulateFCFS(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        AtomicInteger time = new AtomicInteger(0);
        List<Integer> gantt = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        MemoryManager mm = new MemoryManager(100);
        PageTable pt = new PageTable(3);

        for (Process p : processes) {
            if (!mm.allocate(p, p.burstTime * 2)) continue;

            if (time.get() < p.arrivalTime) {
                time.set(p.arrivalTime);
            }

            // Simulate paging: assume one page accessed per unit of burst
            for (int i = 0; i < p.burstTime; i++) {
                pt.accessPage(new Page(p.pid, i));
            }

            p.waitingTime = time.get() - p.arrivalTime;
            time.addAndGet(p.burstTime);
            p.turnaroundTime = p.waitingTime + p.burstTime;

            labels.add("P" + p.pid);
            gantt.add(time.get());

            mm.free(p);
        }

        printResults(processes, labels, gantt);
    }

    public static void simulateSJF(List<Process> processes) {
        List<Process> completed = new ArrayList<>();
        AtomicInteger time = new AtomicInteger(0);
        List<Integer> gantt = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        MemoryManager mm = new MemoryManager(100);
        PageTable pt = new PageTable(3);

        while (!processes.isEmpty()) {
            Process next = processes.stream()
                .filter(p -> p.arrivalTime <= time.get())
                .min(Comparator.comparingInt(p -> p.burstTime))
                .orElse(null);

            if (next == null) {
                time.incrementAndGet();
                continue;
            }

            if (!mm.allocate(next, next.burstTime * 2)) {
                processes.remove(next);
                continue;
            }

            // Simulate paging: assume one page accessed per unit of burst
            for (int i = 0; i < next.burstTime; i++) {
                pt.accessPage(new Page(next.pid, i));
            }

            processes.remove(next);
            next.waitingTime = time.get() - next.arrivalTime;
            time.addAndGet(next.burstTime);
            next.turnaroundTime = next.waitingTime + next.burstTime;
            completed.add(next);

            labels.add("P" + next.pid);
            gantt.add(time.get());

            mm.free(next);
        }

        printResults(completed, labels, gantt);
    }

    public static void printResults(List<Process> processes, List<String> labels, List<Integer> gantt) {
        System.out.print("Gantt Chart:\n| ");
        for (String label : labels) System.out.print(label + " | ");
        System.out.println();

        System.out.print("0 ");
        for (int t : gantt) System.out.print(String.format("%4d", t));
        System.out.println("\n");

        double totalWT = 0, totalTAT = 0;
        System.out.println("PID\tWT\tTAT");
        for (Process p : processes) {
            System.out.printf("%d\t%d\t%d\n", p.pid, p.waitingTime, p.turnaroundTime);
            totalWT += p.waitingTime;
            totalTAT += p.turnaroundTime;
        }

        System.out.printf("\nAverage WT: %.2f\n", totalWT / processes.size());
        System.out.printf("Average TAT: %.2f\n", totalTAT / processes.size());
    }
}
