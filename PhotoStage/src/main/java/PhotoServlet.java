import javax.servlet.http.HttpServlet;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

@WebServlet("/PhotoServlet")
public class PhotoServlet extends HttpServlet {

    private CascadeClassifier faceDetector;

    @Override
    public void init() throws ServletException {
        super.init();
        // Load the OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // Load the face detection classifier
        String xmlPath = getServletContext().getRealPath("/WEB-INF/lib/haarcascade_frontalface_alt.xml");
        faceDetector = new CascadeClassifier(xmlPath);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageData = request.getParameter("image");
        String message = "";

        if (imageData != null && !imageData.isEmpty()) {
            byte[] imageBytes = Base64.getDecoder().decode(imageData.split(",")[1]);

            // Convert the byte array to a Mat object
            Mat image = Imgcodecs.imdecode(new MatOfByte(imageBytes), Imgcodecs.IMREAD_COLOR);

            // Detect faces in the image
            MatOfRect faceDetections = new MatOfRect();
            faceDetector.detectMultiScale(image, faceDetections);

            // Get the number of detected faces
            Rect[] facesArray = faceDetections.toArray();
            int numFaces = facesArray.length;

            if (numFaces == 0) {
                message = "No human face detected in the uploaded image.";
                response.setContentType("text/plain");
                response.getWriter().write(message);
            } else if (numFaces > 1) {
                message = "Multiple faces detected. Please upload an image with only one face.";
                response.setContentType("text/plain");
                response.getWriter().write(message);
            } else {
                // Proceed with cropping if only one face is detected
                Rect face = facesArray[0];
                Mat croppedFace = new Mat(image, face);
                // Resize the cropped face
                Mat resizedFace = new Mat();
                Size size = new Size(100, 150); // Adjust the size to fit your requirements
                Imgproc.resize(croppedFace, resizedFace, size);

                // Save the processed image
                File outputFile = new File(getServletContext().getRealPath("/uploads/photo.png"));
                Imgcodecs.imwrite(outputFile.getAbsolutePath(), resizedFace);

                // Encode the image back to base64 for display
                MatOfByte matOfByte = new MatOfByte();
                Imgcodecs.imencode(".png", resizedFace, matOfByte);
                byte[] byteArray = matOfByte.toArray();
                String encodedImage = Base64.getEncoder().encodeToString(byteArray);

                response.setContentType("text/plain");
                response.getWriter().write("data:image/png;base64," + encodedImage);
            }
        } else {
            message = "Image data is missing.";
            response.setContentType("text/plain");
            response.getWriter().write(message);
        }
    }
}
