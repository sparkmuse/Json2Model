package com.AL.Json2Model.General;

public class DataType {

	private String name;
	private String type;
	private boolean object;

	public DataType(String name, String type, boolean object) {
		super();
		this.name = name;
		this.type = type;
		this.object = object;
	}

	/**
	 * @return the object
	 */
	public boolean isObject() {
		return object;
	}

	/**
	 * @param object
	 *            the object to set
	 */
	public void setObject(boolean object) {
		this.object = object;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return type;
	}

	public void load() {

	}

}
