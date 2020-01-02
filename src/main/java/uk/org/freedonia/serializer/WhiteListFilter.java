package uk.org.freedonia.serializer;

import java.io.ObjectInputFilter;

import java.io.Serializable;
import java.util.Arrays;

public class WhiteListFilter implements ObjectInputFilter {
    private static final  Class[] AUTOBOX_PRIMITIVES = new Class[]{
            Integer.class,
            String.class,
            Double.class,
            Short.class,
            Byte.class,
            Long.class,
            Float.class,
            Boolean.class,
            Character.class
    };

    private static final Class[] BLACKLISTED_CLASSES = new Class[] {
            Object.class,
            Serializable.class
    };


    private final Class[] whiteListClasses;

    public WhiteListFilter(Class[] whiteListClasses) {
        this.whiteListClasses = whiteListClasses;
    }

    @Override
    public Status checkInput(FilterInfo filterInfo) {
        Status status = Status.REJECTED;
        if(isClassBlackListed(filterInfo.serialClass())) {
            return status.REJECTED;
        }
        if(filterInfo.serialClass()== null
                ||filterInfo.serialClass().isPrimitive()
                || Arrays.stream(AUTOBOX_PRIMITIVES).anyMatch(cl -> cl.equals(filterInfo.serialClass()))
                || Arrays.stream(whiteListClasses).anyMatch(cl -> cl.equals(filterInfo.serialClass()))
                ||isArrayOfPrimitives(filterInfo)
                ||isAssignableFrom(filterInfo)) {
            status = Status.ALLOWED;
        }
        return status;
    }

    private boolean isArrayOfPrimitives(FilterInfo filterInfo) {
        boolean isPrimArray = false;
        if(filterInfo.serialClass().isArray()&& filterInfo.serialClass().getComponentType().isPrimitive()) {
            isPrimArray = true;
        }
        return isPrimArray;
    }

    private boolean isClassBlackListed(Class klass) {
        return Arrays.asList(BLACKLISTED_CLASSES).stream().anyMatch(cl -> cl.equals(klass));
    }

    private boolean isAssignableFrom(FilterInfo filterInfo) {
        Class klass = filterInfo.serialClass();
        return Arrays.stream(whiteListClasses).anyMatch(wlt-> klass.isAssignableFrom(wlt));
    }
}
