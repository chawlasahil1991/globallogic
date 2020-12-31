package com.globallogic.model;

public class Presenter {
	private String name;
	private int hours;
	private int fees;
	
	public Presenter(String name, int hours, int fees) {
		super();
		this.name = name;
		this.hours = hours;
		this.fees = fees;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getFees() {
		return fees;
	}
	public void setFees(int fees) {
		this.fees = fees;
	}
	@Override
	public String toString() {
		return "Presenter [name=" + name + ", hours=" + hours + ", fees=" + fees + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fees;
		result = prime * result + hours;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Presenter other = (Presenter) obj;
		if (fees != other.fees)
			return false;
		if (hours != other.hours)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
