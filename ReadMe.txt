README  Course: cs400 Semester: Summer 2020 Project name: “Remember Me?” Student Name: Sam Goldberg
Other notes or comments to the grader: Each line in the CSV must have one of the following formats
	1. lastName, firstName, phoneNumber
	2. lastName, firstName, phoneNumber, pictures/photoName (all images must be placed inside the pictures folder and be typed into the cvs in this format)

Instructions
The program does not yet add or remove contacts from the source file yet. The input file also does not support adding other information at the start of program yet (such as: school, major, work, date of birth). If a source file name that does not exist inside the project folder, the GUI will popup an alert that tells the user that the file does not exist. If the format is incorrect a similar popup will appear. [place any comments or notes that will help the grader here]
Input files must be inside the project folder but not inside the application folder.

Bug Report
I was unable to make the program write back to the input file. 
When clicking on a star from the recents list, the corresponding ContactShallow object in the main contacts section will not update the star until the contact is clicked in the main contacts section.

Future Works
The original idea for this project was to make an interactive graph of contacts. In the future, the application will contain a graph with contacts that point to their mutual friends. The contacts will also all save to a new output file as a local data base. Additionally there will be more formats to type in a contact information instead of only allowing name, phone number, and photo to be in the input file. I would also like to make the project look nicer in the future after learning more about css styling.

