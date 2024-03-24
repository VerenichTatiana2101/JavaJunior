package org.example;

import java.io.*;

public class MyFCsPrivateFields implements Externalizable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static String lName;
    private String fName;
    private String patronymic;
    private transient Dob dob;  //это поле не будет сериализоваться

    public MyFCsPrivateFields() {
    }

    public MyFCsPrivateFields(String fName, String lName, String patronymic) {
        this.fName = fName;
        this.lName = lName;
        this.patronymic = patronymic;
        this.dob = new Dob(15, 12, 2023);
    }

    @Override
    public String toString() {
        return String.format("%s %s.%s. %s",
                fName, lName.toUpperCase().charAt(0),
                patronymic.toUpperCase().charAt(0),
                dob.day + "/" + dob.month + "/" + dob.year);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.dob);
        out.writeObject(this.fName);
        out.writeObject(this.patronymic);
        String tmp = lName;
        out.writeObject(tmp);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.dob = (Dob) in.readObject();
        fName = (String) in.readObject();
        this.patronymic = (String) in.readObject();
        lName = (String) in.readObject();
    }
}

class Dob implements Serializable {
    int day;
    int month;
    int year;

    public Dob(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
}
