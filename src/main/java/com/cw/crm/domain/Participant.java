package com.cw.crm.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Participant.
 */
@Entity
@Table(name = "participant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Participant implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_initial")
    private String middleInitial;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "title")
    private String title;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "primary_phone")
    private String primaryPhone;

    @Column(name = "primary_phone_type")
    private String primaryPhoneType;

    @Column(name = "secondary_phone")
    private String secondaryPhone;

    @Column(name = "secondary_phone_type")
    private String secondaryPhoneType;

    @Column(name = "email")
    private String email;

    @Column(name = "zip")
    private String zip;

    @Column(name = "medicare_id_number")
    private String medicareIdNumber;

    @Column(name = "medicaid_id_number")
    private String medicaidIdNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "participant_status")
    private Integer participantStatus;

    @Column(name = "county")
    private String county;

    @Column(name = "date_authorized")
    private LocalDate dateAuthorized;

    @Column(name = "authorization_number")
    private String authorizationNumber;

    @ManyToOne
    @JsonIgnoreProperties("participants")
    private ContactStatus contactStatus;

    @ManyToOne
    @JsonIgnoreProperties("participants")
    private ContactSubStatus contactSubStatus;

    @ManyToOne
    @JsonIgnoreProperties("participants")
    private MCO mco;

    @ManyToOne
    @JsonIgnoreProperties("participants")
    private ReferralType referralType;

    @ManyToOne
    @JsonIgnoreProperties("participants")
    private ReferralSource referralSource;

    @ManyToOne
    @JsonIgnoreProperties("participants")
    private User assignedTo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Participant firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public Participant middleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
        return this;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public Participant lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public Participant title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public Participant registrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getAddress() {
        return address;
    }

    public Participant address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Participant city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Participant state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Participant dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public Participant primaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
        return this;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public String getPrimaryPhoneType() {
        return primaryPhoneType;
    }

    public Participant primaryPhoneType(String primaryPhoneType) {
        this.primaryPhoneType = primaryPhoneType;
        return this;
    }

    public void setPrimaryPhoneType(String primaryPhoneType) {
        this.primaryPhoneType = primaryPhoneType;
    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public Participant secondaryPhone(String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
        return this;
    }

    public void setSecondaryPhone(String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }

    public String getSecondaryPhoneType() {
        return secondaryPhoneType;
    }

    public Participant secondaryPhoneType(String secondaryPhoneType) {
        this.secondaryPhoneType = secondaryPhoneType;
        return this;
    }

    public void setSecondaryPhoneType(String secondaryPhoneType) {
        this.secondaryPhoneType = secondaryPhoneType;
    }

    public String getEmail() {
        return email;
    }

    public Participant email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZip() {
        return zip;
    }

    public Participant zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getMedicareIdNumber() {
        return medicareIdNumber;
    }

    public Participant medicareIdNumber(String medicareIdNumber) {
        this.medicareIdNumber = medicareIdNumber;
        return this;
    }

    public void setMedicareIdNumber(String medicareIdNumber) {
        this.medicareIdNumber = medicareIdNumber;
    }

    public String getMedicaidIdNumber() {
        return medicaidIdNumber;
    }

    public Participant medicaidIdNumber(String medicaidIdNumber) {
        this.medicaidIdNumber = medicaidIdNumber;
        return this;
    }

    public void setMedicaidIdNumber(String medicaidIdNumber) {
        this.medicaidIdNumber = medicaidIdNumber;
    }

    public String getGender() {
        return gender;
    }

    public Participant gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getParticipantStatus() {
        return participantStatus;
    }

    public Participant participantStatus(Integer participantStatus) {
        this.participantStatus = participantStatus;
        return this;
    }

    public void setParticipantStatus(Integer participantStatus) {
        this.participantStatus = participantStatus;
    }

    public String getCounty() {
        return county;
    }

    public Participant county(String county) {
        this.county = county;
        return this;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public LocalDate getDateAuthorized() {
        return dateAuthorized;
    }

    public Participant dateAuthorized(LocalDate dateAuthorized) {
        this.dateAuthorized = dateAuthorized;
        return this;
    }

    public void setDateAuthorized(LocalDate dateAuthorized) {
        this.dateAuthorized = dateAuthorized;
    }

    public String getAuthorizationNumber() {
        return authorizationNumber;
    }

    public Participant authorizationNumber(String authorizationNumber) {
        this.authorizationNumber = authorizationNumber;
        return this;
    }

    public void setAuthorizationNumber(String authorizationNumber) {
        this.authorizationNumber = authorizationNumber;
    }

    public ContactStatus getContactStatus() {
        return contactStatus;
    }

    public Participant contactStatus(ContactStatus contactStatus) {
        this.contactStatus = contactStatus;
        return this;
    }

    public void setContactStatus(ContactStatus contactStatus) {
        this.contactStatus = contactStatus;
    }

    public ContactSubStatus getContactSubStatus() {
        return contactSubStatus;
    }

    public Participant contactSubStatus(ContactSubStatus contactSubStatus) {
        this.contactSubStatus = contactSubStatus;
        return this;
    }

    public void setContactSubStatus(ContactSubStatus contactSubStatus) {
        this.contactSubStatus = contactSubStatus;
    }

    public MCO getMco() {
        return mco;
    }

    public Participant mco(MCO mCO) {
        this.mco = mCO;
        return this;
    }

    public void setMco(MCO mCO) {
        this.mco = mCO;
    }

    public ReferralType getReferralType() {
        return referralType;
    }

    public Participant referralType(ReferralType referralType) {
        this.referralType = referralType;
        return this;
    }

    public void setReferralType(ReferralType referralType) {
        this.referralType = referralType;
    }

    public ReferralSource getReferralSource() {
        return referralSource;
    }

    public Participant referralSource(ReferralSource referralSource) {
        this.referralSource = referralSource;
        return this;
    }

    public void setReferralSource(ReferralSource referralSource) {
        this.referralSource = referralSource;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public Participant assignedTo(User user) {
        this.assignedTo = user;
        return this;
    }

    public void setAssignedTo(User user) {
        this.assignedTo = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Participant participant = (Participant) o;
        if (participant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), participant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Participant{" +
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
            "}";
    }
}
