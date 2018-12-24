package com.testpro.easyrest.Util;

import org.testng.Assert;

public class Verify {
    private static final ThreadLocal<StringBuffer> threadLocal = new ThreadLocal<>();
    public static StringBuffer verificationErrors = new StringBuffer();
    ;

    public static void verifyTrue(boolean o) {
        try {
            Assert.assertTrue(o);
        } catch (Error e) {
            verificationErrors.append(e.toString());
            setStrbuffer(verificationErrors);
        }
    }

    private static void setStrbuffer(StringBuffer verificationErrors) {
        if (threadLocal.get() != null) {
            StringBuffer stringBuffer = threadLocal.get();
            stringBuffer.append(verificationErrors.toString());
            threadLocal.set(stringBuffer);
        } else {
            threadLocal.set(verificationErrors);
        }
    }

    public static void verifyFalse(boolean o) {
        try {
            Assert.assertFalse(o);
        } catch (Error e) {
            verificationErrors.append(e.toString());
            setStrbuffer(verificationErrors);
        }
    }

    public static void verifyEquals(Object expected, Object actuals) {
        try {
            Assert.assertEquals(expected, actuals);
        } catch (Error e) {
            verificationErrors.append(e.toString());
            setStrbuffer(verificationErrors);
        }
    }

    public static void verifyEquals(Object expected, Object actuals,
                                    String message) {
        try {
            Assert.assertEquals(expected, actuals, message);
        } catch (Error e) {
            verificationErrors.append(e.toString());
            setStrbuffer(verificationErrors);
        }
    }

    public static void assertEquals(String actual, String expected,
                                    String message) {
        try {
            Assert.assertEquals(actual, expected, message);
        } catch (Error e) {
            verificationErrors.append(e.toString());
            setStrbuffer(verificationErrors);
        }
    }

    public static void assertEquals(String actual, String expected) {
        try {
            Assert.assertEquals(actual, expected);
        } catch (Error e) {
            verificationErrors.append(e.toString());
            setStrbuffer(verificationErrors);
        }
    }

    public static void tearDown() {
//        异常信息
        String verificationErrorString = null;
//        如果没有异常信息就不处理
        if (threadLocal.get() != null) {
            verificationErrorString = threadLocal.get().toString();
        }
        if (!"".equals(verificationErrorString) && verificationErrorString != null) {
            verificationErrors.setLength(0);
            threadLocal.remove();
            Assert.fail(verificationErrorString);
        }

    }

    public static void assertTrue(boolean equals) {
        try {
            Assert.assertTrue(equals);
        } catch (Error e) {
            verificationErrors.append(e.toString());
            setStrbuffer(verificationErrors);
        }
    }
}
