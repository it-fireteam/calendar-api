package ru.lmkn.RuFreeDays.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Day {
    private LocalDate date;
    private String type;
}
