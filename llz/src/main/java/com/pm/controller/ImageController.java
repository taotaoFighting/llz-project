package com.pm.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pm.entity.Image;
import com.pm.service.ImageService;
import com.pm.util.Status;
import com.pm.util.UploadService;

@RestController
public class ImageController {
	
	private static final Logger loger = Logger.getLogger(ImageController.class);

	@Autowired
	ImageService imageService;
	
	@Autowired
	private UploadService uploadService;
	
	@RequestMapping(value="/image/{type}",method=RequestMethod.GET)
	public List<Image> listByType(@PathVariable("type") String type) {
		
		return imageService.imageListByType(type);
	}
	
	/**
	 * 需要在spring-mvc中配置MultipartFile相关信息，以及引进fileUpload的依赖
	 * @param multipartFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/file", method = RequestMethod.POST)
	public Map<String, String> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file)throws Exception{
		
////		 String path = request.getSession().getServletContext().getRealPath("../files");
//		 String path = System.getProperty("catalina.home");
//		 path = path+ "/webapps/images/" + multipartFile.getOriginalFilename();
//		 String os = System.getProperty("os.name");  
//		 if(!os.toLowerCase().startsWith("win")){  //判断是否为widows环境
//			 path = path.replace("/", "\\");
//		 }  
		 Map<String, String> map = new HashMap<String, String>();
		if(file!=null){  
            //获取上传文件的原始名称  
            String originalFilename = file.getOriginalFilename();  
//            String newFileName ="";  
            String pic_path;  
            // 上传图片  
            if ( originalFilename != null && originalFilename.length() > 0) {  
                //获取Tomcat服务器所在的路径  
                String tomcat_path = System.getProperty( "catalina.home" );  
                System.out.println(tomcat_path);  
                //获取Tomcat服务器所在路径的最后一个文件目录  
                String bin_path = tomcat_path.substring(tomcat_path.lastIndexOf("\\")+1,tomcat_path.length());  
                System.out.println(bin_path);  
                //若最后一个文件目录为bin目录，则服务器为手动启动  
                if(("bin").equals(bin_path)){//手动启动Tomcat时获取路径为：D:\Software\Tomcat-8.5\bin  
                    //获取保存上传图片的文件路径  
                    pic_path = tomcat_path +"/webapps"+"/images/";  
                }else{//服务中自启动Tomcat时获取路径为：D:\Software\Tomcat-8.5  
                    pic_path = tomcat_path+"/webapps"+"/images/";  
                }  
                // 新的图片名称  
//                newFileName =UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));  
                loger.info("上传图片的路径：" + pic_path + originalFilename);  
                // 新图片  
                File newFile = new File(pic_path + originalFilename);  
                // 将内存中的数据写入磁盘  
                file.transferTo(newFile); 
                
        		map.put("path", originalFilename);
            }  
        }
		
		return map;
		 
//		 loger.info("path = "+path);
//		 return uploadService.saveFile2Disk(multipartFile, path);
	}
	
	
	@RequestMapping(value = "/file" , method = RequestMethod.DELETE)
	public Status deleteFileWithFileName(HttpServletRequest request, @RequestBody HashMap<String, String> reqMap) {
		 
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		
		String pathString = reqMap.get("path");
		
		pathString = pathString.replace("/", "\\");
		
		loger.info("allPath = " + rootPath+pathString);
		
		return uploadService.deleteFileWithFilePath(rootPath+pathString);
		
	}
}
