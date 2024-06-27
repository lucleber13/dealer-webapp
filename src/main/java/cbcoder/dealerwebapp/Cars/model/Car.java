package cbcoder.dealerwebapp.Cars.model;

import cbcoder.dealerwebapp.Cars.enums.CarStatus;
import cbcoder.dealerwebapp.Cars.enums.ValeterStatus;
import cbcoder.dealerwebapp.Cars.enums.WorkshopServiceStatus;
import cbcoder.dealerwebapp.UsersInfo.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CARS")
@SequenceGenerator(name = "cars_seq", sequenceName = "cars_seq", allocationSize = 1)
public class Car implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cars_seq")
    private Long carId;

    @Column(name = "MAKE", nullable = false)
    private String make;

    @Column(name = "MODEL", nullable = false)
    private String model;

    @Column(name = "COLOR", nullable = false)
    private String color;

    @Column(name = "REG_NUMBER", unique = true, nullable = false)
    private String regNumber;

    @Column(name = "CHASSIS_NUMBER", unique = true, nullable = false)
    private String chassisNumber;

    @Column(name = "KEY_NUMBER", nullable = false)
    private int keyNumber;

    @Column(name = "DATE_CREATED", updatable = false)
    @CreatedDate
    private LocalDateTime dateCreated = LocalDateTime.now();

    @Column(name = "DATE_UPDATED")
    @LastModifiedDate
    private LocalDateTime dateUpdated;

    @Column(name = "HANDOVER_DATE")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm", shape = JsonFormat.Shape.STRING, timezone = "Europe/London")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime handoverDate;

    @Column(name = "BUYER_NAME")
    private String buyerName;

    @Column(name = "COMMENTS")
    private String comments;

    @Enumerated(EnumType.STRING)
    @Column(name = "CAR_STATUS")
    private CarStatus carStatus;

    @ElementCollection(targetClass = WorkshopServiceStatus.class)
    @CollectionTable(name = "workshop_service_status")
    @Enumerated(EnumType.STRING)
    @Column(name = "WORKSHOP_SERVICE_STATUS")
    private Set<WorkshopServiceStatus> workshopServiceStatus = new HashSet<>();

    @ElementCollection(targetClass = ValeterStatus.class)
    @CollectionTable(name = "valeter_status")
    @Enumerated(EnumType.STRING)
    @Column(name = "VALETER_STATUS")
    private Set<ValeterStatus> valeterStatus = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_cars",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new LinkedHashSet<>();

    public Car() {
    }

    public Car(String make, String model, String color, String regNumber, String chassisNumber, int keyNumber, String buyerName,
               String comments, LocalDateTime dateCreated) {
        this.make = make;
        this.model = model;
        this.color = color;
        this.regNumber = regNumber;
        this.chassisNumber = chassisNumber;
        this.keyNumber = keyNumber;
        this.buyerName = buyerName;
        this.comments = comments;
        this.dateCreated = dateCreated;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make.toUpperCase().charAt(0) + make.substring(1).toLowerCase();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model.toUpperCase().charAt(0) + model.substring(1).toLowerCase();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color.toUpperCase().charAt(0) + color.substring(1).toLowerCase();
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber.toUpperCase();
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber.toUpperCase();
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(int keyNumber) {
        this.keyNumber = keyNumber;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDateTime getHandoverDate() {
        return handoverDate;
    }

    public void setHandoverDate(LocalDateTime handoverDate) {
        this.handoverDate = handoverDate;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public CarStatus getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }

    public Set<WorkshopServiceStatus> getWorkshopServiceStatus() {
        return workshopServiceStatus;
    }

    public void setWorkshopServiceStatus(Set<WorkshopServiceStatus> workshopServiceStatus) {
        this.workshopServiceStatus = workshopServiceStatus;
    }

    public Set<ValeterStatus> getValeterStatus() {
        return valeterStatus;
    }

    public void setValeterStatus(Set<ValeterStatus> valeterStatus) {
        this.valeterStatus = valeterStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car car)) return false;
        return Objects.equals(carId, car.carId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(carId);
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", regNumber='" + regNumber + '\'' +
                ", chassisNumber='" + chassisNumber + '\'' +
                ", keyNumber=" + keyNumber +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                ", handoverDate=" + handoverDate +
                ", buyerName='" + buyerName + '\'' +
                ", comments='" + comments + '\'' +
                ", carStatus=" + carStatus +
                ", workshopServiceStatus=" + workshopServiceStatus +
                ", valeterStatus=" + valeterStatus +
                ", users=" + users +
                '}';
    }
}
