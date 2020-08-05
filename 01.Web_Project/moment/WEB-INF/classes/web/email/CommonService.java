package web.email;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;

import android.common.Common;
import web.user.WebUserServiceImpl;
import web.user.WebUserVO;

@Service
public class CommonService {

	@Autowired
	private WebUserServiceImpl mailservice;
	
	
	// 지오 코딩 API 처리 
	private String data_list(StringBuilder url) {
		String result = url.toString();

		try {

			HttpURLConnection conn = (HttpURLConnection) new URL(result).openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json; charset=utf-8");
			
			BufferedReader reader = null;
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			} else {
				reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
			}
			url = new StringBuilder();
			while ((result = reader.readLine()) != null) {
				url.append(result);
			}
			reader.close();
			conn.disconnect();
			result = url.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	// 지오 코딩 API 처리 
	public JSONObject json_list(StringBuilder url) {

		JSONObject json = null;
		JSONObject geometry = null;
		JSONArray jsonArr = null;
		String formatted_address=null;
		JSONObject data=null;
		
		try {
			json = (JSONObject)new JSONParser().parse( data_list(url) ); //json형태로 만듬
			
			
			 // 지오코딩api에 자료가 있다면 
				jsonArr = (JSONArray)json.get("results"); 
		
				
				 for(int i=0; i<jsonArr.size(); i++) {
					  
					 json = (JSONObject)jsonArr.get(i);
					 geometry = (JSONObject)json.get("geometry");
					 formatted_address = (String)json.get("formatted_address");
			      }			 
				
				 JSONObject location = (JSONObject)geometry.get("location");
				
					 data = new JSONObject(); 
					 data.put("lng",location.get("lng"));
					 data.put("lat",location.get("lat")); 
					 data.put("address",formatted_address);
					
			
				 
				 System.out.println("위도" + location.get("lng"));
				 System.out.println("경도" +location.get("lng"));
				 System.out.println("주소" +formatted_address);
			 			
			
			
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(data);
		return data;
	}

	public File download(String filename, String filepath, HttpSession session, HttpServletResponse response) {
		File file = new File(session.getServletContext().getRealPath("resources") + filepath);
		String mime = session.getServletContext().getMimeType(filename);

		response.setContentType(mime);

		try {
			filename = URLEncoder.encode(filename, "utf-8").replaceAll("\\+", "%20");
			response.setHeader("content-disposition", "attachment; filename=" + filename);
			ServletOutputStream out = response.getOutputStream();
			FileCopyUtils.copy(new FileInputStream(file), out);
			out.flush();

		} catch (Exception e) {
		}
		return file;
	}

	public String upload(String category, MultipartFile file, HttpSession session) {
		// 프로젝트의 물리적인 위치에 선택한 파일을 업로드한다.
		String resources = session.getServletContext().getRealPath("resources");
		String upload = resources + "/image";
		String folder = upload + "/" + category; /*+ "/" + new SimpleDateFormat("yyyy/MM/dd").format(new Date());*/
		
		File f = new File(folder);
		if (!f.exists())
			f.mkdirs();

		String uuid = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		try {
			file.transferTo(new File(folder, uuid));
		} catch (Exception e) {
		}
		// 파일을 업로드해 둔 위치를 DB에 저정할 수 있도록 위치를 반환한다.
		//return folder.substring(resources.length()) + "/" + uuid;
		return uuid;
	}

	
	
	
	// 게시판 새글 저장 이미지 업로드
	public String uploadpic(String category, MultipartFile file, HttpSession session) {

		String resources = session.getServletContext().getRealPath("resources");
		String upload = resources + "/image";
		String folder = upload + "/" + category;
		
		
		File f = new File(folder);
		if (!f.exists())
			f.mkdirs();

		String uuid  = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		try {
			file.transferTo(new File(folder, uuid));
		} catch (Exception e) {
		}

		return uuid;
	}

	

	public void sendHtml(WebUserVO vo, HttpSession session) throws Exception {
		HtmlEmail mail = new HtmlEmail();
		String key = new TempKey().getKey(50, false); // 인증키 생성
		// 서버지정
		mail.setHostName("smtp.naver.com");
		mail.setDebug(true);
		mail.setCharset("utf-8");
		// 메일서버에 로그인하기
		mail.setAuthentication("kjn02131", "im6141**");
		mail.setSSLOnConnect(true);
		mailservice.createAuthKey(vo.getU_userid(), key);

		try {
			mail.setFrom("kjn02131@naver.com", "Moment관리자"); // 송신인
			mail.addTo(vo.getU_userid(), vo.getU_name()); // 수신인

			mail.setSubject("Moment 가입");

			StringBuffer msg = new StringBuffer();
			msg.append("<html>");
			msg.append("<body>");
			msg.append("<h3>Moment</h3>");
			msg.append("<p>가입을 축하합니다.</p>");
			msg.append( // 메일내용
					"<h1>메일인증</h1>" + "<a href='http://localhost/moment/user/useremailConfirm?userid="
							+ vo.getU_userid() + "&key=" + key + "' target='_blenk'>이메일 인증 확인</a>");
			msg.append("</body>");
			msg.append("</html>");
			mail.setHtmlMsg(msg.toString());

			mail.send(); // 메일 보내기

		} catch (Exception e) {
		}
	}

	private void sendAttach(String email, String name, HttpSession session) {
		MultiPartEmail mail = new MultiPartEmail();
		String address = new TempKey().getKey(8, false);
		// 서버지정
		mail.setHostName("smtp.naver.com");
		mail.setDebug(true);
		mail.setCharset("utf-8");

		// 메일서버에 로그인하기
		mail.setAuthentication("kjn02131", "im6141**");
		mail.setSSLOnConnect(true);

		try {
			mail.setFrom("kjn02131@naver.com", "한울관리자"); // 송신인
			mail.addTo(email, name); // 수신인

			mail.setSubject("한울 IoT 가입");
			mail.setMsg(name + " 님, IoT 과정 가입을 축하합니다!");

			// 파일첨부
			EmailAttachment file = new EmailAttachment();
			file.setURL(new URL("https://mvnrepository.com/assets/images/392dffac024b9632664e6f2c0cac6fe5-logo.png"));
			mail.attach(file);

			file = new EmailAttachment();
			file.setPath(session.getServletContext().getRealPath("resources") + "/images/hanul.png");
			mail.attach(file);

			mail.send(); // 이메일 보내기
		} catch (Exception e) {
		}

	}

	private void sendSimple(String email, String name) {
		SimpleEmail mail = new SimpleEmail();

		// 서버지정
		mail.setHostName("smtp.naver.com");
		mail.setDebug(true);
		mail.setCharset("utf-8");

		// 메일서버에 로그인하기
		mail.setAuthentication("kjn02131", "im6141**");
		mail.setSSLOnConnect(true);

		try {
			mail.setFrom("kjn02131@naver.com", "한울관리자"); // 송신인
			mail.addTo(email, name); // 수신인

			mail.setSubject("한울 IoT 가입");
			mail.setMsg(name + " 님, IoT 과정 가입을 축하합니다!");

			mail.send(); // 이메일 보내기
		} catch (EmailException e) {
		}
	}

}
