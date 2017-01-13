package com.devsilo.domain;

public class Id {

    private final long value;

    public Id(long untrusted_Id) throws IllegalArgumentException {
        if (untrusted_Id > 0) {
            this.value = untrusted_Id;
        } else {
            throw new IllegalArgumentException("Id must be greater than zero");
        }
    }

    public long getValue() {
        return value;
    }
}
