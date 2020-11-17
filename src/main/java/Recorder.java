import com.google.gson.Gson;
import org.opencv.core.Mat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

class Recorder {

    private final int movementSensibility;
    private final int millisecondsBetweenCaptures;
    private final CameraService cameraService;
    private final ImageService imageService;
    private final RabbitmqService rabbitmqService;
    private final String path;
    private final String mqHost;

    Recorder(String path, String mqHost, int movementSensibility, int millisecondsBetweenCaptures) {
        this.cameraService = new CameraService();
        this.imageService = new ImageService();
        this.rabbitmqService = new RabbitmqService();
        this.path = path;
        this.mqHost = mqHost;
        this.movementSensibility =  movementSensibility; //5000
        this.millisecondsBetweenCaptures = millisecondsBetweenCaptures;
    }

    void start() throws Exception {
        imageService.setFolder(path);
        cameraService.openCamera(1920, 1080);

        Mat previousGrayFrame = cameraService.getGrayScaleFrame(cameraService.getFrame());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        Gson gson = new Gson();

        while (true) {
            Mat newFrame = cameraService.getFrame();
            Mat newFrameGrayScale = cameraService.getGrayScaleFrame(newFrame);

            if (cameraService.detectMovement(previousGrayFrame, newFrameGrayScale, this.movementSensibility)) {
                System.out.println("Motion detected!!!");

                String date = simpleDateFormat.format(new Date());
                String path = this.path + "/" + date + ".jpg";
                Map <String, String> data = new HashMap<>();
                data.put("path", path);
                System.out.println("Data: " + data.toString());

                this.imageService.saveImage(newFrame, path);

                this.rabbitmqService.publish(gson.toJson(data), "captured-image-queue", mqHost);
            }

            previousGrayFrame = newFrameGrayScale;

            TimeUnit.MILLISECONDS.sleep(this.millisecondsBetweenCaptures);
        }
    }
}
