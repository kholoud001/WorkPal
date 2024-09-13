package entities;

import java.time.LocalDate;

public class Reservation {

    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean status;
    private int userId;
    private Integer spaceId;
    private Integer additionalServiceId;

    public Reservation(int id, LocalDate startDate, LocalDate endDate, boolean status, int userId, int spaceId, Integer additionalServiceId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.userId = userId;
        this.spaceId = spaceId;
        this.additionalServiceId = additionalServiceId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }

    public Integer getAdditionalServiceId() {
        return additionalServiceId;
    }

    public void setAdditionalServiceId(Integer additionalServiceId) {
        this.additionalServiceId = additionalServiceId;
    }
}
