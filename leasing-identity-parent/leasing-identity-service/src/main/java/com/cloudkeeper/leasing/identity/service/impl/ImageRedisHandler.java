package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.identity.domain.ParActivityPicture;
import com.cloudkeeper.leasing.identity.domain.ParPictureInfro;
import com.cloudkeeper.leasing.identity.dto.paractivitypicture.ParActivityPictureSearchable;
import com.cloudkeeper.leasing.identity.dto.parpictureinfro.ParPictureInfroSearchable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ImageRedisHandler extends Thread {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private FdfsServiceImpl fdfsService;

    @Autowired
    private ParActivityPictureServiceImpl parActivityPictureService;

    @Autowired
    private ParPictureInfroServiceImpl parPictureInfroService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String REDIS_IMG_URL = "http://122.97.218.162:21018/api/identity/accessory/getImage/";


    @Override
    public void run() {
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        while (true) {
            List<String> imgKeys = redisTemplate.opsForList().range("IMGKEYS", 0, 20);
            for (String item : imgKeys) {
                String base64 = (String) redisTemplate.opsForValue().get(item);
                MultipartFile multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(base64);

                try {
                    item = REDIS_IMG_URL + item;
                    ParActivityPictureSearchable parActivityPictureSearchable = new ParActivityPictureSearchable();
                    parActivityPictureSearchable.setImageUrl(item);
                    List<ParActivityPicture> all = parActivityPictureService.findAll(parActivityPictureSearchable);
                    if (all.size() == 1) {
                        ParActivityPicture parActivityPicture = all.get(0);
                        String path = fdfsService.uploadFile(multipartFile);
                        parActivityPicture.setImageUrl(path);
                        parActivityPicture.setRedisUuid(item);
                        parActivityPictureService.save(parActivityPicture);
                        redisTemplate.opsForList().leftPop("IMGKEYS");
                        return;
                    }
                    ParPictureInfroSearchable parPictureInfroSearchable = new ParPictureInfroSearchable();
                    parPictureInfroSearchable.setImageURL(item);
                    List<ParPictureInfro> all1 = parPictureInfroService.findAll(parPictureInfroSearchable);
                    if (all1.size() == 1) {
                        ParPictureInfro parPictureInfro = all1.get(0);
                        String path = fdfsService.uploadFile(multipartFile);
                        parPictureInfro.setImageURL(path);
                        parPictureInfro.setRedisUuid(item);
                        parPictureInfroService.save(parPictureInfro);
                        redisTemplate.opsForList().leftPop("IMGKEYS");
                    }
                } catch (Exception e) {
                    logger.warn("图片保存失败，图片丢失");
                    e.printStackTrace();
                }
            }
        }
    }

}
