# Json2Model

##About

Json2Model (aka Broccoli) is a small little tool to help to automate the creation of Models from JSON files.

##Goals
- Ability to create Model classes from the files.
- Nested classes will be created as well.
- Multi-language support (programing language anyway).
- Data type deduction.

##Install
- Download or clone repository.
- Navigate to folder ````/Json2Model/bin/````
- Run the ````j2m.sh```` from the command line.

Note: Make sure that the file has permission to run in your computer.

##Documentation 
- Navigate to he cloned/downloaded repository
- Go to folder ````Json2Model/doc````
- Open the file ````/overview-summary.html```` to read the documentation
- Command line documentation.
````
usage: j2m [-f <FILE>] [-h] [-lang <LANG>] [-o <DIR>]
A very small tool for people to get things done fast! :)

 -f,--file <FILE>          The file to be processed.
 -h,--help                 Help
 -lang,--language <LANG>   The language for the file output.
                           Allowed languages:
                           [C, OBJECTIVE_C, JAVA, SWIFT, VISUAL_BASIC,
                           VBA, UNKNOWN]
 -o,--out <DIR>            The directory where to place the processed
                           files. If ommited the same DIR as 'file' is
                           used
Enjoy
````


##User Guides
I want to get a Model 'Box' file from Box.json file to follow MVC patters.

Box.json contains:
````json
{
	"customer":{
		"firstName": "John",
		"lastName": "Doe"
	},
	"addresses": [
		{
			"street": "Faraway Creek",
			"number": 1234,
			"city":	"Never-land"
		}
	],
	"balance": 23234.23,
	"active": true
}
````

- Just run this to test.
````
./j2m.sh -f "myDirectory/Box.json" -lang=java -o "/myDirectory/target/"
````

- New files was created in the specified directory:

````java
public class Input {

	private ArrayList<Address> addresses;
	private double balance;
	private boolean active;
	private Customer customer;

	public Input(ArrayList<Address> addresses, double balance, boolean active, Customer customer) {
		super();
		this.addresses = addresses;
		this.balance = balance;
		this.active = active;
		this.customer = customer;
	}

	public void load() {
		//TODO: Needs to be implemented.
	}

	public ArrayList<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(ArrayList<Address> addresses) {
		this.addresses = addresses;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}

public class Customer {

	private String firstName;
	private String lastName;

	public Customer(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}

public class Address {

	private int number;
	private String city;
	private String street;

	public Address(int number, String city, String street) {
		super();
		this.number = number;
		this.city = city;
		this.street = street;
	}

	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
}
````

##Report a Bug or Contribute

- Navigate to the bug section tab on GitHub.
- Create a new Issue. If you are not sure how to create a new issue follow this link for instructions [Creating an issue](https://help.github.com/articles/creating-an-issue/)

- For new language support include sample of the class/structure in the body of the issue. Use this JSON text:

````json
{
	"customer":{
		"firstName": "John",
		"lastName": "Doe"
	},
	"addresses": [
		{
			"street": "Faraway Creek",
			"number": 1234,
			"city":	"Never-land"
		}
	],
	"balance": 23234.23,
	"active": true
}
````

##License

We are released under the Apache 2.0 license.

````
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
````
