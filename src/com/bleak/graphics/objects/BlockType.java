package com.bleak.graphics.objects;

public enum BlockType {
    Concrete(0, "Concrete"),
    Brick(1, "Brick"),
    Grass(2, "Grass"),
    Water(3, "Water");

    private final int id;
    private final String name;

    BlockType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
