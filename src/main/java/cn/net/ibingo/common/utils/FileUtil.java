package cn.net.ibingo.common.utils;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by  yuxiangjie on 2017-07-07.
 */
public class FileUtil {
    private static Logger logger = Logger.getLogger(String.valueOf(FileUtil.class));

    public static String imageUpload(HttpServletRequest request,MultipartFile file){
        String path= "C://Users/lqj/Desktop/windows_amd64";
        String oldFileName = file.getOriginalFilename();
        InputStream in = null;
        File files =new File(path);
        //如果文件夹不存在则创建
        if(!files .exists()  && !files .isDirectory()) {
            files .mkdir();
        }
        try {
            OutputStream out = new FileOutputStream(new File(path,oldFileName));
            in = file.getInputStream();
            int length = 0 ;
            byte [] buf = new byte[1024] ;
            while( (length = in.read(buf) ) != -1){
                out.write(buf, 0, length);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path+File.separator+oldFileName;
    }




    /**
     * 下载图片
     * @param request
     * @param response
     * @return
     */
    public static void imageDownload(HttpServletRequest request, HttpServletResponse response) {
        InputStream is = null;
        OutputStream os = null;
        //1.获取需要下载的文件
        try {
            File mkdirFile = new File(ConstantConfig.IMAGESPATH);
            if (!mkdirFile.exists()) {
                mkdirFile.mkdirs();
            }
            //处理中文乱码
            String fileName = URLDecoder.decode(request.getParameter("userName"), "UTF-8");
            String imageNamePath = "C://Users/lqj/Desktop/windows_amd64" + File.separator + fileName+".png";//ConstantConfig.IMAGESPATH + File.separator + fileName + ".jpg";

            File file = new File(imageNamePath);
            if(file.exists()){
                // 输入流
                is = new FileInputStream(file);
                response.setCharacterEncoding("UTF-8");
                //2.通知浏览器以下载方式打开（下载的中文文件名必须要给过URL编码）
                response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8") + ".jpg");
                os = response.getOutputStream();
                // 1K的数据缓冲
                byte[] bs = new byte[1024];
                // 读取到的数据长度
                int len;
                // 开始读取
                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
                response.setHeader("status", String.valueOf(ConstantConfig.HTTP_CODE_1000));
            }else{
                response.setHeader("status", String.valueOf(ConstantConfig.HTTP_CODE_1010));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setHeader("status", String.valueOf(ConstantConfig.HTTP_CODE_1003));
        } finally {
            // 完毕，关闭所有链接
            try {
                if (is != null) is.close();
                if (os != null) os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
