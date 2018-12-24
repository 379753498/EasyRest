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
            verificationErrors.setLength(0);
        }
    }

    /**
     *   当遇到异常信息把异常信息存起来
     * @param verificationErrors 异常信息
     */
    private static void setStrbuffer(StringBuffer verificationErrors) {
        String sb1 = verificationErrors.toString();
        if (threadLocal.get() != null) {
            StringBuffer stringBuffer = threadLocal.get();
            strbufferprint(sb1, stringBuffer);
            threadLocal.set(stringBuffer);
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            strbufferprint(sb1, stringBuffer);
            threadLocal.set(stringBuffer);
        }
    }

    /**
     * 美化一下异常信息的展示 更可读
     *
     * @param sb1
     * @param stringBuffer
     */
    private static void strbufferprint(String sb1, StringBuffer stringBuffer) {

        stringBuffer.append("\n");
        stringBuffer.append("{-----");
        stringBuffer.append(sb1);
        stringBuffer.append("-----}");
    }

    public static void verifyFalse(boolean o) {
        try {
            Assert.assertFalse(o);
        } catch (Error e) {
            verificationErrors.append(e.toString());
            setStrbuffer(verificationErrors);
            verificationErrors.setLength(0);
        }
    }

    public static void verifyEquals(Object expected, Object actuals) {
        try {
            Assert.assertEquals(expected, actuals);
        } catch (Error e) {
            verificationErrors.append(e.toString());
            setStrbuffer(verificationErrors);
            verificationErrors.setLength(0);
        }
    }

    public static void verifyEquals(Object expected, Object actuals,
                                    String message) {
        try {
            Assert.assertEquals(expected, actuals, message);
        } catch (Error e) {
            verificationErrors.append(e.toString());
            setStrbuffer(verificationErrors);
            verificationErrors.setLength(0);
        }
    }

    public static void assertEquals(String actual, String expected,
                                    String message) {
        try {
            Assert.assertEquals(actual, expected, message);
        } catch (Error e) {
            verificationErrors.append(e.toString());
            setStrbuffer(verificationErrors);
            verificationErrors.setLength(0);
        }
    }

    public static void assertEquals(String actual, String expected) {
        try {
            Assert.assertEquals(actual, expected);
        } catch (Error e) {
            verificationErrors.append(e.toString());
            setStrbuffer(verificationErrors);
            verificationErrors.setLength(0);
        }
    }

    /**
     * 用例结束之前调用如果有异常就阻断 没有异常就pass
     */
    public static void tearDown() {
//        异常信息
        String verificationErrorString = null;
//        如果没有异常信息就不处理
        if (threadLocal.get() != null) {
            verificationErrorString = threadLocal.get().toString();
        }
        if (!"".equals(verificationErrorString) && verificationErrorString != null) {
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
            verificationErrors.setLength(0);
        }
    }
}
