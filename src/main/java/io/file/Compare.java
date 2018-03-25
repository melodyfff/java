package io.file;

/**
 * 比较接口
 * @Description:
 * @author xinchen 2016年10月17日 下午10:28:13
 */
interface Compare {
	/**
	 *
	 * 小于
	 * @param lhs
	 * @param rhs
	 * @return
	 */
	boolean lessThan(Object lhs, Object rhs);

	/**
	 * 小于或等于
	 * @param lhs
	 * @param rhs
	 * @return
	 */
	boolean lessThanOrEqual(Object lhs, Object rhs);
}
