package com.booking.payload;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class TimeSlotDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimeSlotDTO(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

