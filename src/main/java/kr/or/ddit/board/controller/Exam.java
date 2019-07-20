package kr.or.ddit.board.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Exam {
	public static void main(String[] args) {
		
		int [] dow  = {1,3,5};
		
		SimpleDateFormat dateFormat;
		
		
		//dateFormat = new SimpleDateFormat("yyyyMM"); // 년월 표시
		 dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //년월일 표시
		
		 
		String endDate="2019-05-30";
		String endTime="15:30";
		String startDate="2019-05-01";
		String startTime="10:30";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(endDate.substring(0,4))); // 종료 날짜 셋팅
		cal.set(Calendar.MONTH,Integer.parseInt(endDate.substring(5,7))-1); // 종료 날짜 셋팅
		cal.set(Calendar.DATE,Integer.parseInt(endDate.substring(8))); // 종료 날짜 셋팅
		cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(endTime.substring(0,2))); // 종료 날짜 셋팅
		cal.set(Calendar.MINUTE,Integer.parseInt(endTime.substring(3))); // 종료 날짜 셋팅

		String endDate2 = dateFormat.format(cal.getTime());

		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR,Integer.parseInt(startDate.substring(0,4))); // 종료 날짜 셋팅
		cal2.set(Calendar.MONTH,Integer.parseInt(startDate.substring(5,7))-1); // 종료 날짜 셋팅
		cal2.set(Calendar.DATE,Integer.parseInt(startDate.substring(8))); // 종료 날짜 셋팅
		cal2.set(Calendar.HOUR_OF_DAY,Integer.parseInt(startTime.substring(0,2))); // 종료 날짜 셋팅
		cal2.set(Calendar.MINUTE,Integer.parseInt(startTime.substring(3))); // 종료 날짜 셋팅
		String startDate2 = dateFormat.format(cal2.getTime());

		int compare = cal.compareTo(cal2);
		while (true) { // 다르다면 실행, 동일 하다면 빠져나감
			for(int i=0; i<dow.length; i++) {
				if (cal2.get(cal2.DAY_OF_WEEK)==dow[i]) {
					startDate2 = dateFormat.format(cal2.getTime());
					startDate2 = startDate2.substring(0, 10) + "T" + startDate2.substring(11, startDate2.length());
					endDate2 = startDate2.substring(0, 10) + "T" + endDate2.substring(11, endDate2.length());
					
					
					System.out.println("start: "+startDate2+"      end : "+endDate2);
					
				}
			}
			cal2.add(Calendar.DATE, 1); //1일 더해줌
			startDate2 = dateFormat.format(cal2.getTime()); // 비교를 위한 값 셋팅


			compare = cal2.compareTo(cal);
			if(compare>0) {
				break;
			}

		}
	}
}
