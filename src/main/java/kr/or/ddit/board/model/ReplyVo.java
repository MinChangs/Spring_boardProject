package kr.or.ddit.board.model;

import java.util.Date;

public class ReplyVo {

	private int reply_seq;
	private int post_seq;
	private String reply_content;
	private Date reply_dt;
	private String userid;
	private String use_yn;
	
	
	public int getReply_seq() {
		return reply_seq;
	}
	public void setReply_seq(int reply_seq) {
		this.reply_seq = reply_seq;
	}
	public int getPost_seq() {
		return post_seq;
	}
	public void setPost_seq(int post_seq) {
		this.post_seq = post_seq;
	}
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	public Date getReply_dt() {
		return reply_dt;
	}
	public void setReply_dt(Date reply_dt) {
		this.reply_dt = reply_dt;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	
	

}
