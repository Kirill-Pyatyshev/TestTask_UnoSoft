import java.io.File;
import java.io.IOException;
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
            File file = new File(pathToFile);
            List<Set<String>> result = Solution.splittingIntoGroups(file);

            long endTimeWithoutGroup = System.currentTimeMillis();

            AtomicInteger numGroup = new AtomicInteger(1);
            Collections.sort(result, new SizeComparator());
            result.forEach(s -> {
                    System.out.println("_________________________________________________________________________________________________________________________________________________________");
                    System.out.println("Group " + numGroup.getAndIncrement());
                    for (String s1 : s) {
                        System.out.println(s1);
                    }
                    System.out.println("_________________________________________________________________________________________________________________________________________________________");
                    System.out.println();
                });

            long endTime = System.currentTimeMillis();

            System.out.println("The execution time of the program WITHOUT the output of groups is " + (endTimeWithoutGroup - startTime) + " milliseconds or about " + (endTimeWithoutGroup - startTime) / 1000 + " seconds");
            System.out.println("Program running time is " + (endTime - startTime) + " milliseconds or about " + (endTime - startTime) / 1000 + " seconds");
            System.out.println("Groups with more than one element: " + result.stream().filter(s -> s.size() >= 2).count());
            System.out.println();
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
}
