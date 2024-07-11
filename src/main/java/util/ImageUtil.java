package main.java.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageUtil {

	// (Adding)
	 public static byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        int length;
	        
	        while ((length = inputStream.read(buffer)) != -1) {
	            byteArrayOutputStream.write(buffer, 0, length);
	        }
	        
	        return byteArrayOutputStream.toByteArray();
	    }
    
	 // Convert byte array into file. (Editing)
    public File convertByteArrayToFile(byte[] byteArray) throws FileNotFoundException, IOException {
    	 // Create a temporary file
        Path tempFilePath = Files.createTempFile(null, null);
        File tempFile = tempFilePath.toFile();
        
        // Write byte array to the temporary file
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(byteArray);
        }
        
        return tempFile;
    }
    
    // Used in editing. before uploading this will convert the selected file into bytes.
    public  byte[] convertFileToBytes(File file) throws IOException {
        byte[] bytesArray = new byte[(int) file.length()];

        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray); // read file into bytes[]
        fis.close();

        return bytesArray;
    }

    // For image resizing
    public static Image resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return resultingImage;
    }

    //Opens the file chooser to upload Image. used for adding and editing.
    public File imageUploadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
        int result = fileChooser.showOpenDialog(null); // Changed to null to work independently
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public static BufferedImage convertByteArrayToBufferedImage(byte[] imageData) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
        return ImageIO.read(bis);
    }
    
    public  BufferedImage convertFileToBufferedImage(File file) throws IOException {
        return ImageIO.read(file);
    }
}
