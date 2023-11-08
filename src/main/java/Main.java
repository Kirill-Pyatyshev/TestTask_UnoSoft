import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    public static void main(String[] args){

        try {
            long startTime = System.currentTimeMillis();
            String fileName = args[0];

            File fileTemp = new File("");
            String pathProject = fileTemp.getAbsolutePath();
            String path = "/src/main/resources/";


            File file = new File(pathProject + path + fileName);
            BufferedReader abc = new BufferedReader(new FileReader(file));
            List<String> lines = new ArrayList<>();

            String line;

            while ((line = abc.readLine()) != null) {
                lines.add(line);
            }
            abc.close();
            List<Set<String>> result = Solution.splittingIntoGroups(lines);

            System.out.println(lines.size());
            System.out.println(result.size());

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

            System.out.println("Program running time is " + (endTime - startTime) + " milliseconds");
            System.out.println("Or about " + (endTime - startTime)/1000 + " seconds");
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
