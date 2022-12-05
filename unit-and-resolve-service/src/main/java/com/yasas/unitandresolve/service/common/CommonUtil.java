package com.yasas.unitandresolve.service.common;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Random;

public class CommonUtil {

    private CommonUtil() {
    }

    public static String generateCode(int codeLength) {
        return new DecimalFormat(StringUtils.repeat("0", codeLength)).format(new Random().nextInt(9999));
    }
}
