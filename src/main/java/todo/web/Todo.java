package todo.web;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Todo implements Serializable {

	private int id;
	private String title;
	private String task;
	private Timestamp limitdate;
	private Timestamp lastupdate;
	private String userid;
	private int status;
	private String label;
	private String inputLimitdate;
	private List<String> errorMessages;

	//アクセッサ
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Timestamp getLimitdate() {
		return limitdate;
	}

	public void setLimitdate(Timestamp limitdate) {
		this.limitdate = limitdate;
	}

	public Timestamp getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Timestamp lastupdate) {
		this.lastupdate = lastupdate;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getInputLimitdate() {
		return inputLimitdate;
	}

	public void setInputLimitdate(String inputLimitdate) {
		this.inputLimitdate = inputLimitdate;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	// 入力された値のチェックを行うメソッド
	public boolean valueCheck() {
		errorMessages = new ArrayList<String>();

		if (id < 0) {
			errorMessages.add("不正なタスク番号を検出しました。");
		}

		if (title == null || title.isEmpty()) {
			errorMessages.add("入力したタイトルが空です。");
		} else if (title.length() > 256) {
			errorMessages.add("入力したタイトルが長すぎます。");
		}

		if (task == null || task.isEmpty()) {
			errorMessages.add("入力したタスクが空です。");
		} else if (task.length() > 512) {
			errorMessages.add("入力したタスクが長すぎます。");
		}

		if (inputLimitdate == null || inputLimitdate.isEmpty()) {
			errorMessages.add("入力したタスク期限が空です。");
		} else if (!inputLimitdate.matches("\\d{4}-\\d{2}-\\d{2}")) {
			errorMessages.add("入力したタスクが期限のフォーマットが違います。");
		}

		if (userid == null || userid.isEmpty()) {
			errorMessages.add("入力したユーザーIDが空です。");
		} else if (userid.length() > 64) {
			errorMessages.add("入力したユーザーIDが長すぎます。");
		}

		if (status < 0 || status > 3) {
			errorMessages.add("入力したタスク状況の値が不正です。");
		}

		return (errorMessages.size() == 0);
	}
}
