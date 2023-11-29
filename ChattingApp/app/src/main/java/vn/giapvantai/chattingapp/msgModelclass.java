package vn.giapvantai.chattingapp;

public class msgModelclass {
    String message;
    String senderid;
    long timeStamp;
    String fileUrl; // URL của file

    public msgModelclass() {
    }

    public msgModelclass(String message, String senderid, long timeStamp, String fileUrl) {
        this.message = message;
        this.senderid = senderid;
        this.timeStamp = timeStamp;
        this.fileUrl = fileUrl; // Khởi tạo URL của file
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getFileUrl() {
        return fileUrl; // Getter cho URL của file
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl; // Setter cho URL của file
    }
}
