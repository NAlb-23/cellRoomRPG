package resources;

public enum RoomType {
    CELL_1("Cell 1"),
    CELL_2("Cell 2"),
    CELL_3("Cell 3"),
    GUARD_ROOM("Guard Room"),
    HALLWAY("Hallway");

    private final String roomName;

    RoomType(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }
}
