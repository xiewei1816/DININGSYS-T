package com.yqsh.diningsys.core.util;

import java.util.WeakHashMap;

/**
 * 同步器
 *
 */
public class KeySynchronizer implements java.io.Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 8892108852034764996L;
	private static final WeakHashMap<Object, Locker> LOCK_MAP = new WeakHashMap<Object, Locker>();
    
    private static class Locker {
        private Locker() {
            
        }
    }
    
    
    public static synchronized Object acquire(Object key) {
        Locker locker = LOCK_MAP.get(key);
        if(locker == null) {
            locker = new Locker();
            LOCK_MAP.put(key, locker);
        }
        return locker;
    }
}
