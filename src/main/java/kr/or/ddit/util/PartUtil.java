package kr.or.ddit.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PartUtil {

	private static final String UPLOAD_PATH = "d:\\upload\\";



	/**
	* Method : getExt
	* 작성자 : PC24
	* 변경이력 :
	* @param fileName
	* @return
	* Method 설명 : 파일명으로부터 파일 확장자를 반환
	*/
	public static String getExt(String fileName) {
		String ext="";
		if(fileName.contains(".")){
			ext="."+fileName.substring(fileName.lastIndexOf('.')+1);
			return ext;
		}
		return ext;
	}
	
	
	/**
	* Method : checkUploadFolder
	* 작성자 : PC24
	* 변경이력 :
	* @param yyyy
	* @param mm
	* Method 설명 : 년, 월 업로드 폴더가 존재하는지 체크, 없을 경우 폴더 생성
	*/
	public static void checkUploadFolder(String yyyy, String mm) {		
		
		File yyyyFolder = new File(UPLOAD_PATH + yyyy);
		if (!yyyyFolder.exists()) {
			yyyyFolder.mkdir();
		}

		File mmFolder = new File(UPLOAD_PATH + yyyy+ File.separator + mm);
		if (!mmFolder.exists()) {
			mmFolder.mkdir();
		}
		
	}
	
	
	public static String getUploadPath(){
		Date dt = new Date();
		SimpleDateFormat yyyyMMSdf = new SimpleDateFormat("yyyyMM");
		String yyyyMM = yyyyMMSdf.format(dt);
		String yyyy = yyyyMM.substring(0,4);
		String mm = yyyyMM.substring(4,6);

		PartUtil.checkUploadFolder(yyyy, mm);
		
		return UPLOAD_PATH+ yyyy + File.separator + mm;
	}
	

	
}
