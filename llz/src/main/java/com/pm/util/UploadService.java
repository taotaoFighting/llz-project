package com.pm.util;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("UploadService")
public interface UploadService {

	/**
	 * 上传文件，返回链接
	 * @param multipartFile
	 * @return
	 */
	FileInfo uploadFile(MultipartFile multipartFile);
	
	 public Map<String, String> saveFile2Disk(MultipartFile multipartFile , String path);
	 
	 public Status deleteFileWithFilePath(String filePath);

}