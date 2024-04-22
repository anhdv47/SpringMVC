package com.topcv.enumeration;

public class CommonEnum {
    public enum AccountStatus {
        INACTIVE, ACTIVE;

        public int toInt() {
            return this.ordinal();
        }
    }
}
