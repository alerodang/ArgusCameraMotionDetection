import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

class ImageService {

    private String directory;

    ImageService(String folder) {
        this.directory = folder;
    }

    void setFolder(){
        File file = new File(this.directory);
        boolean dirCreated = file.mkdir();
        System.out.println("Created directory: " + dirCreated);
    }

    void saveImage(Mat imageMatrix) {
        String date = new SimpleDateFormat("yyyyMMdd-HHmmssSSSS").format(new Date());
        Imgcodecs.imwrite(this.directory + "/" + date + ".jpg", imageMatrix);
    }
}
