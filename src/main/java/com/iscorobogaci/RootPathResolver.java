package com.iscorobogaci;

import com.iscorobogaci.enums.SortingBy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class RootPathResolver {

    public String resolveRootPath(LocalDate creationDate, SortingBy sortingBy) {
        switch (sortingBy) {
            case WEEK:
                return rootPathByWeek(creationDate);
            case MONTH:
                return rootPathByMonth(creationDate);
        }
        return null;
    }

    private String rootPathByWeek(LocalDate creationDate) {
        final DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        return creationDate.with(TemporalAdjusters.previousOrSame(firstDayOfWeek)).toString();
    }

    private String rootPathByMonth(LocalDate creationDate) {
        return creationDate.with(TemporalAdjusters.firstDayOfMonth()).toString();
    }


}
