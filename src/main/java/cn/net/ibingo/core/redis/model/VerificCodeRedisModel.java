package cn.net.ibingo.core.redis.model;

public class VerificCodeRedisModel {

	private String username;

	private String ramNum;

    public String getKey() {
        return "ramNum_"+username;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRamNum() {
		return ramNum;
	}

	public void setRamNum(String ramNum) {
		this.ramNum = ramNum;
	}
}