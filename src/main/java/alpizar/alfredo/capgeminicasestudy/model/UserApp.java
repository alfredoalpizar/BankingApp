package alpizar.alfredo.capgeminicasestudy.model;


import javax.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public class UserApp {

    private Long userId;

    @NotNull
    @Size(min=4, max=17)
    private String userName;

    @NotNull
    @Size(min=6, max=30)
    private String encryptedPassword;

    @NotNull
    @Size(min=1, max=30)
    private String firstName;

    @NotNull
    @Size(min=1, max=30)
    private String lastName;

    @NotNull
    @Size(min=4, max=30)
    private String address;

    private MultipartFile[] dataFile;


    public UserApp(){

    }

    public UserApp(Long userId, String userName, String encryptedPassword, String firstName, String lastName, String address) {

        this.userId = userId;
        this.userName = userName;
        this.encryptedPassword = encryptedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public MultipartFile[] getDataFile() {
        return dataFile;
    }

    public void setDataFile(MultipartFile[] dataFile) {
        this.dataFile = dataFile;
    }
    @Override
    public String toString() {
        return this.userName + "/" + this.encryptedPassword;
    }

}