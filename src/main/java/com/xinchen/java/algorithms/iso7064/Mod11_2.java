package com.xinchen.java.algorithms.iso7064;

/**
 * 身份证最后一位校验算法(ISO 7064:1983.MOD 11-2)
 *
 * see: https://github.com/danieltwagner/iso7064
 *
 * @author xin chen
 * @version 1.0.0
 * @date 2021/1/7 15:06
 */
public class Mod11_2 extends PureSystemCalculator {

    @Override
    protected int getModulus() {
        return 11;
    }

    @Override
    protected int getRadix() {
        return 2;
    }

    @Override
    protected String getCharacterSet() {
        return "0123456789X";
    }

    @Override
    protected boolean isDoubleCheckDigit() {
        return false;
    }

}