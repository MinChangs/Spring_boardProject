package kr.or.ddit.board.model;

public class AttachmentVo {
	
	private int attachment_seq;
	private int post_seq;
	private String path;
	private String filename;
	public int getAttachment_seq() {
		return attachment_seq;
	}
	public void setAttachment_seq(int attachment_seq) {
		this.attachment_seq = attachment_seq;
	}
	public int getPost_seq() {
		return post_seq;
	}
	public void setPost_seq(int post_seq) {
		this.post_seq = post_seq;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}

}
