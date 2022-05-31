package ru.aizen.d2.editor;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class StatusTest {

    @Test
    void successfullyGetStatusFromByteValue() {
        byte value = 0b00100100;
        boolean isExpansion = true;
        boolean isDead = false;
        boolean isHardcode = true;
        Status expectedStatus = new Status(isExpansion, isDead, isHardcode);


        Status status = Status.from(value);


        assertThat(status, equalTo(expectedStatus));
    }

    @Test
    void successfullyGetByteValueFromStatus() {
        boolean isExpansion = true;
        boolean isDead = true;
        boolean isHardcode = true;
        Status status = new Status(isExpansion, isDead, isHardcode);
        byte expectedValue = 0b00101100;


        byte value = status.byteValue();


        assertThat(value, equalTo(expectedValue));
    }

}