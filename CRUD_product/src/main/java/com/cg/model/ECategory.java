package com.cg.model;

public enum ECategory {
    LAPTOP (1,"LAPTOP"), PHONE (2,"PHONE"), DESKTOP (3,"DESKTOP");
    private int id;
   private String name;

    ECategory(int id, String name) {
        this.id = id;
        this.name = name;
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
    public static ECategory toCategory(Long id) {
        for (ECategory category : values()) {
            if (category.id == id) {
                return category;
            }
        }
        return null;
    }


}
