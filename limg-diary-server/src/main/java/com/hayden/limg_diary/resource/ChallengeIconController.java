package com.hayden.limg_diary.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/res/challengeicon")
public class ChallengeIconController {
    @Value("${path.res.static.challenge_icon_dir}")
    String iconDirPath;

    @GetMapping("/{challengeId}")
    public Resource getChallengeIcon(@PathVariable int challengeId) throws MalformedURLException {
        return new UrlResource(String.format("file:%s/%d", iconDirPath, challengeId));
    }

}
