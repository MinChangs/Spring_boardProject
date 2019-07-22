package kr.or.ddit.board.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.board.model.CalendarVo;
import kr.or.ddit.board.service.IBoardService;
@Controller
public class Calendar2 {
	private static final Logger logger = LoggerFactory.getLogger(Calendar2.class);
	@Resource(name = "boardService")
	IBoardService boardService;

	@RequestMapping(path = "/calendar")
	public String calendarView() {
		return "FullCalendar-Example-master/calendar";
	}
	
	@RequestMapping(path = "/insertCalendar")
//	int _id,
	public String insertData(Model model,  
//						String title, String description ,String startTime, String endTime,
//						  String startDate, String endDate, int[] dow, String type, String username,
//						  String backgroundColor, String textColor,boolean allDay) {
			@RequestBody List<Map<String,Object>>  list){
		logger.debug("!!!!endDate : {}", ((String)list.get(0).get("endDate")).getClass());
		logger.debug("!!!!dow : {}", list.get(0).get("dow").getClass());
//		int[] dow = ((List<Map<String, Object>>) list.get(0).get("dow")).toArray(new int[((List<Map<String, Object>>) list.get(0).get("dow")).size()]);
//		int[] dow = new int[((List<Map<String, Object>>) list.get(0).get("dow")).size()];
//
//		int size=0;
//
//		for(int temp :  list.get(0).get("dow")){
//
//			dow[size++] = temp;
//
//		}
//		Object[] dow = ((List<Map<String, Object>>) list.get(0).get("dow")).toArray(new int[((List<Map<String, Object>>) list.get(0).get("dow")).size()]);


		
		String[] items = list.get(0).get("dow").toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

		int[] dow = new int[items.length];

		for (int i = 0; i < items.length; i++) {
		    try {
		    	dow[i] = Integer.parseInt(items[i]);
		    } catch (NumberFormatException nfe) {
		        //NOTE: write something here if you need to recover from formatting errors
		    };
		}
		
		
		logger.debug("!!!!dow : {}", dow);
//		boolean result = Boolean.valueOf((boolean) list.get(0).get("allDay")).booleanValue();
		logger.debug("!!!!allday : {}", (boolean) list.get(0).get("allDay"));
//		logger.debug("!!!!endTime : {}", endTime);
		
		SimpleDateFormat dateFormat;
		
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //년월일 표시

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(((String)list.get(0).get("endDate")).substring(0,4))); // 종료 날짜 셋팅
		cal.set(Calendar.MONTH,Integer.parseInt(((String)list.get(0).get("endDate")).substring(5,7))-1); // 종료 날짜 셋팅
		cal.set(Calendar.DATE,Integer.parseInt(((String)list.get(0).get("endDate")).substring(8))); // 종료 날짜 셋팅
		cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(((String)list.get(0).get("endTime")).substring(0,2))); // 종료 날짜 셋팅
		cal.set(Calendar.MINUTE,Integer.parseInt(((String)list.get(0).get("endTime")).substring(3))); // 종료 날짜 셋팅

		String endDate2 = dateFormat.format(cal.getTime());

		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR,Integer.parseInt(((String)list.get(0).get("startDate")).substring(0,4))); // 시작 날짜 셋팅
		cal2.set(Calendar.MONTH,Integer.parseInt(((String)list.get(0).get("startDate")).substring(5,7))-1); // 시작 날짜 셋팅
		cal2.set(Calendar.DATE,Integer.parseInt(((String)list.get(0).get("startDate")).substring(8))); // 시작 시작 셋팅
		cal2.set(Calendar.HOUR_OF_DAY,Integer.parseInt(((String)list.get(0).get("startTime")).substring(0,2))); // 시작 날짜 셋팅
		cal2.set(Calendar.MINUTE,Integer.parseInt(((String)list.get(0).get("startTime")).substring(3))); // 시작 날짜 셋팅
		String startDate2 = dateFormat.format(cal2.getTime());

		int compare = cal.compareTo(cal2);
		while (true) { // 다르다면 실행, 동일 하다면 빠져나감
			for(int i=0; i<dow.length; i++) {
				if (cal2.get(cal2.DAY_OF_WEEK)==dow[i]) {
//					startDate2 = dateFormat.format(cal2.getTime());
					startDate2 = startDate2.substring(0, 10) + "T" + startDate2.substring(11, startDate2.length());
					endDate2 = startDate2.substring(0, 10) + "T" + endDate2.substring(11, endDate2.length());
					
					CalendarVo vo = new CalendarVo();
					vo.setC_allDay((boolean) list.get(0).get("allDay"));
					vo.setC_backgroundColor(((String)list.get(0).get("backgroundColor")));
					vo.setC_description(((String)list.get(0).get("description")));
//					vo.setC_id(_id);
					vo.setC_textColor(((String)list.get(0).get("textColor")));
					vo.setC_title(((String)list.get(0).get("title")));
					vo.setC_type(((String)list.get(0).get("type")));
					vo.setC_username(((String)list.get(0).get("username")));
					vo.setC_start(startDate2);
					vo.setC_end(endDate2);
					
					System.out.println("start: "+startDate2+"      end : "+endDate2);
					boardService.insertCalendar(vo);
					
				}
			}
			cal2.add(Calendar.DATE, 1); //1일 더해줌
			startDate2 = dateFormat.format(cal2.getTime()); // 비교를 위한 값 셋팅


			compare = cal2.compareTo(cal);
			if(compare>0) {
				break;
			}

		}

		
		return "redirect:/getCalendar";
	}
	
	@RequestMapping(path = "/updateCalendar")
