package com.pm.util;
import org.apache.commons.lang3.StringUtils;


public class UploadFileUtils {

	public static String downloadUrl = null;

    /**
     * 绝对路径与相对路径转换
     */
    public static String addPrefix(String url) {
        // 本身就是绝对路径直接返回
        if (url == null || url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        }
        return  downloadUrl+ url;
    }

    public static String removePrefix(String url) {
        // 如果 url 不符合条件，直接返回
        if (url == null || !url.startsWith(downloadUrl)) {
            return url;
        }
        return StringUtils.removeStart(url, downloadUrl);
    }

    public void setDownloadUrl(String downloadUrl) {
        UploadFileUtils.downloadUrl = downloadUrl;
    }
}