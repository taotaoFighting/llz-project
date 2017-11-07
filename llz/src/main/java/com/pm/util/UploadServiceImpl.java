package com.pm.util;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.httpclient.HttpURL;
import org.apache.log4j.Logger;
import org.apache.webdav.lib.WebdavResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import com.pm.dao.DishesDao;
import com.pm.entity.Dishes;

/**
 * 
 * @author zelei.fan
 *
 */
@Service
public class UploadServiceImpl implements UploadService{
	
	@Autowired
	DishesDao dishesDao;
	
	private static final Logger loger = Logger.getLogger(UploadServiceImpl.class);
	
//	@Value("${upload.file.url}")
    private String uploadUrl = "http://192.168.18.245/ProjcetManager";

    @Value("webdav")
    private String uploadUsername;

    @Value("webdav")
    public String uploadPassword;
	
	public FileInfo uploadFile(MultipartFile multipartFile) {
		
		//取到文件大小，如果超过指定范围的话就直接返回提醒错误
        long size = multipartFile.getSize();
        //获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀，即文件类型
        String fileExt = "";
        if (fileName.contains(".")) {
            fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        }
        //设置MD5加密
        String fileMD5 = md5File(multipartFile);

        //拼接文件路径:/后缀/年/月/日/md5/filename
        String saveUrl = "/" + fileExt + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileMD5 + "/" + multipartFile.getOriginalFilename();

        loger.info("save-path"+saveUrl);
        
        String location = null;
		try {
			location = saveFile(multipartFile, saveUrl);//保存文件操作
		} catch (Exception e) {
			e.printStackTrace();
		}

        FileInfo fileInfo = new FileInfo();
        fileInfo.setAbsoluteUrl(location);
        fileInfo.setRelativeUrl(saveUrl);
        fileInfo.setFileMd5(fileMD5);
        fileInfo.setFileName(fileName);
        fileInfo.setFileSize(String.valueOf(size));
        fileInfo.setFileType(fileExt);
        return fileInfo;

	}
	
	//MD5加密
	private String md5File(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	
	
	private String saveFile(MultipartFile file, String savePath) throws Exception {
        // 上传文件，到文件服务器，uploadUrl是在config中配好的，就是给uploadUrl赋值，如果不用那么麻烦的话可以直接把url放进去:upload("http://192.168.102.11/test", uploadUsername, uploadPassword, savePath, file.getBytes());
    	upload(uploadUrl, "tomcat"/*那台服务器的用户名*/, "tomcat"/*那台服务器的密码*/, savePath, file.getBytes());
        return append(uploadUrl, savePath);
        
    }
	
	
	public static void upload(String webDavServer, String webDavUser, String webDavPassword, String remotePath, byte[] bytes) throws IOException {

		if (!webDavServer.endsWith("/")) webDavServer += "/";
		
		//连接服务器
		HttpURL hrl = new HttpURL(webDavServer);
		hrl.setUserinfo(webDavUser, webDavPassword);

		WebdavResource wdr =  new WebdavResource(hrl);

		//make directory if need
		StringBuffer ssdir = new StringBuffer();
		// 去除最后一个文件名
		StringTokenizer t = new StringTokenizer(remotePath.substring(0, remotePath.lastIndexOf("/")), "/");
		while(t.hasMoreTokens()){
			String sdir = t.nextToken();
			ssdir.append(sdir+"/");
			wdr.mkcolMethod(wdr.getPath() + ssdir );
		}

		String remoteFile= wdr.getPath() + remotePath;//拼成绝对地址
		boolean result = wdr.putMethod(remoteFile, bytes);//把文件写进去
		checkArgument(result, "文件上传出错");//false时会报错，true则为成功

		wdr.close();//最后关闭连接

	}
	
	
	
    /**
     * 连接 URL
     * @param paths
     * @return
     */
    public static String append(String... paths) {
        List<String> pathList = Lists.newArrayList(paths);
        PeekingIterator<String> iter = Iterators.peekingIterator(pathList.iterator());
        StringBuilder urlBuilder = new StringBuilder();
        while (iter.hasNext()) {
            String current = iter.next();
            urlBuilder.append(current);
            if (!iter.hasNext()) {
                break;
            }
            if (current.endsWith("/") && iter.peek().startsWith("/")) {
                urlBuilder.deleteCharAt(urlBuilder.length() - 1);
            } else if (!current.endsWith("/") && !iter.peek().startsWith("/")) {
                urlBuilder.append("/");
            }
        }
        return urlBuilder.toString();
    }
    
    //转存文件到指定路径
    public Map<String, String> saveFile2Disk(MultipartFile multipartFile , String path) {
    	
    	Map<String, String> map = new HashMap<String, String>();
    	
    	if (!multipartFile.isEmpty()) {
			 loger.info("path = "+path);
			 try {
				multipartFile.transferTo(new File(path));
			} catch (IllegalStateException e) {
				e.printStackTrace();
				map.put("path", "上传出现错误");
			} catch (IOException e) {
				e.printStackTrace();
				
				map.put("path", "上传出现错误");
			}finally{
				map.put("path", multipartFile.getOriginalFilename());
			}
		}
    	
    	return map;
	}
    
    public Status deleteFileWithFilePath(String filePath){
    	
    	Status status = new Status();
    	
    	if (filePath.isEmpty()) {
    		status.setStatus(0);
	    	status.setMsg("路径不存在");
		}
    	
    	File file = new File(filePath);
    	// 路径为文件且不为空则进行删除
    	if (file.isFile() && file.exists()) {
    		
    	    boolean flag =  file.delete();
    	    
    	    if (flag) {
    	    	//文件删除成功
    	    	status.setStatus(1);
    	    	status.setMsg("文件删除成功");
			}else {
				//文件删除失败
				status.setStatus(0);
    	    	status.setMsg("文件删除失败");
			}
    	 
    	}else {
			//文件不存在
    		status.setStatus(0);
	    	status.setMsg("文件不存在");
		}
    	
    	return status;
    }

}
