package coms309.people;


/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 */

//public class Person {
//
//    private String firstName;
//
//    private String lastName;
//
//    private String address;
//
//    private String telephone;
//
//    public Person(){
//
//    }
//
//    public Person(String firstName, String lastName, String address, String telephone){
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.address = address;
//        this.telephone = telephone;
//    }
//
//    public String getFirstName() {
//        return this.firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return this.lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getAddress() {
//        return this.address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getTelephone() {
//        return this.telephone;
//    }
//
//    public void setTelephone(String telephone) {
//        this.telephone = telephone;
//    }
//
//    @Override
//    public String toString() {
//        return firstName + " "
//               + lastName + " "
//               + address + " "
//               + telephone;
//    }
public class Meat {

    private String name;

    private String price;

    private String quantity;

    public Meat(){

    }

    public Meat(String name, String price, String quantity){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getNameOfCut() {
        return this.name;
    }

    public void setNameOfCut(String name) {
        this.name = name;
    }

    public String getPricePerPound() {
        return this.price;
    }

    public void setPricePerPound(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return name + " "
                + price + " "
                + quantity + " ";
    }
}
