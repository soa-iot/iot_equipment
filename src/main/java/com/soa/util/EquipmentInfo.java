package com.soa.util;

public class EquipmentInfo {
	private String EQU_ID;
	private String WEL_NAME;
	private String EQU_POSITION_NUM;
	private String EQU_NAME;
	private String EQU_MODEL;
	private String EQU_INSTALL_POSITION;
	private String EQU_MANUFACTURER;
	private String MEARING_RANGE;
	private String SERIAL_NUM;
	private String PRESSURE_RANGE;
	private String EQU_WORK_TEMP;
	private String FLA_SIZE;
	private String MANAGE_TYPE;
	private String EXPERY_TIME;
	private String CHECK_CYCLE;
	private String CHECK_TIME;
	private String NEXT_CHECK_TIME;
	private String EQU_PRODUC_DATE;
	private String EQU_COMMISSION_DATE;
	private String EQU_MEMO_ONE;
	private String MEASURE_ACC;
	private String REMARK1;
	private String EQUIPMENT_ATTACH_URL;
	public String getEQU_ID() {
		return EQU_ID;
	}
	public String getEQU_MEMO_ONE() {
		return EQU_MEMO_ONE;
	}
	public void setEQU_MEMO_ONE(String eQU_MEMO_ONE) {
		EQU_MEMO_ONE = eQU_MEMO_ONE;
	}
	public String getMEASURE_ACC() {
		return MEASURE_ACC;
	}
	public void setMEASURE_ACC(String mEASURE_ACC) {
		MEASURE_ACC = mEASURE_ACC;
	}
	public String getREMARK1() {
		return REMARK1;
	}
	public void setREMARK1(String rEMARK1) {
		REMARK1 = rEMARK1;
	}
	public String getEQUIPMENT_ATTACH_URL() {
		return EQUIPMENT_ATTACH_URL;
	}
	public void setEQUIPMENT_ATTACH_URL(String eQUIPMENT_ATTACH_URL) {
		EQUIPMENT_ATTACH_URL = eQUIPMENT_ATTACH_URL;
	}
	public void setEQU_ID(String eQU_ID) {
		EQU_ID = eQU_ID;
	}
	public String getWEL_NAME() {
		return WEL_NAME;
	}
	public void setWEL_NAME(String wEL_NAME) {
		WEL_NAME = wEL_NAME;
	}
	public String getEQU_POSITION_NUM() {
		return EQU_POSITION_NUM;
	}
	public void setEQU_POSITION_NUM(String eQU_POSITION_NUM) {
		EQU_POSITION_NUM = eQU_POSITION_NUM;
	}
	public String getEQU_NAME() {
		return EQU_NAME;
	}
	public void setEQU_NAME(String eQU_NAME) {
		EQU_NAME = eQU_NAME;
	}
	public String getEQU_MODEL() {
		return EQU_MODEL;
	}
	public void setEQU_MODEL(String eQU_MODEL) {
		EQU_MODEL = eQU_MODEL;
	}
	public String getEQU_INSTALL_POSITION() {
		return EQU_INSTALL_POSITION;
	}
	public void setEQU_INSTALL_POSITION(String eQU_INSTALL_POSITION) {
		EQU_INSTALL_POSITION = eQU_INSTALL_POSITION;
	}
	public String getEQU_MANUFACTURER() {
		return EQU_MANUFACTURER;
	}
	public void setEQU_MANUFACTURER(String eQU_MANUFACTURER) {
		EQU_MANUFACTURER = eQU_MANUFACTURER;
	}
	public String getMEARING_RANGE() {
		return MEARING_RANGE;
	}
	public void setMEARING_RANGE(String mEARING_RANGE) {
		MEARING_RANGE = mEARING_RANGE;
	}
	public String getSERIAL_NUM() {
		return SERIAL_NUM;
	}
	public void setSERIAL_NUM(String sERIAL_NUM) {
		SERIAL_NUM = sERIAL_NUM;
	}
	public String getPRESSURE_RANGE() {
		return PRESSURE_RANGE;
	}
	public void setPRESSURE_RANGE(String pRESSURE_RANGE) {
		PRESSURE_RANGE = pRESSURE_RANGE;
	}
	public String getEQU_WORK_TEMP() {
		return EQU_WORK_TEMP;
	}
	public void setEQU_WORK_TEMP(String eQU_WORK_TEMP) {
		EQU_WORK_TEMP = eQU_WORK_TEMP;
	}
	public String getFLA_SIZE() {
		return FLA_SIZE;
	}
	public void setFLA_SIZE(String fLA_SIZE) {
		FLA_SIZE = fLA_SIZE;
	}
	public String getMANAGE_TYPE() {
		return MANAGE_TYPE;
	}
	public void setMANAGE_TYPE(String mANAGE_TYPE) {
		MANAGE_TYPE = mANAGE_TYPE;
	}
	public String getEXPERY_TIME() {
		return EXPERY_TIME;
	}
	public void setEXPERY_TIME(String eXPERY_TIME) {
		EXPERY_TIME = eXPERY_TIME;
	}
	public String getCHECK_CYCLE() {
		return CHECK_CYCLE;
	}
	public void setCHECK_CYCLE(String cHECK_CYCLE) {
		CHECK_CYCLE = cHECK_CYCLE;
	}
	public String getCHECK_TIME() {
		return CHECK_TIME;
	}
	public void setCHECK_TIME(String cHECK_TIME) {
		CHECK_TIME = cHECK_TIME;
	}
	public String getNEXT_CHECK_TIME() {
		return NEXT_CHECK_TIME;
	}
	public void setNEXT_CHECK_TIME(String nEXT_CHECK_TIME) {
		NEXT_CHECK_TIME = nEXT_CHECK_TIME;
	}
	public String getEQU_PRODUC_DATE() {
		return EQU_PRODUC_DATE;
	}
	public void setEQU_PRODUC_DATE(String eQU_PRODUC_DATE) {
		EQU_PRODUC_DATE = eQU_PRODUC_DATE;
	}
	public String getEQU_COMMISSION_DATE() {
		return EQU_COMMISSION_DATE;
	}
	public void setEQU_COMMISSION_DATE(String eQU_COMMISSION_DATE) {
		EQU_COMMISSION_DATE = eQU_COMMISSION_DATE;
	}
	@Override
	public String toString() {
		return "EquipmentInfo [EQU_ID=" + EQU_ID + ", WEL_NAME=" + WEL_NAME + ", EQU_POSITION_NUM=" + EQU_POSITION_NUM
				+ ", EQU_NAME=" + EQU_NAME + ", EQU_MODEL=" + EQU_MODEL + ", EQU_INSTALL_POSITION="
				+ EQU_INSTALL_POSITION + ", EQU_MANUFACTURER=" + EQU_MANUFACTURER + ", MEARING_RANGE=" + MEARING_RANGE
				+ ", SERIAL_NUM=" + SERIAL_NUM + ", PRESSURE_RANGE=" + PRESSURE_RANGE + ", EQU_WORK_TEMP="
				+ EQU_WORK_TEMP + ", FLA_SIZE=" + FLA_SIZE + ", MANAGE_TYPE=" + MANAGE_TYPE + ", EXPERY_TIME="
				+ EXPERY_TIME + ", CHECK_CYCLE=" + CHECK_CYCLE + ", CHECK_TIME=" + CHECK_TIME + ", NEXT_CHECK_TIME="
				+ NEXT_CHECK_TIME + ", EQU_PRODUC_DATE=" + EQU_PRODUC_DATE + ", EQU_COMMISSION_DATE="
				+ EQU_COMMISSION_DATE + ", EQU_MEMO_ONE=" + EQU_MEMO_ONE + ", MEASURE_ACC=" + MEASURE_ACC + ", REMARK1="
				+ REMARK1 + ", EQUIPMENT_ATTACH_URL=" + EQUIPMENT_ATTACH_URL + "]";
	}

}
