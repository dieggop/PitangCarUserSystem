package com.desafio.carusersystem.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Cars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String year;
    private String licensePlate;
    private String model;
    private String color;

    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Cars{" +
                "id=" + id +
                ", year='" + year + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", usuario=" + usuario +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cars cars = (Cars) o;
        return Objects.equals(id, cars.id) &&
                Objects.equals(year, cars.year) &&
                Objects.equals(licensePlate, cars.licensePlate) &&
                Objects.equals(model, cars.model) &&
                Objects.equals(color, cars.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, year, licensePlate, model, color);
    }
}
