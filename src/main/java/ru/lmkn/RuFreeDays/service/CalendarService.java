package ru.lmkn.RuFreeDays.service;

import ru.lmkn.RuFreeDays.model.Day;

import java.util.List;

public interface CalendarService {
    List<Day> findWeekends(String year);
}
