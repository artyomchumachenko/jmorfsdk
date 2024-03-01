package ru.textanalysis.tawt.jmorfsdk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.textanalysis.tawt.ms.grammeme.MorfologyParameters;
import ru.textanalysis.tawt.ms.model.jmorfsdk.Form;

public class Exploring1 {
    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        JMorfSdk jMorfSdk = JMorfSdkFactory.loadFullLibrary();
        Map<Integer, List<Form>> allForms = jMorfSdk.getAllForms();

        Set<String> wordsNounAndGenitive = new HashSet<>();
        Set<Long> notGenitiveCasesMatches = new HashSet<>();
        Map<Long, Integer> notGenitiveCasesMatchesCount = new HashMap<>();
        for (Integer key : allForms.keySet()) {
            List<Form> currForms = allForms.get(key);
            for (Form currForm : currForms) {
                if (!currForm.isContainsTypeOfSpeech(MorfologyParameters.TypeOfSpeech.NOUN)) {
                    break;
                }
                if (currForm.getMorfCharacteristicsByIdentifier(MorfologyParameters.Case.IDENTIFIER) !=
                        MorfologyParameters.Case.GENITIVE) {
                    // searching
                    if (wordsNounAndGenitive.contains(currForm.getMyString())) {
                        long notGenitiveCase = currForm.getMorfCharacteristicsByIdentifier(MorfologyParameters.Case.IDENTIFIER);
                        if (notGenitiveCasesMatchesCount.get(notGenitiveCase) != null) {
                            int count = notGenitiveCasesMatchesCount.get(notGenitiveCase);
                            count++;
                            notGenitiveCasesMatchesCount.put(notGenitiveCase, count);
                            continue;
                        }
                        notGenitiveCasesMatches.add(notGenitiveCase);
                        notGenitiveCasesMatchesCount.put(currForm.getMorfCharacteristicsByIdentifier(MorfologyParameters.Case.IDENTIFIER), 1);
                    }
                } else {
                    wordsNounAndGenitive.add(currForm.getMyString());
                }
            }
        }
        Long finish = System.currentTimeMillis();
        System.out.println(finish - start);
        System.out.println(notGenitiveCasesMatches);
    }
}
