package minicraft.view;

public enum BiomeType {
    GRASS("grass.png", true),
    SAND("sand.png", true),
    WATER("water.png", true),
    STONE("stone.png", false);

    private String pictureFile;
    private boolean walkable;

    private BiomeType(String pictureFile, boolean isWalkable) {
        this.pictureFile = pictureFile;
        this.walkable = isWalkable;
    }

    public String getPictureFile() {
        return pictureFile;
    }

    public boolean isWalkable() {
        return walkable;
    }
}
