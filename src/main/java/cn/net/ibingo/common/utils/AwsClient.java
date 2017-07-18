package cn.net.ibingo.common.utils;


import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.regions.ServiceAbbreviations;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

public class AwsClient {
    static AmazonS3 s3;
    static TransferManager tx;

    static {
        s3 = new AmazonS3Client(new BasicAWSCredentials(ConstantConfig.AWS_ACCESS_KEY, ConstantConfig.AWS_SECRET_KEY));
        Region usWest2 = Region.getRegion(Regions.AP_SOUTHEAST_1);
        s3.setRegion(usWest2);
        final String serviceEndpoint = usWest2.getServiceEndpoint(ServiceAbbreviations.S3);
        s3.setEndpoint(serviceEndpoint);
    }
    private static void listObjects(String bucketName) {
        ObjectListing objectListing = s3.listObjects(bucketName);
        for(S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey());
        }
    }

    /**
     * @param @param  tempFile 目标文件
     * @param @param  remoteFileName 文件名
     * @param @return
     * @param @throws IOException    设定文件
     * @return String    返回类型
     * @throws
     * @Title: uploadToS3
     * @Description: 将文件上传至S3上并且返回url
     */
    public static String uploadToS3(File tempFile, String remoteFileName) throws IOException {
        try {
            //上传文件
            s3.putObject(new PutObjectRequest(ConstantConfig.BUCKETNAME, remoteFileName, tempFile).withCannedAcl(CannedAccessControlList.PublicRead));
            //获取一个request
            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(
                    ConstantConfig.BUCKETNAME, remoteFileName);
            //生成公用的url
            URL url = s3.generatePresignedUrl(urlRequest);
            System.out.println("=========URL=================" + url + "============URL=============");
            return url.toString();
        } catch (AmazonServiceException ase) {
            ase.printStackTrace();
        } catch (AmazonClientException ace) {
            ace.printStackTrace();
        }
        return null;
    }

    /**
     * @param @param  remoteFileName
     * @param @throws IOException    设定文件
     * @return S3ObjectInputStream    返回类型  数据流
     * @throws
     * @Title: getContentFromS3
     * @Description: 获取文件2进制流
     */
    public static void getContentFromS3(String remoteFileName, HttpServletResponse response) throws IOException {
        OutputStream os = null;
        S3ObjectInputStream inputStream = null;
        try {
            GetObjectRequest request = new GetObjectRequest(ConstantConfig.BUCKETNAME, remoteFileName);
            S3Object object = s3.getObject(request);
            inputStream = object.getObjectContent();
            response.setCharacterEncoding("UTF-8");
            //2.通知浏览器以下载方式打开（下载的中文文件名必须要给过URL编码）
            response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode(remoteFileName, "UTF-8") + ".jpg");
            os = response.getOutputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 完毕，关闭所有链接
            try {
                if (inputStream != null) inputStream.close();
                if (os != null) os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * @param @param  remoteFileName 文件名
     * @param @param  path 下载的路径
     * @param @throws IOException    设定文件
     * @return void    返回类型
     * @throws
     * @Title: downFromS3
     * @Description: 将文件下载到本地路径
     */
    public static void downFromS3(String remoteFileName, String path) throws IOException {
        try {
            GetObjectRequest request = new GetObjectRequest(ConstantConfig.BUCKETNAME, remoteFileName);
            s3.getObject(request, new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param @param  remoteFileName 文件名
     * @param @return
     * @param @throws IOException    设定文件
     * @return String    返回类型
     * @throws
     * @Title: getUrlFromS3
     * @Description: 获取文件的url
     */
    public static String getUrlFromS3(String remoteFileName) throws IOException {
        try {
            GeneratePresignedUrlRequest httpRequest = new GeneratePresignedUrlRequest(ConstantConfig.BUCKETNAME, remoteFileName);
            String url = s3.generatePresignedUrl(httpRequest).toString();//临时链接
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证s3上是否存在名称为BUCKETNAME的Bucket
     *
     * @param s3
     * @param bucketName
     * @return
     */
    public static boolean checkBucketExists(AmazonS3 s3, String bucketName) {
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket bucket : buckets) {
            if (Objects.equals(bucket.getName(), bucketName)) {
                return true;
            }
        }
        return false;
    }

    public static void delFromS3(String remoteFileName) throws IOException {
        try {
            s3.deleteObject(ConstantConfig.BUCKETNAME, remoteFileName);
        } catch (AmazonServiceException ase) {
            ase.printStackTrace();
        } catch (AmazonClientException ace) {
            ace.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String key = "redisinfo";
        File tempFile = new File("C:\\Users\\guosen\\Desktop\\redis.txt");
        uploadToS3(tempFile, key);//上传文件
        String url = getUrlFromS3(key);//获取文件的url
        System.out.println(url);
//    	delFromS3(key);//删除文件
    }
}