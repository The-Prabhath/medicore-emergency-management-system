package com.medicore.model;

/**
 * Triage priority level for a patient in the emergency queue.
 */
public enum Priority {
    CRITICAL("Critical", "#E24B4A"),
    URGENT("Urgent", "#EF9F27"),
    STANDARD("Standard", "#639922");

    private final String label;
    private final String colorHex;

    Priority(String label, String colorHex) {
        this.label = label;
        this.colorHex = colorHex;
    }

    public String getLabel() {
        return label;
    }

    public String getColorHex() {
        return colorHex;
    }
}
