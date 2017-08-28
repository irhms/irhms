package com.messagebird.objects;

import java.math.BigInteger;
/**
 * get and set phone number with country code
 * Created by rvt on 1/7/15.
 */
public class LookupHlr extends Hlr {
    private String countryCode;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public BigInteger getPhoneNumber() {
        return msisdn;
    }

    public void setPhoneNumber(BigInteger phoneNumber) {
        this.msisdn = phoneNumber;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
