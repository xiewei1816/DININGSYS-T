package com.yqsh.diningsys.core.util;

import java.util.Comparator;

/**
 * Created on 2017-03-14 10:36
 *
 * @author zhshuo
 */
public class CompareInteger implements Comparator<Integer>{

    @Override
    public int compare(Integer v1, Integer v2) {
        return v1.compareTo(v2);
    }

}
