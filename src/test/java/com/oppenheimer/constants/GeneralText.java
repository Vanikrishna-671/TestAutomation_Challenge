package com.oppenheimer.constants;

public enum GeneralText {

    HOME_PAGE_HEADING("The Oppenheimer Project"),
    DISPENSE_NOW_BUTTON("Dispense Now"),
    DISPENSE_BUTTON_COLOR("Red"),
    DISPENSE_PAGE_TITLE("Dispense!!"),
    DISPENSE_PAGE_TEXT("Cash dispensed"),
    NATID_TABLE_HEADING("NatId"),
    RELIEF_TABLE_HEADING("Relief");

    private final String desc;

    GeneralText(String desc) {
        this.desc = desc;

    }

    @Override
    public String toString() {
        return desc;
    }


}
