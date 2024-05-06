package Time_Calculation;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Time_calc {

    static String times = "0900-1830-B30,0900-1730-B30,0900-1730-B30,0900-1730-B30,0900-1730-B30,OFF,OFF";

    public String splitting_times(String times) {
        StringBuilder result = new StringBuilder();
        String[] splitted_times = times.split(",");
        boolean regularSchedule = true;
        LocalTime StartTime1 = null;
        LocalTime EndTime1 = null;
        Instant end = null;
        int day = 1;

        for (String i : splitted_times) {
            String[] day_wise_timing = i.split("-");
            if (day_wise_timing.length == 3) {
                LocalTime startTime = parseTimeString(day_wise_timing[0]);
                LocalTime endTime = parseTimeString(day_wise_timing[1]);
                LocalDate ld=LocalDate.of(2024,05, 06);
                Instant startTimeInstant = convertToInstant(startTime,ld);
               
                Instant endTimeInstant = convertToInstant(endTime,ld);
                ld.plusDays(1);
//                System.out.println(startTimeInstant);
//                System.out.println(endTimeInstant);
                if (StartTime1 == null && EndTime1 == null) {
                	StartTime1 = startTime;
                	EndTime1 = endTime;
                } else {
                    if (!StartTime1.equals(startTime) || !EndTime1.equals(endTime)) {
                        regularSchedule = false;
                    }
                }
                result.append(view_Work_Duration(startTime, endTime, day,ld));
                if (end != null) {
                    Duration duration = Duration_Between_Days(end, startTimeInstant);
                    result.append("Duration Between Days:\n");
                    result.append("Hours: ").append(duration.toHours()).append("\n");
                    result.append("Minutes: ").append(duration.toMinutes() % 60).append("\n");
                }
                end = endTimeInstant;
            } else {
                result.append("Day").append(day).append(": OFF DAY\n");
            }
            day++;
            
        }
        result.append(view_scheduleType(regularSchedule));
        return result.toString();
    }

    LocalTime parseTimeString(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        return LocalTime.parse(timeString, formatter);
    }

    Instant convertToInstant(LocalTime time,LocalDate date) {
    	LocalDate currentDate = date;
    	ZonedDateTime zonedDateTime = time.atDate(currentDate).atZone(ZoneId.of("Asia/Kolkata"));
    	
        return zonedDateTime.toInstant();
    }

    private String view_Work_Duration(LocalTime startTime, LocalTime endTime, int day,LocalDate ld) {
        Duration workDuration = Duration.between(convertToInstant(startTime,ld), convertToInstant(endTime,ld));
        long hours = workDuration.toHours();
        long remainingMinutes = workDuration.toMinutes() % 60;
        StringBuilder result = new StringBuilder();
        result.append("Day ").append(day).append(" Timings:\n");
        result.append("Start Time: ").append(startTime).append(", End Time: ").append(endTime)
                .append(", Break Time: ").append(30).append(" Hours ").append(hours)
                .append(" Minutes ").append(remainingMinutes).append("\n");
        return result.toString();
    }

    Duration Duration_Between_Days(Instant end, Instant startTime) {
        Duration duration = Duration.between(end, startTime);
        if (duration.isNegative()) {
            duration = duration.plusHours(24);
        }
        return duration;
    }

    private String view_scheduleType(boolean regularSchedule) {
        if (regularSchedule) {
            return "Regular Schedule\n";
        } else {
            return "Variable Schedule\n";
        }
    }

    public static void main(String[] args) {
        Time_calc emp1 = new Time_calc();
        System.out.println(emp1.splitting_times(times));
    }
}

