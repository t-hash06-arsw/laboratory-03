package edu.eci.arsw.springdemo.ui;

import edu.eci.arsw.springdemo.GrammarChecker;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String a[]) {
        try (ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml")) {
            GrammarChecker gc = ac.getBean(GrammarChecker.class);
            System.out.println(gc.check("la la la "));
        }
    }
}
