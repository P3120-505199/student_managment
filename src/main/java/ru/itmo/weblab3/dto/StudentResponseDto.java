package ru.itmo.weblab3.dto;

public class StudentResponseDto {
    private final long id;
    private final String fullName;
    private final int isuId;

    public StudentResponseDto(long id, String fullName, int isuId) {
        this.id = id;
        this.fullName = fullName;
        this.isuId = isuId;
    }

    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public int getIsuId() {
        return isuId;
    }
}