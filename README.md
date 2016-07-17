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
  "text": "input",
  "left": 20,
  "top": 20,
  "width": 200,
  "height": 25
}
````

- Just run this to test.
````
./j2m.sh -f "myDirectory/Box.json" -lang=java -o "/myDirectory/target/"
````

- A new file Box.class was created in the specified directory 
Box.class contains:

````java
public class ClassF {

	private double top;
	private double left;
	private double width;
	private String text;
	private double height;

	public void load() {
		//TODO: Needs to be implemented.
	}
	public getTop double() {
		return top;
	}
	public void setTop(double top) {
		this.top = top;
	}
	public getLeft double() {
		return left;
	}
	public void setLeft(double left) {
		this.left = left;
	}
	public getWidth double() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public getText String() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public getHeight double() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
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
