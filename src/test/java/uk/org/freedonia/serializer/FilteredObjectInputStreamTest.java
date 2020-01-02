package uk.org.freedonia.serializer;

import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;

import static org.junit.Assert.*;

public class FilteredObjectInputStreamTest {

    @Test
    public void testWithInvalidClass() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(new BigDecimal(123));
        os.flush();
        byte[] data = bos.toByteArray();
        ObjectInputStream ois = new FilteredObjectInputStream(new ByteArrayInputStream(data),SamplePojo.class);
        try {
            Object object = ois.readObject();
            fail("object serialization allowed");
        } catch (InvalidClassException ice) {
        }
    }

    @Test
    public void testWithValidClassFoundInClass() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(new BigDecimal(123));
        os.flush();
        byte[] data = bos.toByteArray();
        ObjectInputStream ois = new FilteredObjectInputStream(new ByteArrayInputStream(data),AnotherSamplePojo.class);
        try {
            Object object = ois.readObject();
            assertEquals(object,new BigDecimal(123));
        } catch (InvalidClassException ice) {

        }
    }

    @Test
    public void testWithValidClass() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(new BigDecimal(123));
        os.flush();
        byte[] data = bos.toByteArray();
        ObjectInputStream ois = new FilteredObjectInputStream(new ByteArrayInputStream(data),BigDecimal.class);
        Object object = ois.readObject();
        assertEquals(object,new BigDecimal(123));
    }

    @Test
    public void testWithInvalidSerializableBaseInterface() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(new BigDecimal(123));
        os.flush();
        byte[] data = bos.toByteArray();
        ObjectInputStream ois = new FilteredObjectInputStream(new ByteArrayInputStream(data),Serializable.class);
        try {
            Object object = ois.readObject();
            fail("The filter should have thrown an exception");
        } catch (InvalidClassException ice) {

        }
    }

    @Test
    public void testFilterDoesNotFilterStreamsItIsNotAttachedTo() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(new BigDecimal(123));
        os.flush();
        byte[] data = bos.toByteArray();
        ObjectInputStream ois = new FilteredObjectInputStream(new ByteArrayInputStream(data),Serializable.class);
        ObjectInputStream unFilteredStream = new ObjectInputStream(new ByteArrayInputStream(data));

        try {
            BigDecimal bigDecimal = (BigDecimal) unFilteredStream.readObject();
            assertEquals(new BigDecimal(123),bigDecimal);
            Object object = ois.readObject();
            fail("The filter should have thrown an exception");
        } catch (InvalidClassException ice) {
        }
    }

}