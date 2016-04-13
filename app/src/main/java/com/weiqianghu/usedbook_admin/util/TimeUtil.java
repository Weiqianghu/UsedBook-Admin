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

        String month = time.substring(5,7);
        if ("01".equals(month) || "02".equals(month) || "03".equals(month)) {
            return 1;
        }
        if ("04".equals(month) || "06".equals(month) || "05".equals(month)) {
            return 2;
        }
        if ("08".equals(month) || "09".equals(month) || "07".equals(month)) {
            return 3;
        }
        if ("12".equals(month) || "10".equals(month) || "11".equals(month)) {
            return 4;
        }
        return -1;
    }
}
