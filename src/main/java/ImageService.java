import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;

class ImageService {

    void setFolder(String path){
        File file = new File(path);
        boolean dirCreated = file.mkdir();
        System.out.println("Created directory: " + dirCreated);
    }

    void saveImage(Mat imageMatrix, String path) {
        Imgcodecs.imwrite(path, imageMatrix);
    }
}
