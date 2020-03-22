import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.util.ArrayList;
import java.util.List;

class CameraService {

    private VideoCapture camera = new VideoCapture();
    private int count = 0;

    Mat getGrayScaleFrame(Mat frame) {
        Mat greyScaleFrame = new Mat();
        Imgproc.cvtColor(frame, greyScaleFrame, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(greyScaleFrame, greyScaleFrame, new Size(21, 21), 0);
        return greyScaleFrame;
    }

    void openCamera(int width, int heigth) throws InterruptedException {
        camera.open(0); //open camera
        camera.set(3, width);
        camera.set(4, heigth);
        Thread.sleep(3000);
    }

    Mat getFrame() {
        Mat frame = new Mat();
        camera.read(frame);
        return frame;
    }

    private Mat getFramesDifference(Mat frameGrayScale, Mat newFrameGrayScale) {
        Mat framesDifference = new Mat();
        Core.absdiff(frameGrayScale, newFrameGrayScale, framesDifference);
        return framesDifference;
    }

    private List<MatOfPoint> getContours(Mat framesDifference) {
        Mat threshold = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();

        Imgproc.threshold(framesDifference, threshold, 25, 255, Imgproc.THRESH_BINARY);
        Imgproc.dilate(threshold, threshold, new Mat(), new Point(-1, -1), 2);
        Imgproc.findContours(threshold, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        return contours;
    }

    boolean detectMovement(Mat frameGrayScale, Mat newFrameGrayScale, int limit) {
        Mat framesDifference = this.getFramesDifference(frameGrayScale, newFrameGrayScale);
        List<MatOfPoint> framesDifferenceContours = this.getContours(framesDifference);

        for (MatOfPoint framesDifferenceContour : framesDifferenceContours) {
            if (Imgproc.contourArea(framesDifferenceContour) > limit) {
                System.out.println(count++);
                return true;
            }
        }
        return false;
    }

}
