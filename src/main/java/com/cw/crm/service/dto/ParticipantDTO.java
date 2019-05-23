package com.cw.crm.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Participant entity.
 */
public class ParticipantDTO implements Serializable {

    private Long id;

    @NotNull
    private String firstName;

    private String middleInitial;

    @NotNull
    private String lastName;

    private String title;

    private LocalDate registrationDate;

    private String address;

    private String city;

    private String state;

    private LocalDate dob;

    private String primaryPhone;

    private String primaryPhoneType;

    private String secondaryPhone;

    private String secondaryPhoneType;

    private String email;

    private String zip;

    private String medicareIdNumber;

    private String medicaidIdNumber;

    private String gender;

    private Integer participantStatus;

    private String county;

    private LocalDate dateAuthorized;

    private String authorizationNumber;


    private Long contactStatusId;

    private String contactStatusName;

    private Long contactSubStatusId;

    private String contactSubStatusName;

    private Long mcoId;

    private String mcoName;

    private Long referralTypeId;

    private String referralTypeName;

    private Long referralSourceId;

    private String referralSourceName;

    private Long assignedToId;

    private String assignedToLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public String getPrimaryPhoneType() {
        return primaryPhoneType;
    }

    public void setPrimaryPhoneType(String primaryPhoneType) {
        this.primaryPhoneType = primaryPhoneType;
    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public void setSecondaryPhone(String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }

    public String getSecondaryPhoneType() {
        return secondaryPhoneType;
    }

    public void setSecondaryPhoneType(String secondaryPhoneType) {
        this.secondaryPhoneType = secondaryPhoneType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getMedicareIdNumber() {
        return medicareIdNumber;
    }

    public void setMedicareIdNumber(String medicareIdNumber) {
        this.medicareIdNumber = medicareIdNumber;
    }

    public String getMedicaidIdNumber() {
        return medicaidIdNumber;
    }

    public void setMedicaidIdNumber(String medicaidIdNumber) {
        this.medicaidIdNumber = medicaidIdNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getParticipantStatus() {
        return participantStatus;
    }

    public void setParticipantStatus(Integer participantStatus) {
        this.participantStatus = participantStatus;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public LocalDate getDateAuthorized() {
        return dateAuthorized;
    }

    public void setDateAuthorized(LocalDate dateAuthorized) {
        this.dateAuthorized = dateAuthorized;
    }

    public String getAuthorizationNumber() {
        return authorizationNumber;
    }

    public void setAuthorizationNumber(String authorizationNumber) {
        this.authorizationNumber = authorizationNumber;
    }

    public Long getContactStatusId() {
        return contactStatusId;
    }

    public void setContactStatusId(Long contactStatusId) {
        this.contactStatusId = contactStatusId;
    }

    public String getContactStatusName() {
        return contactStatusName;
    }

    public void setContactStatusName(String contactStatusName) {
        this.contactStatusName = contactStatusName;
    }

    public Long getContactSubStatusId() {
        return contactSubStatusId;
    }

    public void setContactSubStatusId(Long contactSubStatusId) {
        this.contactSubStatusId = contactSubStatusId;
    }

    public String getContactSubStatusName() {
        return contactSubStatusName;
    }

    public void setContactSubStatusName(String contactSubStatusName) {
        this.contactSubStatusName = contactSubStatusName;
    }

    public Long getMcoId() {
        return mcoId;
    }

    public void setMcoId(Long mCOId) {
        this.mcoId = mCOId;
    }

    public String getMcoName() {
        return mcoName;
    }

    public void setMcoName(String mCOName) {
        this.mcoName = mCOName;
    }

    public Long getReferralTypeId() {
        return referralTypeId;
    }

    public void setReferralTypeId(Long referralTypeId) {
        this.referralTypeId = referralTypeId;
    }

    public String getReferralTypeName() {
        return referralTypeName;
    }

    public void setReferralTypeName(String referralTypeName) {
        this.referralTypeName = referralTypeName;
    }

    public Long getReferralSourceId() {
        return referralSourceId;
    }

    public void setReferralSourceId(Long referralSourceId) {
        this.referralSourceId = referralSourceId;
    }

    public String getReferralSourceName() {
        return referralSourceName;
    }

    public void setReferralSourceName(String referralSourceName) {
        this.referralSourceName = referralSourceName;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long userId) {
        this.assignedToId = userId;
    }

    public String getAssignedToLogin() {
        return assignedToLogin;
    }

    public void setAssignedToLogin(String userLogin) {
        this.assignedToLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParticipantDTO participantDTO = (ParticipantDTO) o;
        if (participantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), participantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParticipantDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleInitial='" + getMiddleInitial() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", title='" + getTitle() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", dob='" + getDob() + "'" +
            ", primaryPhone='" + getPrimaryPhone() + "'" +
            ", primaryPhoneType='" + getPrimaryPhoneType() + "'" +
            ", secondaryPhone='" + getSecondaryPhone() + "'" +
            ", secondaryPhoneType='" + getSecondaryPhoneType() + "'" +
            ", email='" + getEmail() + "'" +
            ", zip='" + getZip() + "'" +
            ", medicareIdNumber='" + getMedicareIdNumber() + "'" +
            ", medicaidIdNumber='" + getMedicaidIdNumber() + "'" +
            ", gender='" + getGender() + "'" +
            ", participantStatus=" + getParticipantStatus() +
            ", county='" + getCounty() + "'" +
            ", dateAuthorized='" + getDateAuthorized() + "'" +
            ", authorizationNumber='" + getAuthorizationNumber() + "'" +
            ", contactStatus=" + getContactStatusId() +
            ", contactStatus='" + getContactStatusName() + "'" +
            ", contactSubStatus=" + getContactSubStatusId() +
            ", contactSubStatus='" + getContactSubStatusName() + "'" +
            ", mco=" + getMcoId() +
            ", mco='" + getMcoName() + "'" +
            ", referralType=" + getReferralTypeId() +
            ", referralType='" + getReferralTypeName() + "'" +
            ", referralSource=" + getReferralSourceId() +
            ", referralSource='" + getReferralSourceName() + "'" +
            ", assignedTo=" + getAssignedToId() +
            ", assignedTo='" + getAssignedToLogin() + "'" +
            "}";
    }
}
