package com.weiqianghu.usedbook_admin.util;

/**
 * Created by weiqianghu on 2016/3/3.
 */
public class AuditUtil {
    public static String getStrByFailureCode(int auditState){
        String verifyStateStr="其他原因";
        switch (auditState){
            case Constant.AUDIT_STATE0:
                verifyStateStr="信息已提交，等待审核";
                break;
            case Constant.AUDIT_STATE1:
                verifyStateStr="审核通过";
                break;
            case Constant.AUDIT_STATE2:
                verifyStateStr="审核失败，身份证和联系人不匹配";
                break;
            case Constant.AUDIT_STATE3:
                verifyStateStr="审核失败，身份证照片太模糊，不能辨认信息";
                break;
        }
        return verifyStateStr;
    }
}
