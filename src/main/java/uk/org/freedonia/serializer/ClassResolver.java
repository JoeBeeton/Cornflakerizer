package uk.org.freedonia.serializer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class ClassResolver {


    public static Set<Class> getClasses(Class klass) {
        Set<Class> classSet = new HashSet<>();
        for(Field field : getFieldsUpTo(klass, Object.class)) {
            if(!Modifier.isStatic(field.getModifiers())) {
                Type type = field.getGenericType();
                classSet.add(field.getType());
                addTypesToSet(classSet, type);
            }
        }
        klass.getClasses();
        classSet.add(klass);
        return classSet;
    }

    private static void addTypesToSet(Set<Class> classSet, Type type) {
        if(type instanceof Class) {
            classSet.add((Class)type);
        }
        if(type instanceof ParameterizedType ) {
            addTypesToSet(classSet, ((ParameterizedType) type).getRawType());
            for(Type newType : getGenericTypes(type)) {
                addTypesToSet(classSet, newType);
            }
        }
    }

    private static List<Type> getGenericTypes(Type type) {
        if(type instanceof ParameterizedType) {
            return Arrays.asList( ((ParameterizedType)type).getActualTypeArguments());
        } else {
            return Collections.emptyList();
        }
    }

    private static Iterable<Field> getFieldsUpTo( Class<?> startClass,
                                                 Class<?> exclusiveParent) {
        List<Field> currentClassFields = new ArrayList<>(Arrays.asList(startClass.getDeclaredFields()));
        Class<?> parentClass = startClass.getSuperclass();

        if (parentClass != null &&
                (exclusiveParent == null || !(parentClass.equals(exclusiveParent)))) {
            List<Field> parentClassFields =
                    (List<Field>) getFieldsUpTo(parentClass, exclusiveParent);
            currentClassFields.addAll(parentClassFields);
        }
        return currentClassFields;
    }




}