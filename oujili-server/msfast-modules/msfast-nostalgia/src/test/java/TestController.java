import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import com.wxmblog.MsfastNostalgiaApplication;
import com.wxmblog.base.auth.service.MsfConfigService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-10 16:22
 **/

@SpringBootTest(classes = MsfastNostalgiaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestController {

    @Autowired
    MsfConfigService msfConfigService;

    @Test
    public void test() throws Exception {

        String data = StreamUtils.copyToString(new FileInputStream(""), StandardCharsets.UTF_8);

        String[] split = data.split("\n");
        System.out.println(split.length);

        StringBuilder sb = new StringBuilder();
        for (String item : split) {
            String[] split1 = item.split(",");
            sb.append("INSERT INTO `msfast_nostalgia`.`university` (`creator`, `create_time`, `modifyer`, `modify_time`, `del_flag`, `version`, `name`) VALUES (NULL, '2022-12-20 16:06:30', NULL, NULL, 0, 0, '"+split1[0]+"')").append(";\n");
        }

        StreamUtils.copy(sb.toString().getBytes(), new FileOutputStream("C:\\Users\\wanglei\\Desktop\\university.sql"));

    }

    @Test
    public void reader() throws Exception
    {

        CsvReader reader = CsvUtil.getReader();
        //从文件中读取CSV数据
        CsvData data = reader.read(new File("C:\\Users\\wanglei\\Desktop\\全国大学名单.csv"), Charset.forName("UTF-8"));
        List<CsvRow> rows = data.getRows();
        //遍历行
        StringBuilder sb = new StringBuilder();
        for (CsvRow csvRow : rows) {
            System.out.print("`"+csvRow.get(0)+",");
            sb.append("INSERT INTO `msfast_nostalgia`.`university` (`creator`, `create_time`, `modifyer`, `modify_time`, `del_flag`, `version`, `name`) VALUES (NULL, '2022-12-20 16:06:30', NULL, NULL, 0, 0, '"+csvRow.get(0)+"')").append(";\n");

        }
        StreamUtils.copy(sb.toString().getBytes(), new FileOutputStream("C:\\Users\\wanglei\\Desktop\\university.sql"));
    }
}
