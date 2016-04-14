package com.weiqianghu.usedbook_admin.util;

import android.text.TextUtils;

/**
 * Created by weiqianghu on 2016/4/13.
 */
public class TimeUtil {
    public static int getQuarter(String time) {
        if (TextUtils.isEmpty(time) || time.length() != 19) {
            return -1;
        }

        String month = time.substring(5, 7);
        if ("01".equals(month) || "02".equals(month) || "03".equals(month)) {
            return 1;
        } else if ("04".equals(month) || "06".equals(month) || "05".equals(month)) {
            return 2;
        } else if ("08".equals(month) || "09".equals(month) || "07".equals(month)) {
            return 3;
        } else if ("12".equals(month) || "10".equals(month) || "11".equals(month)) {
            return 4;
        }
        return -1;
    }

    public static int getMonth(String time) {
        if (TextUtils.isEmpty(time) || time.length() != 19) {
            return -1;
        }

        String month = time.substring(5, 7);

        switch (month) {
            case "01":
                return 1;
            case "02":
                return 2;
            case "03":
                return 3;
            case "04":
                return 4;
            case "05":
                return 5;
            case "06":
                return 6;
            case "07":
                return 7;
            case "08":
                return 8;
            case "09":
                return 9;
            case "10":
                return 10;
            case "11":
                return 11;
            case "12":
                return 12;
            default:
                return -1;
        }
    }
}
