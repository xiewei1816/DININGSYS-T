package com.yqsh.diningsys.api.util;

import java.math.BigDecimal;

public class DoubleCompare {
	public static boolean compare(double val1, double val2) {  
		BigDecimal data1 = new BigDecimal(val1);  
	    BigDecimal data2 = new BigDecimal(val2);
        return data1.compareTo(data2) == 0;
	}
}
