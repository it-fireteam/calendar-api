package ru.lmkn.RuFreeDays.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import ru.lmkn.RuFreeDays.model.Day;
import ru.lmkn.RuFreeDays.service.CalendarService;

@RestController
@RequestMapping("calendar")
@AllArgsConstructor
public class CalendarController {

   private final CalendarService calendarService;

   @GetMapping(value = "/weekends", produces = "application/json")
    public List<Day> getFreeDays(@RequestParam(value = "year", defaultValue = "2020") String year) {
        return calendarService.findWeekends(year);
    }

}

