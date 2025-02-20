package main.java.model;



public class Member {
	private int memberId;
	private int userId;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String email;
    
    private byte[] imageData;

    // Constructor
    public Member() {
    }

    public Member(int memberId,int userId,  String firstName, String lastName, String address, String phoneNumber, String email, byte[] imageData) {
    	this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.imageData = imageData;
    }

    // Getters and Setters 
    public int getUserId() {
    	return userId;
    }
    
    public void setUserId(int userId) {
    	this.userId = userId;
    }
    
    public int getMemberId() {
    	return memberId;
    }
    
    public void setMemberId(int memberId) {
    	this.memberId = memberId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }


    // toString method (optional, for easy printing)
    @Override
    public String toString() {
        return "Member{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
