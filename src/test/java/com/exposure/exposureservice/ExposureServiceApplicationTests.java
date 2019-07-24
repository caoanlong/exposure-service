package com.exposure.exposureservice;

import com.exposure.exposureservice.entity.Thing;
import com.exposure.exposureservice.repository.ThingRepostory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExposureServiceApplicationTests {

//    @Autowired
//    private ThingRepostory thingRepostory;

    @Test
    public void contextLoads() {
    }

    @Test
    public void updateBatch() {
        String title = null;
//        List<Thing> all = thingRepostory.findAll(title);
//        for (int i = 0; i < all.size(); i++) {
//            all.get(i).setCreateType(1);
//        }
//        thingRepostory.updateBatch(all);
    }
}
