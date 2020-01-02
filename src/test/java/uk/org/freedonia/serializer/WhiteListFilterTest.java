package uk.org.freedonia.serializer;

import org.junit.Test;
import java.io.ObjectInputFilter;


import java.util.List;

import static org.junit.Assert.*;

public class WhiteListFilterTest {

    @Test
    public void testWithInvalidClass() {
        Class[] whiteList = new Class[]{String.class};
        WhiteListFilter filter = new WhiteListFilter(whiteList);
        ObjectInputFilter.FilterInfo info = getFilterInfo(List.class);
        assertEquals(ObjectInputFilter.Status.REJECTED,filter.checkInput(info));
    }

    @Test
    public void testWithValidClass() {
        Class[] whiteList = new Class[]{List.class};
        WhiteListFilter filter = new WhiteListFilter(whiteList);
        ObjectInputFilter.FilterInfo info = getFilterInfo(List.class);
        assertEquals(ObjectInputFilter.Status.ALLOWED,filter.checkInput(info));
    }

    @Test
    public void testWithPrimitiveClass() {
        Class[] whiteList = new Class[]{List.class};
        WhiteListFilter filter = new WhiteListFilter(whiteList);
        ObjectInputFilter.FilterInfo info = getFilterInfo(Integer.class);
        assertEquals(ObjectInputFilter.Status.ALLOWED,filter.checkInput(info));
    }

    @Test
    public void testWithBasicTypes() {
        Class[] whiteList = new Class[]{Object.class};
        WhiteListFilter filter = new WhiteListFilter(whiteList);
        ObjectInputFilter.FilterInfo info = getFilterInfo(Object.class);
        assertEquals(ObjectInputFilter.Status.REJECTED,filter.checkInput(info));
    }



    private ObjectInputFilter.FilterInfo getFilterInfo(Class matchingClass) {
        return new ObjectInputFilter.FilterInfo() {
            @Override
            public Class<?> serialClass() {
                return matchingClass;
            }

            @Override
            public long arrayLength() {
                return 0;
            }

            @Override
            public long depth() {
                return 0;
            }

            @Override
            public long references() {
                return 0;
            }

            @Override
            public long streamBytes() {
                return 0;
            }
        };
    }

}