//	int _id,
	public String updateCalendar(Model model, @RequestBody List<Map<String,Object>>  list){
		logger.debug("!!!!endDate : {}", ((String)list.get(0).get("endDate")));
		logger.debug("!!!!_id : {}", Integer.parseInt((String)list.get(0).get("_id")));
		
//		logger.debug("!!!!dow : {}", list.get(0).get("dow").getClass());

//		boolean result = Boolean.valueOf((boolean) list.get(0).get("allDay")).booleanValue();
		logger.debug("!!!!allday : {}", (boolean) list.get(0).get("allDay"));
//		logger.debug("!!!!endTime : {}", endTime);
		
		SimpleDateFormat dateFormat;
		
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //년월일 표시
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(((String)list.get(0).get("endDate")).substring(0,4))); // 종료 날짜 셋팅
		cal.set(Calendar.MONTH,Integer.parseInt(((String)list.get(0).get("endDate")).substring(5,7))-1); // 종료 날짜 셋팅
		cal.set(Calendar.DATE,Integer.parseInt(((String)list.get(0).get("endDate")).substring(8))); // 종료 날짜 셋팅
		cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(((String)list.get(0).get("endTime")).substring(0,2))); // 종료 날짜 셋팅
		cal.set(Calendar.MINUTE,Integer.parseInt(((String)list.get(0).get("endTime")).substring(3))); // 종료 날짜 셋팅
		
		String endDate2 = dateFormat.format(cal.getTime());
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR,Integer.parseInt(((String)list.get(0).get("startDate")).substring(0,4))); // 시작 날짜 셋팅
		cal2.set(Calendar.MONTH,Integer.parseInt(((String)list.get(0).get("startDate")).substring(5,7))-1); // 시작 날짜 셋팅
		cal2.set(Calendar.DATE,Integer.parseInt(((String)list.get(0).get("startDate")).substring(8))); // 시작 시작 셋팅
		cal2.set(Calendar.HOUR_OF_DAY,Integer.parseInt(((String)list.get(0).get("startTime")).substring(0,2))); // 시작 날짜 셋팅
		cal2.set(Calendar.MINUTE,Integer.parseInt(((String)list.get(0).get("startTime")).substring(3))); // 시작 날짜 셋팅
		String startDate2 = dateFormat.format(cal2.getTime());
		

			startDate2 = startDate2.substring(0, 10) + "T" + startDate2.substring(11, startDate2.length());
			endDate2 = endDate2.substring(0, 10) + "T" + endDate2.substring(11, endDate2.length());
					
			CalendarVo vo = new CalendarVo();
			vo.setC_allDay((boolean) list.get(0).get("allDay"));
			vo.setC_backgroundColor(((String)list.get(0).get("backgroundColor")));
			vo.setC_description(((String)list.get(0).get("description")));
			vo.setC_id(Integer.parseInt((String)list.get(0).get("_id")));
			vo.setC_textColor(((String)list.get(0).get("textColor")));
			vo.setC_title(((String)list.get(0).get("title")));
			vo.setC_type(((String)list.get(0).get("type")));
			vo.setC_username(((String)list.get(0).get("username")));
			vo.setC_start(startDate2);
			vo.setC_end(endDate2);
					
			System.out.println("start: "+startDate2+"      end : "+endDate2);
			boardService.updateCalendar(vo);
					
		

			
//		}
		
		
		return "redirect:/getCalendar";
	}
	
	
	
	
	
	
	@RequestMapping(path = "/deleteCalendar")
	public String deleteCalendar(Model model, int c_id){
		logger.debug("!!!! c_id : {}", c_id);
		boardService.deleteCalendar(c_id);
		return "redirect:/getCalendar";
	}
	
	
	@RequestMapping(path = "/getCalendar")
	public String getCalendar(Model model) {
		
		List<CalendarVo> list = boardService.getCalendar();
		for(int i=0;i<list.size(); i++) {
			System.out.println("list : "+list.get(i));
			
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		for(int i=0; i<list.size(); i++) {
			map.put("_id",list.get(i).getC_id());
			map.put("backgroundColor",list.get(i).getC_backgroundColor());
			map.put("description",list.get(i).getC_description());
			map.put("end",list.get(i).getC_end());
			map.put("start",list.get(i).getC_start());
			map.put("textColor",list.get(i).getC_textColor());
			map.put("title",list.get(i).getC_title());
			map.put("type",list.get(i).getC_type());
			map.put("username",list.get(i).getC_username());
			map.put("allday",list.get(i).isC_allDay());
		}
		model.addAttribute("list",list);
//		model.addAllAttributes(list);

		
		return "jsonView";
		
	}
	
	
	
	
}
