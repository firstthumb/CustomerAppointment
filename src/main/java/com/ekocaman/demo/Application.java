package com.ekocaman.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);

        // Get calendar set to current date and time
        Calendar c = Calendar.getInstance();

// Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.add(Calendar.WEEK_OF_MONTH, 1);

// Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            System.out.println(df.format(c.getTime()));
            c.add(Calendar.DATE, 1);
        }
    }
}
