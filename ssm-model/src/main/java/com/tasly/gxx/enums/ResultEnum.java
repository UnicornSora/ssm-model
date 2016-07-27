package com.tasly.gxx.enums;

/**
 * ҵ���쳣���࣬����ҵ���쳣������̳��ڴ��쳣 �����쳣ʱ����Ҫ��ȷ���쳣����ģ�顣 ���磺��Ч�û����Զ���Ϊ [10010001]
 * ǰ��λ��Ϊϵͳģ���ţ���4λΪ������� ,Ψһ��
 * 
 * @author yingjun
 *
 */
public enum ResultEnum {

	// ���ݿ�������쳣
	DB_INSERT_RESULT_ERROR(99990001, "db insert error"),
	DB_UPDATE_RESULT_ERROR(99990002, "db update error"),
	DB_SELECTONE_IS_NULL(99990003,"db select return null"),

	// ϵͳ�쳣
	INNER_ERROR(99980001, "ϵͳ����"), 
	TOKEN_IS_ILLICIT(99980002, "Token��֤�Ƿ�"), 
	SESSION_IS_OUT_TIME(99980003, "�Ự��ʱ"),

	// �û�����쳣
	INVALID_USER(1001001, "��Ч�û�");

	private int state;

	private String msg;

	ResultEnum(int state, String msg) {
		this.state = state;
		this.msg = msg;
	}

	public int getState() {
		return state;
	}

	public String getMsg() {
		return msg;
	}

	public static ResultEnum stateOf(int index) {
		for (ResultEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
