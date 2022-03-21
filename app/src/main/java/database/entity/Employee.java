package database.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Employee {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String Name;
    @ColumnInfo(name = "fistname")
    public String FirstName;
    @ColumnInfo(name = "function")
    public String Function;
    @ColumnInfo(name = "telnumber")
    public String Telnumber;
    @ColumnInfo(name = "email")
    public String Email;
    @ColumnInfo(name = "address")
    public String Address;
    @ColumnInfo(name = "npa")
    public String Npa;
    @ColumnInfo(name = "image_url")
    public String Image_Url;
    @ColumnInfo(name = "username")
    public String Username;
    @ColumnInfo(name = "password")
    public String Password;
    @ColumnInfo(name = "isAdmin")
    public Boolean isAdmin;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getFunction() {
        return Function;
    }

    public void setFunction(String function) {
        Function = function;
    }

    public String getTelnumber() {
        return Telnumber;
    }

    public void setTelnumber(String telnumber) {
        Telnumber = telnumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getNpa() {
        return Npa;
    }

    public void setNpa(String npa) {
        Npa = npa;
    }

    public String getImage_Url() {
        return Image_Url;
    }

    public void setImage_Url(String image_Url) {
        Image_Url = image_Url;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
