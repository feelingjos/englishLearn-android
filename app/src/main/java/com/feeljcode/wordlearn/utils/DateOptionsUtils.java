package com.feeljcode.wordlearn.utils;

import com.alibaba.fastjson.util.TypeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Auther: feelj
 * @Date: 2019/8/19  23:13:19
 * @Description:
 */
public class DateOptionsUtils {


    /**
     * 获取日期 yyyymmdd格式
     * @param date    日期
     * @param range   与日期的偏移量
     * @return
     */
    public static Integer getDate(String date,Integer range){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());

        if(null != date && !"".equals(date)){
            try {
                Date parse = dateFormat.parse(date);
                calendar.setTime(parse);
            }catch (Exception ex){
                ex.getStackTrace();
            }
        }
        calendar.add(calendar.DATE,range);

        return TypeUtils.castToInt(dateFormat.format(calendar.getTime()));
    }

}
