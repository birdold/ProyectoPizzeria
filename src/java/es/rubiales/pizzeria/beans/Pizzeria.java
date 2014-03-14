package es.rubiales.pizzeria.beans;

/*
 * Copyright (C) 2014 Javier García Escobedo (javiergarbedo.es)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */



import java.util.Date;

/**
 * 
 * @author Javier García Escobedo (javiergarbedo.es)
 * @version 0.0.1
 * @date 2014-02-24
 */

public class Pizzeria {
    private int id;
    private String name = "";
    private String price = "";
    private String comments = "";
    private String photoFileName = "";

    public Pizzeria() {
    }

    public Pizzeria(int id, String name, String price, String comments, String photoFileName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.comments = comments;
        this.photoFileName = photoFileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    @Override
    public String toString() {
        return name;
    }
}

