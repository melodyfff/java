package com.xinchen.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;


/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2020/6/26 23:13
 */
class UnSafeUtilTest {
    @Test
    void testGetUnsafe(){
        final Unsafe unsafe = UnSafeUtil.getUnsafe();
        Assertions.assertNotNull(unsafe);
    }
}