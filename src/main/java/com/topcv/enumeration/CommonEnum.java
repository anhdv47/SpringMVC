package com.topcv.enumeration;

public class CommonEnum {
    public enum AccountStatus {
        INACTIVE, ACTIVE;

        public int toInt() {
            return this.ordinal();
        }
    }

    public enum CompanyStatus {
        INACTIVE, ACTIVE;

        public int toInt() {
            return this.ordinal();
        }
    }

    public enum AccountRole {
        None,
        USER,
        COMPANY;

        public int toInt() {
            return this.ordinal();
        }
    }

    public enum CvStatus {
        INACTIVE, ACTIVE;

        public int toInt() {
            return this.ordinal();
        }
    }

    public enum ApplyJobStatus {
        PENDING, ACCEPTED, REJECTED;

        public int toInt() {
            return this.ordinal();
        }
    }
}
