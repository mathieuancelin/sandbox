package com.serli.osgi.cdi.services.impl;

import com.serli.osgi.cdi.services.DictionaryService;
import com.serli.osgi.cdi.services.SpellCheckerService;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.inject.Inject;

/**
 *
 * @author Mathieu ANCELIN
 */
public class SpellCheckerServiceImpl implements SpellCheckerService {

    @Inject
    private DictionaryService dictionaryService;

    @Override
    public List<String> check(String passage) {
        if ((passage == null) || (passage.length() == 0)) { 
            return null;
        }
        List<String> errorList = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(passage, " ,.!?;:");
        while (st.hasMoreTokens()) {
            String word = st.nextToken();
            if (! dictionaryService.checkWord(word)) {
                errorList.add(word);
            }
        }
        if (errorList.isEmpty()) { 
            return null;
        }
        System.out.println("Wrong words:" + errorList);
        return errorList;
    }
}

