package IO.file;

/**
 * @Description:
 * @author xinchen 2016年10月17日 下午10:28:13
 */
interface Compare {
	boolean lessThan(Object lhs, Object rhs);

	boolean lessThanOrEqual(Object lhs, Object rhs);
}
