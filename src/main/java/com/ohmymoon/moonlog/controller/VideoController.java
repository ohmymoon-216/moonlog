package com.ohmymoon.moonlog.controller;

import com.github.kokorin.jaffree.StreamType;
import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.PipeOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.concurrent.TimeUnit;

@RequestMapping("/video")
@RestController
@Slf4j
public class VideoController {


    @GetMapping("/live.mp4")
    public ResponseEntity<StreamingResponseBody> livestream() {

        String rtspUrl = "rtsp://test:test1234@192.168.0.9:554/ISAPI/streaming/channels/101";

        StreamingResponseBody streamingResponseBody = os -> {
            FFmpeg.atPath()
                    .addArgument("-re")
                    .addArguments("-acodec", "pcm_s16le")
                    .addArguments("-rtsp_transport", "tcp")
                    .addArguments("-i", rtspUrl)
                    .addArguments("-vcodec", "copy")
                    .addArguments("-af", "asetrate=22050")
                    .addArguments("-acodec", "aac")
                    .addArguments("-b:a", "96k")
                    .addOutput(PipeOutput.pumpTo(os)
                            .disableStream(StreamType.AUDIO)
                            .disableStream(StreamType.SUBTITLE)
                            .disableStream(StreamType.DATA)
                            .setFrameCount(StreamType.VIDEO, 100L)
                            //1 frame every 10 seconds
                            .setFrameRate(0.1)
                            .setDuration(1, TimeUnit.HOURS)
                            .setFormat("ismv"))
                    .addArgument("-nostdin")
                    .execute();
        };

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "video/mp4");


        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(streamingResponseBody);
    }
}
