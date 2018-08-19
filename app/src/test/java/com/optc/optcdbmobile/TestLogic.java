package com.optc.optcdbmobile;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestLogic {
    @Test
    public void testGetMethod() throws NoSuchMethodException {

        for (Method method : TestClass.class.getMethods()) {
            if (method.getName().equals("showString")) {
                try {
                    method.invoke(new TestClass(), "This is printed withing forloop");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else if (method.getName().equals("returnString")) {
                try {
                    String s = (String) method.invoke(new TestClass(), new Object[0]);
                    System.out.println(s);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    class TestClass {
        public void showString(String s) {
            System.out.println(s);
        }

        public String returnString() {
            final String internal_string = "Returned from TestClass";
            return internal_string;
        }
    }
}