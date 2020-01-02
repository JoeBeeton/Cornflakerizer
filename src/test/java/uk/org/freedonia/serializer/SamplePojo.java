package uk.org.freedonia.serializer;

import java.io.Serializable;

public class SamplePojo  implements Serializable {

    private String value1;
    private int value2;

    public SamplePojo(String value1, int value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }
}
