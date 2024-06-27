package cbcoder.dealerwebapp.Cars.Dtos;

import cbcoder.dealerwebapp.Cars.enums.CarStatus;
import cbcoder.dealerwebapp.Cars.enums.ValeterStatus;
import cbcoder.dealerwebapp.Cars.enums.WorkshopServiceStatus;
import cbcoder.dealerwebapp.Cars.model.Car;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link Car}
 */
public class CarDto implements Serializable {
    private Long carId;
    private int keyNumber;
    @CreatedDate
    private LocalDateTime dateCreated;
    @LastModifiedDate
    private LocalDateTime dateUpdated;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime handoverDate;
    private String buyerName;
    private String comments;
    private CarStatus carStatus;
    private Set<WorkshopServiceStatus> workshopServiceStatus;
    private Set<ValeterStatus> valeterStatus;
    private String make;
    private String model;
    private String color;
    private String regNumber;
    private String chassisNumber;
    private Long userId;

    public CarDto() {
    }

    public CarDto(Long carId, int keyNumber, LocalDateTime dateCreated, LocalDateTime dateUpdated, LocalDateTime handoverDate, String buyerName, Long userId,
                  String comments, CarStatus carStatus, Set<WorkshopServiceStatus> workshopServiceStatus, Set<ValeterStatus> valeterStatus, String make, String model, String color, String regNumber, String chassisNumber) {
        this.carId = carId;
        this.keyNumber = keyNumber;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.handoverDate = handoverDate;
        this.buyerName = buyerName;
        this.userId = userId;
        this.comments = comments;
        this.carStatus = carStatus;
        this.workshopServiceStatus = workshopServiceStatus;
        this.valeterStatus = valeterStatus;
        this.make = make;
        this.model = model;
        this.color = color;
        this.regNumber = regNumber;
        this.chassisNumber = chassisNumber;
    }

    public Long getCarId() {
        return carId;
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public LocalDateTime getHandoverDate() {
        return handoverDate;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getComments() {
        return comments;
    }

    public CarStatus getCarStatus() {
        return carStatus;
    }

    public Set<WorkshopServiceStatus> getWorkshopServiceStatus() {
        return workshopServiceStatus;
    }

    public Set<ValeterStatus> getValeterStatus() {
        return valeterStatus;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDto entity = (CarDto) o;
        return Objects.equals(this.carId, entity.carId) &&
                Objects.equals(this.keyNumber, entity.keyNumber) &&
                Objects.equals(this.dateCreated, entity.dateCreated) &&
                Objects.equals(this.dateUpdated, entity.dateUpdated) &&
                Objects.equals(this.handoverDate, entity.handoverDate) &&
                Objects.equals(this.buyerName, entity.buyerName) &&
                Objects.equals(this.userId, entity.userId) &&
                Objects.equals(this.comments, entity.comments) &&
                Objects.equals(this.carStatus, entity.carStatus) &&
                Objects.equals(this.workshopServiceStatus, entity.workshopServiceStatus) &&
                Objects.equals(this.valeterStatus, entity.valeterStatus) &&
                Objects.equals(this.make, entity.make) &&
                Objects.equals(this.model, entity.model) &&
                Objects.equals(this.color, entity.color) &&
                Objects.equals(this.regNumber, entity.regNumber) &&
                Objects.equals(this.chassisNumber, entity.chassisNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId, keyNumber, dateCreated, dateUpdated, handoverDate, buyerName, userId, comments, carStatus, workshopServiceStatus,
                valeterStatus, make, model, color, regNumber, chassisNumber);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "carId = " + carId + ", " +
                "keyNumber = " + keyNumber + ", " +
                "dateCreated = " + dateCreated + ", " +
                "dateUpdated = " + dateUpdated + ", " +
                "handoverDate = " + handoverDate + ", " +
                "buyerName = " + buyerName + ", " +
                "userId = " + userId + ", " +
                "comments = " + comments + ", " +
                "carStatus = " + carStatus + ", " +
                "workshopServiceStatus = " + workshopServiceStatus + ", " +
                "valeterStatus = " + valeterStatus + ", " +
                "make = " + make + ", " +
                "model = " + model + ", " +
                "color = " + color + ", " +
                "regNumber = " + regNumber + ", " +
                "chassisNumber = " + chassisNumber + ")";
    }
}