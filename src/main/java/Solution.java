import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

    @AllArgsConstructor
    private static class PositionWord {
        public String value;
        public int position;
    }

    public static List<Set<String>> splittingIntoGroups(File file) throws IOException {
        List<Map<String, Integer>> wordsToGroupsNumbers = new ArrayList<>();
        List<Set<String>> linesGroups = new ArrayList<>();
        Map<Integer, Integer> mergedGroupNumberToFinalGroupNumber = new HashMap<>();
        Pattern pattern = Pattern.compile("\"\\d+\"\\d+\"");
        boolean shouldBreak;

        LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        while (it.hasNext()) {
            String line = it.nextLine();
            shouldBreak = false;
            String[] words = line.split(";");
            HashSet<Integer> foundInGroups = new HashSet<>();
            List<PositionWord> positionWords = new ArrayList<>();

            for (int i = 0; i < words.length; i++) {
                String word = words[i];

                Matcher matcher = pattern.matcher(word);
                if (matcher.find()) {
                    shouldBreak = true;
                    break;
                }

                if (wordsToGroupsNumbers.size() == i)
                    wordsToGroupsNumbers.add(new HashMap<>());

                if (word.equals("\"\"") || word.isEmpty()) {
                    continue;
                }

                Map<String, Integer> wordToGroupNumber = wordsToGroupsNumbers.get(i);
                Integer wordGroupNumber = wordToGroupNumber.get(word);

                if (wordGroupNumber != null) {
                    while (mergedGroupNumberToFinalGroupNumber.containsKey(wordGroupNumber))
                        wordGroupNumber = mergedGroupNumberToFinalGroupNumber.get(wordGroupNumber);

                    foundInGroups.add(wordGroupNumber);
                } else {
                    positionWords.add(new PositionWord(word, i));
                }
            }

            if (shouldBreak) {
                continue;
            }

            int groupNumber;

            if (foundInGroups.isEmpty()) {
                groupNumber = linesGroups.size();
                linesGroups.add(new HashSet<>());
            } else {
                groupNumber = foundInGroups.iterator().next();
            }

            for (PositionWord newWord : positionWords) {
                wordsToGroupsNumbers.get(newWord.position).put(newWord.value, groupNumber);
            }

            for (int mergeGroupNumber : foundInGroups) {
                if (mergeGroupNumber != groupNumber) {
                    mergedGroupNumberToFinalGroupNumber.put(mergeGroupNumber, groupNumber);
                    linesGroups.get(groupNumber).addAll(linesGroups.get(mergeGroupNumber));
                    linesGroups.set(mergeGroupNumber, null);
                }
            }

            linesGroups.get(groupNumber).add(line);
        }

        linesGroups.removeAll(Collections.singleton(null));

        return linesGroups;
    }
}
