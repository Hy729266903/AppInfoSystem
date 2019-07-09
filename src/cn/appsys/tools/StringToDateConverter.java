package cn.appsys.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String,Date> {

	private String datePattern;  //日期格式, yyyy-MM-dd或yyyyMMdd
	public StringToDateConverter(String datePattern) {
		this.datePattern = datePattern;  //datePattern的值就为yyyy-MM-dd
	}
	@Override
	public Date convert(String str) {
		Date date = null;
		try {
			//将字符串转换成Date类型 
			date = new SimpleDateFormat(datePattern).parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}


}
