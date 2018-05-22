// Ilay Raz
// ilraz
// CMPS12B-02
// 2/23/18
// Simulation.java


import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.io.PrintWriter;

public class Simulation {
    private static PrintWriter trace;
    private static PrintWriter report;

    public static void main(String[] args) throws IOException {
        if(args.length < 1) {
            trace.println("Usage: Simulation <input_file>");
            System.exit(1);
        }

        trace = new PrintWriter(args[0] + ".trc");
        report = new PrintWriter(args[0] + ".rpt");

        Queue input = getInput(args[0]);

        trace.println("Trace file: " + args[0] + ".trc");
        trace.println(input.length() + " Jobs:");
        trace.println(input);
        trace.println();

        report.println("Report file: " + args[0] + ".rpt");
        report.println(input.length() + " Jobs:");
        report.println(input);
        report.println();
        report.println("***********************************************************");

        for (int pCount = 1; pCount < input.length(); pCount++) {
            runWorld(pCount, input.clone());
            resetJobs(input.clone());
        }

        trace.close();
        report.close();
    }

    private static Queue getInput(String arg) throws IOException {
        Queue input = new Queue();
        String[] lines = Files.lines(Paths.get(arg)).toArray(String[]::new);
        int length = Integer.parseInt(lines[0]);
        for (int i = 1; i <= length; i++)
            input.enqueue(new Job(Integer.parseInt(lines[i].substring(0, lines[i].indexOf(" "))), Integer.parseInt(lines[i].substring(lines[i].indexOf(" ") + 1, lines[i].length()))));
        return input;
    }

    private static void resetJobs(Queue queue) {
        while (!queue.isEmpty())
            ((Job)(queue.dequeue())).resetFinishTime();
    }

    private static void printProcessReport(int pCount, Queue queue) {
        int totalWait, maxWait, length, wait;

        totalWait = maxWait = wait = 0;
        length = queue.length();
        while (!queue.isEmpty()) {
            wait = ((Job)queue.dequeue()).getWaitTime();
            totalWait += wait;
            maxWait = wait > maxWait ? wait : maxWait;
        }

        if (pCount == 1)
            report.print("1 processor: ");
        else
            report.print(pCount + " processors: ");
        report.print("totalWait=" + totalWait);
        report.print(", maxWait=" + maxWait);
        report.println(", averageWait=" + String.format("%.2f", ((double)totalWait) / length));
    }

    private static void printTraceHeader(int pCount) {
        trace.println("*****************************");
        if (pCount == 1)
            trace.println("1 processor:");
        else
            trace.println(pCount + " processors:");
        trace.println("*****************************");
    }

    private static void printTraceTick(int t, Queue input, Queue[] processor) {
        trace.println("time=" + t);
        trace.println("0: " + input.toString());
        for (int i = 0; i < processor.length; i++)
            trace.println((i+1) + ": " + processor[i].toString());
        trace.println();
    }

    private static Queue getShortestQueue(Queue[] queueArray) {
        Queue queue = queueArray[0];
        for (Queue q : queueArray)
            if (q.length() < queue.length())
                queue = q;
        return queue;
    }

    private static boolean allQueuesEmpty(Queue[] queueArray) {
        for (Queue q : queueArray)
            if (!q.isEmpty())
                return false;
        return true;
    }

    private static void runWorld(int pCount, Queue input) {
        Queue[] processor = new Queue[pCount];
        for (int i = 0; i < pCount; i++)
            processor[i] = new Queue();

        printTraceHeader(pCount);

        boolean toPrint = true;
        for (int t = 0; (!input.isEmpty() && ((Job)input.peek()).getFinish() == Job.UNDEF) || !allQueuesEmpty(processor); t++) {
            for (Queue p : processor)
                if (!p.isEmpty() && ((Job)p.peek()).getFinish() == Job.UNDEF)
                    ((Job)p.peek()).computeFinishTime(t);

            for (Queue p : processor)
                if (!p.isEmpty() && ((Job)p.peek()).getFinish() == t) {
                    input.enqueue(p.dequeue());
                    toPrint = true;
                }

            for (Queue p : processor)
                if (!p.isEmpty() && ((Job)p.peek()).getFinish() == Job.UNDEF)
                    ((Job)p.peek()).computeFinishTime(t);

            while(!input.isEmpty() && ((Job)input.peek()).getArrival() == t) {
                getShortestQueue(processor).enqueue(input.dequeue());
                toPrint = true;
            }

            for (Queue p : processor)
                if (!p.isEmpty() && ((Job)p.peek()).getFinish() == Job.UNDEF)
                    ((Job)p.peek()).computeFinishTime(t);

            if (toPrint)
                printTraceTick(t, input, processor);
            toPrint = false;
        }

        printProcessReport(pCount, input);
    }
}
