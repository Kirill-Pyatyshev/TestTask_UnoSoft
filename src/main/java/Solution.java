import lombok.AllArgsConstructor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

    @AllArgsConstructor
    private static class PositionWord {
        public String value;
        public int position;
    }

    public static List<Set<String>> splittingIntoGroups(List<String> lines) {
        // Позиция значения<Значение, Номер группы>
        List<Map<String, Integer>> wordsToGroupsNumbers = new ArrayList<>();
        // Номер группы<Значения группы(или строки)>
        List<Set<String>> linesGroups = new ArrayList<>();
        // <Номер слитой группы, Номер группы в которую слили>
        Map<Integer, Integer> mergedGroupNumberToFinalGroupNumber = new HashMap<>();
        Pattern pattern = Pattern.compile("\"\\d+\"\\d+\"");
        boolean shouldBreak;
        for (String line : lines) {
            shouldBreak = false;
            String[] words = line.split(";");
            TreeSet<Integer> foundInGroups = new TreeSet<>();
            List<PositionWord> positionWords = new ArrayList<>();
            for (int i = 0; i < words.length; i++) {
                String word = words[i];

                Matcher matcher = pattern.matcher(word);
                if(matcher.find()){
                    shouldBreak = true;
                    break;
                }

                if (wordsToGroupsNumbers.size() == i)
                    wordsToGroupsNumbers.add(new HashMap<>());

                if (word.equals("\"\"") || word.equals("")) {
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
                linesGroups.add(new LinkedHashSet<>());
            } else {
                groupNumber = foundInGroups.first();
            }

            for (PositionWord newWord : positionWords) {
                wordsToGroupsNumbers.get(newWord.position).put(newWord.value, groupNumber);
            }

            for (int mergeGroupNumber : foundInGroups)
            {
                if (mergeGroupNumber != groupNumber)
                {
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
