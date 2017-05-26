package com.web.dto;

import java.io.Serializable;

public class EditInfo implements Serializable {

	private static final long serialVersionUID = 1950279160538625338L;
	private int id;
	private String name;
	private String oldPwd;
	private String pwd;
	private String pwdRepeat;

	public EditInfo() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwdRepeat() {
		return pwdRepeat;
	}

	public void setPwdRepeat(String pwdRepeat) {
		this.pwdRepeat = pwdRepeat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((oldPwd == null) ? 0 : oldPwd.hashCode());
		result = prime * result + ((pwd == null) ? 0 : pwd.hashCode());
		result = prime * result + ((pwdRepeat == null) ? 0 : pwdRepeat.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		EditInfo other = (EditInfo) obj;
		if (id != other.id) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (oldPwd == null) {
			if (other.oldPwd != null) {
				return false;
			}
		} else if (!oldPwd.equals(other.oldPwd)) {
			return false;
		}
		if (pwd == null) {
			if (other.pwd != null) {
				return false;
			}
		} else if (!pwd.equals(other.pwd)) {
			return false;
		}
		if (pwdRepeat == null) {
			if (other.pwdRepeat != null) {
				return false;
			}
		} else if (!pwdRepeat.equals(other.pwdRepeat)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName());
		sb.append(":");
		sb.append(" User Id = < " + this.id);
		sb.append(" User Name = < " + this.name);
		sb.append(" >, Old Pwd = < " + this.oldPwd);
		sb.append(" >, Password = < " + this.pwd);
		sb.append(" >, Repeat Password = < " + this.pwdRepeat + " >");
		return sb.toString();
	}

}
