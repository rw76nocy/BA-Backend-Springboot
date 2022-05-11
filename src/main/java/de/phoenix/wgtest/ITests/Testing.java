package de.phoenix.wgtest.ITests;

import de.phoenix.wgtest.model.management.ERole;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Testing {

    @Test
    public void test() {
        First f = null;

        List<Map.Entry<A, ERole>> data = new ArrayList<>();
        addEntrytoList(data, new First(1L), ERole.GUARDIAN);
        addEntrytoList(data, f, ERole.MOTHER);
        addEntrytoList(data, new Second("Sec"), ERole.ASD);
        addEntrytoList(data, new Third(new Date()), ERole.CHILDDOCTOR);

        Map<A, ERole> test = listToRoleMap(data);

        for (A a : test.keySet()) {

            if (a instanceof First) {
                First first = (First) a;
                first.setId(1L);
                System.out.println("I am an instance of First.");
                System.out.println("My id is: "+first.getId());
                System.out.println("My ERole is: "+test.get(a).toString());
            }

            if (a instanceof Second) {
                Second second = (Second) a;
                second.setName("Sec");
                System.out.println("I am an instance of Second.");
                System.out.println("My name is: "+second.getName());
                System.out.println("My ERole is: "+test.get(a).toString());
            }

            if (a instanceof Third) {
                Third third = (Third) a;
                System.out.println("I am an instance of Third.");
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                String strDate = dateFormat.format(third.getDate());
                System.out.println("My date is: "+ strDate);
                System.out.println("My ERole is: "+test.get(a).toString());
            }

            if (a == null) {
                System.out.println("Something goes wrong!");
            }

        }
    }

    public Map<A, ERole> listToRoleMap(List<Map.Entry<A, ERole>> list) {
        Map<A, ERole> map = new HashMap<>();
        for (Map.Entry<A, ERole> entry : list) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    public void addEntrytoList(List<Map.Entry<A, ERole>> list, A a, ERole e) {
        if (a != null) {
            list.add(Map.entry(a, e));
        }
    }
}
