import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


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
            AtomicInteger numGroup = new AtomicInteger(1);

            for (int i = result.stream().mapToInt(Set::size).max().getAsInt(); i >= 1; i--) {
                int maxVal = i;

                result.stream().filter(strings -> strings.size() == maxVal).collect(Collectors.toSet()).forEach(s -> {
                    System.out.println("_________________________________________________________________________________________________________________________________________________________");
                    System.out.println("Group " + numGroup.getAndIncrement());
                    for (String s1 : s) {
                        System.out.println(s1);
                    }
                    System.out.println("_________________________________________________________________________________________________________________________________________________________");
                    System.out.println();
                });
            }
            long endTime = System.currentTimeMillis();

            System.out.println("Program running time is " + (endTime - startTime) + " milliseconds");
            System.out.println("Or about " + (endTime - startTime)/1000 + " seconds");
            System.out.println("Groups with more than one element: " + result.stream().filter(s -> s.size() >= 2).count());
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
