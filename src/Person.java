import java.util.Scanner;

public class Person{

    private String name;
    private String surname;
    private String email;

    public Person (String name, String surname, String email){
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSurname(){
        return surname;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void printInfo(){

        System.out.println("\tFull Name:"+ name);
        System.out.println("\tSurname:"+ surname);
        System.out.println("\tEmail:"+ email);
    }
}

   