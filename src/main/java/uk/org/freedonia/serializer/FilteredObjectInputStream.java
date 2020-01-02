package uk.org.freedonia.serializer;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class FilteredObjectInputStream extends ObjectInputStream {

    private Set<Class> allowedClasses;

    public FilteredObjectInputStream(InputStream in, Class ...expectedClasses) throws IOException {
        super(in);
         allowedClasses =  Arrays
                .stream(expectedClasses)
                .map(SerializableResolver::getSerializableClasses)
                 .flatMap(Collection::stream)
                 .collect(Collectors.toSet());
         setObjectInputFilter(new WhiteListFilter(allowedClasses.toArray(new Class[]{} ) ) );
    }


}
