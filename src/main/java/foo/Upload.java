package foo;

import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class Upload extends ActionSupport {
    private File upload;
    private String uploadFileName;
    private String uploadContentType;

    // Custom upload logic
    public String execute() throws Exception {
        if (uploadFileName != null) {
            try {
                // Specify the directory where files will be uploaded
                String uploadDirectory = System.getProperty("user.dir") + "/uploads/";

                // Create the destination file
                File destFile = new File(uploadDirectory, uploadFileName);

                // Copy the uploaded file to the destination
                FileUtils.copyFile(upload, destFile);

                // Add message to reflect the exact upload path on the frontend
                addActionMessage("File uploaded successfully to " + destFile.getAbsolutePath());

                return SUCCESS;
            } catch (Exception e) {
                addActionError(e.getMessage());
                e.printStackTrace();
                return ERROR;
            }
        } else {
            return INPUT;
        }
     }

    // Getters and setters
    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

}
