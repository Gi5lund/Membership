import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class PersonalInformation implements Serializable {
    @Serial
    private static final long serialVersionUID= 2393294789740398589L;
    private String firstname = "Firstname";
    private String middlenames = "Middlename(s) or empty";
    private String surname = "Surname";
    private boolean genderIsMale;
    private LocalDate birthdate = LocalDate.now();
    private String email = "something@somewhere.end";

    public PersonalInformation() {
        genderIsMale = false;
    }

    public PersonalInformation(String fn, String mn, String sn, boolean gender,String bday) {
        this.firstname = fn;
        this.middlenames = mn;
        this.surname = sn;
        this.genderIsMale=gender;
        this.birthdate = LocalDate.parse(bday);
    }
    public PersonalInformation(String fn, String mn, String sn, boolean gender,LocalDate bday) {
        this.firstname = fn;
        this.middlenames = mn;
        this.surname = sn;
        this.genderIsMale=gender;
        this.birthdate = bday;
    }

    /**
     * @param birthday
     */
    public static int getAge(LocalDate birthday) {

        LocalDate today = LocalDate.now();
        //LocalDate birthday = LocalDate.of(birthday.getYear(), birthday.getMonth(), birthday.getDayOfMonth());
        Period period = Period.between(birthday, today);
        return period.getYears();
    }

    public String toString() {
        String fn = firstname;
        String mn = middlenames;
        String sn = surname;
        String gender="";
        if (fn.contains(" ")) {
            fn.replace(" ", "_");
        }
        if(mn.contains(" ")){
            mn.replace(" ","_");
        }
        if (genderIsMale){
            gender="Male";
        }else{
            gender="Female";
        }
        return fn+" "+mn+" "+sn+" "+gender+" "+getAge(getBirthdate())+" "+getEmail();

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlenames() {
        return middlenames;
    }

    public void setMiddlenames(String middlenames) {
        this.middlenames = middlenames;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setGenderIsMale(boolean gender){
        this.genderIsMale=gender;
    }

    public boolean isMale() {
        return genderIsMale;
    }
}