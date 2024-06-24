package org.hit.hitcommunitybackend;

import com.qcloud.cos.COS;
import org.hit.hitcommunitybackend.utility.COSUtility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class COSTest {
    @Autowired
    private COSUtility cos;

    @Test
    public void cosTest(){
        byte[] bytes = cos.getData("/avatars/46.jpg");
        System.out.println("图片的大小是");
        System.out.println(bytes.length);
    }
}
