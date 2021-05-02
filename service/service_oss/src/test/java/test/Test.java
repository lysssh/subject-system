package test;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.liye.oss.utils.ConstandPropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 * @author ly
 * @project_name subject-system
 * @package_name test
 * @create_time 2021/4/24 22:06
 */

@Slf4j
public class Test {

    @org.junit.Test
    public void test1() {
        String a ="https://edu-gulioarent.oss-cn-beijing.aliyuncs.com/";
        System.out.println(a.length());
        String name = "https://edu-gulioarent.oss-cn-beijing.aliyuncs.com/2021/04/13/a0a52bb987574f3196d978d8414cb773file.png";
        System.out.println(name.substring(a.length()));
        String[] split = name.split(".com/");
        for (int i=0;i<split.length;i++) {
            System.out.println(split[i]);
        }

        System.out.println(split[split.length-1]);
    }

    @org.junit.Test
    public void deleteFile() {
        String name = "2021/04/13/5d7a5258522d412bb142177ceed4fd7c1618109764212.jpg";
        OSS ossClient = null;
        try {
            // Endpoint以杭州为例，其它Region请按实际情况填写。
            String endpoint = "oss-cn-beijing.aliyuncs.com";
            // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
            String accessKeyId = "LTAI4G2Ef8mvAuLLaHqhmuyC";
            String accessKeySecret = "FovwHOANrKGUuJzHmY6b1k5inRNKDh";
            String bucketName = "edu-gulioarent";

            // 创建OSSClient实例。
            ossClient= new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            boolean b = ossClient.doesObjectExist(bucketName, name);
            if(!b) {
                log.error("文件不存在");
            }else {
                // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
                ossClient.deleteObject(bucketName,name);
                log.info("删除成功");
            }

        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            if(ossClient!=null) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
    }
}
