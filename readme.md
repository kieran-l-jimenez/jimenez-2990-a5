#README WIP

The purpose of this program is to help the user manage an inventory of items. Each item will have a name(2-256 characters),
a serial number, and a value. The user can add, delete, and edit items. The user can search, sort, export, and load an 
inventory. At the time of export, the user can pick TSV(.txt), HTML(.html), or JSON(.json) for the format of the file and 
these types can all be loaded at a later time.

##1. Item Description
Items inside this application contain three fields:
1. Value - This is the price of the item. Value must be entered as a positive number and will be rounded to the 1/100 place.

2. Serial Number - This is a 10 character tag used to uniquely identify an item. This tag can be any combination of upper-case
   letters, lower-case letters, and digits [0-9].

3. Name - This is the name of the product. Names must have a character length less than 257 and more than 1.

##2. Inventory Controls
###1.1 Search
To search the current inventory the user will navigate to the "Search" item in the menu bar across the top of the application.
Upon clicking "Search", the user may activate a search using one of two different methods.
1. By Serial Number - This method requires a proper Serial Number to be entered within the "Serial Number" text field to
   the right of the table view.
2. By Name - This method requires a proper Name to be entered within the "Name" text field to the right of the table view.

###1.2 Sort
Along the top of the table view are the different fields of an item. To sort by a field simply click on the column heading.
This sorts the list in ascending order and clicking again sorts in descending order. This program does not automatically 
sort new items, and the user must cycle through the sort order again to have an accurately updated list.

###1.3 Export
WIP

By clicking the "File" item on the menu and clicking "Save File" a file chooser window will open where the user can 
name their inventory file and decide where it will be stored. Files can be saved in a Tab Separated Format(TSV), in HTML,
or in JSON.

###1.4 Load
WIP

By clicking the "File" item on the menu and clicking "Load File" a file chooser window will open where the user can
navigate their computer and open a new Inventory file.

##3. Item Controls
###2.1 Add
To add a new item to the current inventory the user will first fill in properly formatted data in the text field on the 
right. The user will then navigate to the "Edit" menu option and click "New Item".

###2.2 Delete
To delete an item the user will select the desired item within the table view before navigating to the "Edit" menu option 
and clicking "Delete Item".

###2.3 Edit
To edit an item the user will double-click the desired field to edit. A text field will appear in the table cell selected
where the user will type in the desired change. Press Enter to finalize the change.
