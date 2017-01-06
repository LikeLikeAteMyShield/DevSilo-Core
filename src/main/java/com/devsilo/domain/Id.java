package com.devsilo.domain;

public class Id {

    private final long value;

    public Id(long untrusted_Id) throws Exception {
        if (untrusted_Id > 0) {
            this.value = untrusted_Id;
        } else {
            throw new Exception("Id must be greater than zero");
        }
    }

    public long getValue() {
        return value;
    }
}
