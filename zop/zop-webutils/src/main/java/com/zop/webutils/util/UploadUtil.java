package com.zop.webutils.util;

import com.zop.utils.util.StringUtil;
import com.zop.webutils.context.ZopWebUtilsSetting;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UploadUtil {
	
	private UploadUtil(){
		
	}

	public static String replacePath(String path){
		if(StringUtil.isEmpty(path)){
            return path;
        }
		return  path.replaceAll(ZopWebUtilsSetting.FILE_STORE_PREFIX, ZopWebUtilsSetting.IMG_SERVER_DOMAIN);
	}

	/**
	 * 转换图片的名称
	 * @param filePath  如：http://static.eop.com/user/1/1/attachment/goods/2001010101030.jpg
	 * @param shortName 
	 * @return
	 */
	public static  String getThumbPath(String filePath, String shortName) {
		String pattern = "(.*)([\\.])(.*)";
		String thumbPath = "$1" + shortName + "$2$3";

		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(filePath);
		if (m.find()) {
			String s = m.replaceAll(thumbPath);

			return s;
		}
		return null;
	}	
	
	
	public static void main(String[] args){
	 System.out.println(getThumbPath("http://static.eop.com/user/1/1/attachment/goods/2001010101030.jpg","_thumbnail"));	
	 System.out.println(getThumbPath("/user/1/1/attachment/goods/2001010101030.jpg","_thumbnail"));	
	}
	
}
