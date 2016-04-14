package com.weiqianghu.usedbook_admin.util;

/**
 * Created by weiqianghu on 2016/3/25.
 */
public class OrderStateUtil {

    public static String getStrByOrderState(String orderState) {
        {
            switch (orderState) {
                case Constant.PAY:
                    return "待付款";
                case Constant.DELIVER:
                    return "待发货";
                case Constant.EXPRESS:
                    return "配送中";
                case Constant.EVALUATE:
                    return "待评价";
                case Constant.FINISH:
                    return "已完成";
                default:
                    return "出错了";
            }
        }
    }
}
