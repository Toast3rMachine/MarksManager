package com.api.marksmanager.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateCourseRequest {

    @NotBlank
    private String name;

    @NotNull
    @Min(1)
    @Max(31)
    private int date; //Day of the month

    @NotNull
    @Min(8)
    @Max(16)
    private int startHour;

    @NotNull
    @Min(9)
    @Max(18)
    private int endHour;

    public CreateCourseRequest() {}

    public CreateCourseRequest(String name, int date, int startHour, int endHour) {
        this.name = name;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotNull int getDate() {
        return date;
    }

    public void setDate(@NotNull int date) {
        this.date = date;
    }

    @NotNull
    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(@NotNull int startHour) {
        this.startHour = startHour;
    }

    @NotNull
    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(@NotNull int endHour) {
        this.endHour = endHour;
    }
}
