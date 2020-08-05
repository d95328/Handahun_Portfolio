package web.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import android.common.Common;

public class FileUpload {
	public static Map<String, String> upload(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		
		//String savePath = "upload";
		String savePath = Common.IMAGE_FILE_SAVE_PATH;
		Map<String, String> fileUploadMap = new HashMap<>();
		String encoding = "UTF-8";
		
		File currentDirPath = new File(savePath);
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		factory.setRepository(currentDirPath);
		
		ServletFileUpload upload = new ServletFileUpload(factory);
	
		try {
			List<FileItem> items = upload.parseRequest(request);
			
			for (int i = 0; i < items.size(); i++) {
				FileItem fileItem = (FileItem) items.get(i);
				if(fileItem.isFormField()) {
					System.out.println(fileItem.getFieldName()+" = "+fileItem.getString(encoding));
					fileUploadMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
				}else {
					if(fileItem.getSize() > 0) {
						int idx = fileItem.getName().lastIndexOf("\\");
						
						if(idx == -1) {
							idx = fileItem.getName().lastIndexOf("/");
						}
						String fileName = fileItem.getName().substring(idx + 1);
						fileUploadMap.put(fileItem.getFieldName(), fileName);
						//�뙆�씪 留뚮뱾湲�
						File uploadFile = new File(savePath + "\\" + fileName);
						System.out.println(uploadFile.getAbsolutePath());
						fileItem.write(uploadFile);
					}
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return fileUploadMap;
	}
	
	public static void imageMerge(String imagePath, String imageName1, String imageName2) {
		try {
			BufferedImage bimage1 = ImageIO.read(new File(imagePath+"\\"+imageName1));
		   BufferedImage bimage2 = ImageIO.read(new File(imagePath+"\\"+imageName2));
		   
		   Image img1 = bimage1.getScaledInstance(bimage1.getWidth(), bimage1.getHeight(), Image.SCALE_SMOOTH);
		   Image img2 = bimage2.getScaledInstance(bimage2.getWidth(), bimage2.getHeight(), Image.SCALE_SMOOTH);

		   int width = bimage1.getWidth()+ bimage2.getWidth();
		   int height = Math.max(bimage1.getHeight() , bimage2.getHeight());

		   BufferedImage mergedImage = new BufferedImage(width+160, height+1000, BufferedImage.TYPE_INT_RGB);
//		   BufferedImage mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		   Graphics2D graphics = (Graphics2D) mergedImage.getGraphics();

		   graphics.setBackground(Color.WHITE);
		   graphics.drawImage(img1, 0, 500, null);
		   graphics.drawImage(img2, bimage1.getWidth()+80, 500, null);
//		   graphics.drawImage(img1, 0, 0, null);
//		   graphics.drawImage(img2, bimage1.getWidth(), 0, null);
		   
		   ImageIO.write(mergedImage, "jpg", new File(imagePath+"\\"+imageName1.replace("_1.jpg", "_rev.jpg")));
		   // ImageIO.write(mergedImage, "jpg", new File("c:\\mergedImage.jpg"));
		   // ImageIO.write(mergedImage, "png", new File("c:\\mergedImage.png"));
		  } catch (IOException ioe) {
		   ioe.printStackTrace();
		  }

		  System.out.println("사진 합성 완료");
		 }
		
}
