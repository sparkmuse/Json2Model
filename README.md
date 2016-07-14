# Json2Model

##About
This is a small little tool to help to automate the creation of Models from JSON files.

##Goals
The golas of the program goes here.

##Install
- Download or clone repository.
- Navigate to folder ````/Json2Model/bin/````
- Run the ````j2m.sh```` from the command line.

Note: Make sure that the file has persions to run in your computer.

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
                           Allowed langauges:
                           [C, OBJECTIVE_C, JAVA, SWIFT, VISUAL_BASIC,
                           VBA, UNKNOWN]
 -o,--out <DIR>            The directory where to place the processed
                           files. If ommited the same DIR as 'file' is
                           used
Enjoy
````


##User Guides
- Just run this to test.
````
./j2m.sh -f "myDirectory/input.json" -lang=java -o "/myDirectory/target/"
````

##Licence
- The licence will go here
