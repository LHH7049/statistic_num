package com.lz.demo;

import com.lz.demo.service.StatisticAlgorithmService;
import com.lz.demo.util.ExcelUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    List<List<String>> readFile() throws Exception {
        String path = "F:/statistic.xlsx";
        File file = new File(path);
        List<List<String>> result = ExcelUtil.INSTANCE.read(file, 0);

        return result;
    }

    @Test
    void statistics() throws Exception {
        int n = 80;
        long start = System.currentTimeMillis();
        List<List<String>> inputData = readFile();
        List<int[]> result = new ArrayList<>();
        for (List<String> readDatum : inputData) {
            int[] item = new int[n + 1];
            for (String s : readDatum) {
                if (StringUtils.hasText(s)){
                    item[Integer.parseInt(s)] ++ ;
                }
            }
            result.add(item);
        }

        List<Integer> keys = new ArrayList<>();
        keys.add(5);


//        StatisticAlgorithmService service = new StatisticAlgorithmService(result, keys);
//        for (int[] items : result) {
//            service.run(items, new ArrayList<>());
//        }
//        Map<String, Integer> recordMap = service.getRecordMap();
//        System.out.println(recordMap.size());

        // 解析数据
//        Map<String, Integer> resultMap = new StatisticAlgorithmService(result, keys).run();
//        String key = "5,19,20";
//        System.out.println("cost time:" + (System.currentTimeMillis() - start) + "ms");
//        System.out.println(String.format("key:%s --- value:%s",key , resultMap.get(key)));
    }
}
