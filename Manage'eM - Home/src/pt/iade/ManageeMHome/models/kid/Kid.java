package pt.iade.ManageeMHome.models.kid;

import pt.iade.ManageeMHome.Person;

public class Kid extends Person{

	private int points;
	
	public Kid(int age, String name/*, int id, String username, String password*/) {
		super(age, name/* id, username, password*/);
		this.name = name;
		this.age = age;
	}
	public int getAge() {
		return age;
	}
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "("+age+")";
	}
	
}
