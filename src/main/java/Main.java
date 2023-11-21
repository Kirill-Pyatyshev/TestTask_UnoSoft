import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    public static void main(String[] args) {

        try {
            long startTime = System.currentTimeMillis();

            String pathToFile = args[0];


            File file = new File("");

            File file1 = new File(file.getAbsolutePath() + "/" + pathToFile);
            List<Set<String>> result = Solution.splittingIntoGroups(file1);

            outputToFile(result, startTime);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    static class SizeComparator implements Comparator<Set<?>> {

        @Override
        public int compare(Set<?> o1, Set<?> o2) {
            return Integer.valueOf(o2.size()).compareTo(o1.size());
        }
    }

    static void outputToFile(List<Set<String>> result, long startTime) throws IOException {

        File resultFile = new File("result.txt");
        resultFile.createNewFile();

        FileWriter fileWriter = new FileWriter(resultFile);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        AtomicInteger numGroup = new AtomicInteger(1);
        Collections.sort(result, new SizeComparator());
        result.forEach(s -> {
            printWriter.println("_________________________________________________________________________________________________________________________________________________________");
            printWriter.println("Group " + numGroup.getAndIncrement());
            for (String s1 : s) {
                printWriter.println(s1);
            }
            printWriter.println("_________________________________________________________________________________________________________________________________________________________");
            printWriter.println();
        });

        long endTime = System.currentTimeMillis();

        printWriter.println("Program running time is " + (endTime - startTime) + " milliseconds or about " + (endTime - startTime) / 1000 + " seconds");
        printWriter.println("Groups with more than one element: " + result.stream().filter(s -> s.size() >= 2).count());
        printWriter.println();
        printWriter.close();
    }
}
