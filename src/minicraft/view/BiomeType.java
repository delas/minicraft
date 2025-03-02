package minicraft.view;

public enum BiomeType {
    GRASS("grass.png", true),
    WATER("water.png", false);

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
