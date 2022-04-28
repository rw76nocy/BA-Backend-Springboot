package de.phoenix.wgtest.payload.request;

import de.phoenix.wgtest.model.management.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class CreateChildRequest {

    private LivingGroup livingGroup;

    @NotBlank
    private String gender;

    private String image;

    @NotBlank
    @Size( max = 100)
    private String firstName;

    @NotBlank
    @Size( max = 100)
    private String lastName;

    @NotBlank
    private Date birthday;

    @NotBlank
    private Date entranceDate;

    private Date releaseDate;

    @NotBlank
    @Size( max = 1000)
    private String reason;

    @Size( max = 1000)
    private String care;

    @Size( max = 1000)
    private String visit;

    @Size( max = 1000)
    private String diseases;

    private Person superVisor1;

    private Person superVisor2;

    private Person guardian;

    private Asd asd;

    private Person mother;

    private Person father;

    private Person childdoctor;

    private DayCareRequest dayCare;

    private HealthInsuranceRequest healthInsurance;

    private FoodSupplierRequest foodSupplier;

    private Institution driver;

    private List<SpecifiedPersonRequest> referencePersons;

    private List<SpecifiedPersonRequest> doctors;

    private List<SpecifiedPersonRequest> therapists;

    private List<SpecifiedPersonRequest> partners;

    public CreateChildRequest() {
    }

    public CreateChildRequest(LivingGroup livingGroup, String gender, String image, String firstName, String lastName,
                              Date birthday, Date entranceDate, Date releaseDate, String reason, String care,
                              String visit, String diseases, Person superVisor1, Person superVisor2, Person guardian,
                              Asd asd, Person mother, Person father, Person childdoctor, DayCareRequest dayCare,
                              HealthInsuranceRequest healthInsurance, FoodSupplierRequest foodSupplier, Institution driver,
                              List<SpecifiedPersonRequest> referencePersons, List<SpecifiedPersonRequest> doctors,
                              List<SpecifiedPersonRequest> therapists, List<SpecifiedPersonRequest> partners) {
        this.livingGroup = livingGroup;
        this.gender = gender;
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.entranceDate = entranceDate;
        this.releaseDate = releaseDate;
        this.reason = reason;
        this.care = care;
        this.visit = visit;
        this.diseases = diseases;
        this.superVisor1 = superVisor1;
        this.superVisor2 = superVisor2;
        this.guardian = guardian;
        this.asd = asd;
        this.mother = mother;
        this.father = father;
        this.childdoctor = childdoctor;
        this.dayCare = dayCare;
        this.healthInsurance = healthInsurance;
        this.foodSupplier = foodSupplier;
        this.driver = driver;
        this.referencePersons = referencePersons;
        this.doctors = doctors;
        this.therapists = therapists;
        this.partners = partners;
    }

    public LivingGroup getLivingGroup() {
        return livingGroup;
    }

    public void setLivingGroup(LivingGroup livingGroup) {
        this.livingGroup = livingGroup;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getEntranceDate() {
        return entranceDate;
    }

    public void setEntranceDate(Date entranceDate) {
        this.entranceDate = entranceDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCare() {
        return care;
    }

    public void setCare(String care) {
        this.care = care;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public Person getSuperVisor1() {
        return superVisor1;
    }

    public void setSuperVisor1(Person superVisor1) {
        this.superVisor1 = superVisor1;
    }

    public Person getSuperVisor2() {
        return superVisor2;
    }

    public void setSuperVisor2(Person superVisor2) {
        this.superVisor2 = superVisor2;
    }

    public Person getGuardian() {
        return guardian;
    }

    public void setGuardian(Person guardian) {
        this.guardian = guardian;
    }

    public Asd getAsd() {
        return asd;
    }

    public void setAsd(Asd asd) {
        this.asd = asd;
    }

    public Person getMother() {
        return mother;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    public Person getFather() {
        return father;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public Person getChilddoctor() {
        return childdoctor;
    }

    public void setChilddoctor(Person childdoctor) {
        this.childdoctor = childdoctor;
    }

    public DayCareRequest getDayCare() {
        return dayCare;
    }

    public void setDayCare(DayCareRequest dayCare) {
        this.dayCare = dayCare;
    }

    public HealthInsuranceRequest getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(HealthInsuranceRequest healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public FoodSupplierRequest getFoodSupplier() {
        return foodSupplier;
    }

    public void setFoodSupplier(FoodSupplierRequest foodSupplier) {
        this.foodSupplier = foodSupplier;
    }

    public Institution getDriver() {
        return driver;
    }

    public void setDriver(Institution driver) {
        this.driver = driver;
    }

    public List<SpecifiedPersonRequest> getReferencePersons() {
        return referencePersons;
    }

    public void setReferencePersons(List<SpecifiedPersonRequest> referencePersons) {
        this.referencePersons = referencePersons;
    }

    public List<SpecifiedPersonRequest> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<SpecifiedPersonRequest> doctors) {
        this.doctors = doctors;
    }

    public List<SpecifiedPersonRequest> getTherapists() {
        return therapists;
    }

    public void setTherapists(List<SpecifiedPersonRequest> therapists) {
        this.therapists = therapists;
    }

    public List<SpecifiedPersonRequest> getPartners() {
        return partners;
    }

    public void setPartners(List<SpecifiedPersonRequest> partners) {
        this.partners = partners;
    }
}
