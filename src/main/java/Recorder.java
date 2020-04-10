import com.google.gson.Gson;
import org.opencv.core.Mat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

class Recorder {

    private CameraService cameraService;
    private ImageService imageService;
    private RabbitmqService rabbitmqService;
    private String path;
    private String mqHost;

    Recorder(String path, String mqHost) {
        this.cameraService = new CameraService();
        this.imageService = new ImageService();
        this.rabbitmqService = new RabbitmqService();
        this.path = path;
        this.mqHost = mqHost;
    }

    void start() throws Exception {
        imageService.setFolder(path);
        cameraService.openCamera(1920, 1080);

        Mat previousGrayFrame = cameraService.getGrayScaleFrame(cameraService.getFrame());
        Mat newFrame;
        Mat newFrameGrayScale;

        while (true) {
            Gson gson = new Gson();
            newFrame = cameraService.getFrame();
            newFrameGrayScale = cameraService.getGrayScaleFrame(newFrame);

            if (cameraService.detectMovement(previousGrayFrame, newFrameGrayScale, 5000)) {
                System.out.println("Motion detected!!!");

                String date = new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date());
                String path = this.path + "/" + date + ".jpg";
                Map <String, String> data = new HashMap<>();
                data.put("path", path);
                System.out.println("Data: " + data.toString());

                this.imageService.saveImage(newFrame, path);

                this.rabbitmqService.publish(gson.toJson(data), "captured-image-queue", mqHost);
            }

            previousGrayFrame = newFrameGrayScale;

            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
