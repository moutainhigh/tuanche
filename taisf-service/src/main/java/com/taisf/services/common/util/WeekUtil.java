package com.taisf.services.common.util;

import com.jk.framework.base.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2018/4/12.
 * @version 1.0
 * @since 1.0
 */
public class WeekUtil {

    /**
     * 获取今天周几
     * @return
     */
    public static int getWeek() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取今天周几
     * @return
     */
    public static int getWeek(Date time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public static void main(String[] args) {

        Date first = DateUtil.getFirstDayOfWeekDay(new Date());

        System.out.println(first);
        System.out.println(getWeek(DateUtil.getFirstDayOfWeekDay(new Date())));
    }
}
