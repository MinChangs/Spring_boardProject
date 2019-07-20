package kr.or.ddit.crawling;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class CrawlingTest {
   public static void main(String[] args) throws UnsupportedEncodingException, IOException {
      CrawlingTest ct = new CrawlingTest();

      ct.jsoupSelect();
   }
   
   public void jsoupSelect() throws IOException {
      
	   String url = "https://search.naver.com/search.naver?where=news&sm=tab_jum&query=치매";
	   Document doc = Jsoup.connect(url).get(); 

	   Elements imgs = doc.select("#main_pack .type01 dl");
	   if(imgs.size() > 0) {
	       String src = imgs.outerHtml();
	       System.out.println(src);
	   }

   }
   
}