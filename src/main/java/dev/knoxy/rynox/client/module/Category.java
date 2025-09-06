package dev.knoxy.rynox.client.module;

public enum Category {
    COMBAT("Combat"),
    MISC("Misc"),
    RENDER("Render"),
    PLAYER("Player"),
    EXPLOIT("Exploit");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
