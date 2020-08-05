package android.user;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import android.common.Common;
import android.utill.AndroidFileUpload;

@Repository
public class UserDAO implements UserService{
	
	private static final String CHARACTER_SET = "UTF-8";
	@Autowired private SqlSession sql;
	
	@Override
	public UserVO userLogin(HashMap<String, String> map) {
		return sql.selectOne("user.mapper.login", map);
	}

	@Override
	public JSONObject userSignUp(HttpServletRequest request, HttpServletResponse response) {
		UserVO dto = new UserVO();
		try {
			Map<String, String> fileUploadParamsMap = AndroidFileUpload.upload(request, response, Common.PROFILE_IMG_SAVE_PATH);
			dto.setU_userid(URLDecoder.decode(fileUploadParamsMap.get("u_userid"), CHARACTER_SET));
			dto.setU_name(URLDecoder.decode(fileUploadParamsMap.get("u_name"), CHARACTER_SET));
			dto.setU_nick(URLDecoder.decode(fileUploadParamsMap.get("u_nick"), CHARACTER_SET));
			dto.setU_local(URLDecoder.decode(fileUploadParamsMap.get("u_local"), CHARACTER_SET));
			dto.setU_userpw(URLDecoder.decode(fileUploadParamsMap.get("u_userpw"), CHARACTER_SET));
			dto.setU_profileimg(fileUploadParamsMap.get("u_profileimg"));		
			
			System.out.println(dto);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		int result = sql.insert("user.mapper.signUp", dto); //result 0 or 1 		
		JSONObject joinResult = new JSONObject(); 
		
		if ( result > 0 ) {
			joinResult.put("result", "success"); 
		} else {
			joinResult.put("result", "fail");
		} 
		System.out.println("================ User SignUp Result ================");
		System.out.println(joinResult);
		
		return joinResult;
	}

	@Override
	public JSONObject userModify(HttpServletRequest request, HttpServletResponse response) {
		String encoding = "UTF-8";
		UserVO dto = new UserVO();
		List<FileItem> items = null;
		Map<String, String> fileUploadParamsMap = new HashMap<String, String>();
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			items = upload.parseRequest(request);
			for (int i = 0; i < items.size(); i++) {
				FileItem fileItem = (FileItem) items.get(i);
				if(fileItem.isFormField()) {
					System.out.println(fileItem.getFieldName()+" = "+fileItem.getString(encoding));
					fileUploadParamsMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
				}else {
					if(fileItem.getSize() > 0) {
						int idx = fileItem.getName().lastIndexOf("\\");
						
						if(idx == -1) {
							idx = fileItem.getName().lastIndexOf("/");
						}
						String fileName = fileItem.getName().substring(idx + 1);
						fileUploadParamsMap.put(fileItem.getFieldName(), fileName);
					}
				}
			}
			
			String includeImg = URLDecoder.decode(fileUploadParamsMap.get("includeImg"), CHARACTER_SET);
			System.out.println(includeImg);
			
			//회원 프로필 사진을 변경 했을때.
			if(!includeImg.equals("no")) {
				fileUploadParamsMap = AndroidFileUpload.upload(Common.PROFILE_IMG_SAVE_PATH, items);
				System.out.println(fileUploadParamsMap.toString());
			}
			
			dto.setU_userid(URLDecoder.decode(fileUploadParamsMap.get("u_userid"), CHARACTER_SET));
			dto.setU_nick(URLDecoder.decode(fileUploadParamsMap.get("u_nick"), CHARACTER_SET));
			dto.setU_local(URLDecoder.decode(fileUploadParamsMap.get("u_local"), CHARACTER_SET));
			dto.setU_userpw(URLDecoder.decode(fileUploadParamsMap.get("u_userpw"), CHARACTER_SET));
			dto.setU_profileimg(URLDecoder.decode(fileUploadParamsMap.get("u_profileimg"), CHARACTER_SET));
			System.out.println("================ User Modify Data ================");
			System.out.println(dto.toString());
			
		} catch (Exception e) {
			e.getStackTrace();
		}
		
		int result = sql.update("user.mapper.modify",dto);
		JSONObject ModResult = new JSONObject();
		
		if( result > 0 ) {
			ModResult.put("result", "success");

		} else {
			ModResult.put("result", "fail");
		}
		System.out.println("================ User Modify Result ================");
		System.out.println(ModResult);
		return ModResult;
	}

	@Override
	public JSONObject userIdDuplicateCheck(String u_userid) {
		
		int result = (Integer) sql.selectOne("user.mapper.userIdDuplicateCheck", u_userid);
		JSONObject idckResult = new JSONObject();
		
		if( result > 0 ) {
			idckResult.put("result", "fail");
		} else {
			idckResult.put("result", "success");
		}
		
		System.out.println("================ User ID Duplicate Check Result ================");
		System.out.println(idckResult);
		return idckResult;
	}

	@Override
	public JSONObject userNickDuplicateCheck(String u_nick) {
		
		int result = (Integer) sql.selectOne("user.mapper.userNickDuplicateCheck", u_nick);
		JSONObject nickckResult = new JSONObject();
		
		if( result > 0 ) {
			nickckResult.put("result", "fail");
		} else {
			nickckResult.put("result", "success");
		}
		
		System.out.println("================ User Nick Duplicate Check Result ================");
		System.out.println(nickckResult);
		return nickckResult;
	}
	//수정커밋테스트
	//수정커밋테스트2
	//엿먹어라ㅋㅋㅋㅋ
	
}
