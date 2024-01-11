package com.todo.backend.service;

import java.time.DayOfWeek;
import java.util.Arrays;

public class DayOfWeekConverter {
    public static int convertToBitset(DayOfWeek[] selectedDays) {
        int bitset = 0;

        for (DayOfWeek selectedDay : selectedDays) {
            int dayIndex = selectedDay.getValue() % 7; // 0부터 6까지의 값으로 변환
            bitset |= 1 << dayIndex;
        }

        return bitset;
    }

    public static DayOfWeek[] convertToDays(int bitset) {
        DayOfWeek[] selectedDays = new DayOfWeek[7];

        for (int i = 0; i < 7; i++) {
            if ((bitset & (1 << i)) != 0) {
                selectedDays[i] = DayOfWeek.of((i + 1) % 7 + 1); // 1부터 7까지의 값으로 변환
            }
        }

        return Arrays.copyOf(selectedDays, Long.bitCount(bitset));
    }
}
