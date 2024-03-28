package org.example;


import javax.persistence.*;

@Entity
@Table(name = "java.magic")
public class Magic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "название")
    private String name;
    @Column(name = "повреждение")
    private int damage;
    @Column(name = "атака")
    private int attBonus;

    @Column(name = "броня")
    private int bron;

    public Magic(String name, int damage, int attBonus) {
        this.name = name;
        this.damage = damage;
        this.attBonus = attBonus;
    }

    public Magic(String name, int damage, int attBonus, int bron) {
        this.name = name;
        this.damage = damage;
        this.attBonus = attBonus;
        this.bron = bron;
    }

    public Magic() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setAttBonus(int attBonus) {
        this.attBonus = attBonus;
    }

    public void setBron(int bron) {
        this.bron = bron;
    }

    @Override
    public String toString() {
        return "Magic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", damage=" + damage +
                ", attBonus=" + attBonus +
                ", bron=" + bron +
                '}';
    }
}
