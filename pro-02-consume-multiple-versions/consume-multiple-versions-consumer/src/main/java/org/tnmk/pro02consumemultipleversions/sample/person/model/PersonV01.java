package org.tnmk.pro02consumemultipleversions.sample.person.model;

public class PersonV01 {
    private String fullName;
    private String nickName;

    @Override
    public String toString() {
        return "PersonV01{" +
                "fullName='" + fullName + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
