package web.common;

import java.io.BufferedReader;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class Common {
	 public static final String IMAGE_PATH = "C:\\Users\\SEC\\Desktop\\TeamProject\\MomentTeamProject\\MomentSpring\\moment\\src\\main\\webapp\\resources\\uploadFile";
	 public static final String IMAGE_PATH_BACKGROUND = "D:\\Tomcat8.5\\webapps\\moment\\resources\\image\\background";
	 public static final String IMAGE_PATH_PROFILE = "D:\\Tomcat8.5\\webapps\\moment\\resources\\profile";
	 
	 
	 
	 //request값 제이슨으로 파싱
	 public static JSONObject jsonParser(BufferedReader requestData) {
		StringBuffer json = new StringBuffer();
	    String line = null;
	    JSONObject jsonObject = null;
	    try {
	    	
	    	//request로 부터 제이슨 데이터 받아오기
	        BufferedReader reader = requestData;
	        
	        
	        while(( line = reader.readLine() ) != null) {
	            json.append(line);
	        }
	        
	        //읽어온 Json 파싱
		    JSONParser jsonParser = new JSONParser();
		    
		    //파싱한 Json 데이터를 오브젝트에 담아준 후
		    Object jsonParserObject = jsonParser.parse(json.toString());
		    
		    //Object 타입을 JSONObject로 선언
		    jsonObject = (JSONObject)jsonParserObject;
		    
	    }catch(Exception e) {
	    	System.out.println("JSON 데이터 가져오기 실패");
	    }
	    return jsonObject;
	}
}
