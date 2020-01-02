package uk.org.freedonia.serializer;

import java.io.Serializable;
import java.math.BigDecimal;

public class AnotherSamplePojo implements Serializable {

    private BigDecimal bidDecimal;
    private String bla;
    private SamplePojo samplePojo;

    public AnotherSamplePojo(BigDecimal bidDecimal, String bla, SamplePojo samplePojo) {
        this.bidDecimal = bidDecimal;
        this.bla = bla;
        this.samplePojo = samplePojo;
    }

    public BigDecimal getBidDecimal() {
        return bidDecimal;
    }

    public void setBidDecimal(BigDecimal bidDecimal) {
        this.bidDecimal = bidDecimal;
    }

    public String getBla() {
        return bla;
    }

    public void setBla(String bla) {
        this.bla = bla;
    }

    public SamplePojo getSamplePojo() {
        return samplePojo;
    }

    public void setSamplePojo(SamplePojo samplePojo) {
        this.samplePojo = samplePojo;
    }
}
