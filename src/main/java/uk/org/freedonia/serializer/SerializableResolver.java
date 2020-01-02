package uk.org.freedonia.serializer;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

public class SerializableResolver {

    public static Set<Class> getSerializableClasses(Class klass) {
        Set<Class> allClasses = ClassResolver.getClasses(klass);
        return allClasses.stream().filter(SerializableResolver::isClassValid).collect(Collectors.toSet());
    }

    private static boolean isClassValid(Class klass) {
        if(Serializable.class.isAssignableFrom(klass)) {
            return true;
        } else if(klass.isPrimitive()) {
            return true;
        } else {
            return false;
        }
    }


}
