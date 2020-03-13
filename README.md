# Orbiter
A JavaFX program for visualizing the orbits of a math function. Made as a university math project.

Uses the mXparser library by Mariusz Gromada for evaluating functions. Uses JavaFX for user interaction.






1. Usage

Note: to use the application you need Java installed on your computer.



First, input some number into the textbox in the main menu. It can be an integer or a fractional number (using a dot), and then click “Add”. It’s going to be visible in the menu and a button to remove it from the list will show up. The numbers added here will be the ones that the function we define later will be applied to.


Make sure not to click “add” if there’s no text in the textbox or if there’s text (not numbers) there, since then the application will throw an error.

After inputting a few numbers you choose one of the three options on the bottom:

1. Provide a single formula for all arguments – you need to input a formula that will be applied to all the points. It’s important that you don’t shorten expressions like ‘2*x+4’ into ‘2x+4’ - the multiplication sign has to be there between the numbers, it cannot be omitted. Using functions such as sin(x), cos(x), ln(x) or even min(1,2,3,4) should work, just make sure there’s no typos. Also, don’t include ‘y=’ or ‘f(x)=’ in the text, just what you want the value to be.

2. Provide a conditional formula – this is probably the most interesting and “powerful” feature here – you can provide a formula that depends on the arguments such as the one in the 3x+1 problem

To do that, you provide several conditions and the appropriate values of the function that you want if the conditions are met. You could use x>1 or x>=-50, you can also use modulo by using the # sign.

3. The third option is to just assign specific values to each argument (without any formula). This is the simplest variant – you just write some numbers into EVERY textbox and click done.

To exit the generated graph just press Escape – keep in mind that the arguments will stay there so if you want to use different ones you need to delete them.

2. Implementation

Probably the most important part of the application is a simple linked list of objects called “Point” - they have a couple of attributes, most importantly Xand Y. Each point’s X corresponds to one value you provide in the main menu. Similarly, its' Y will be the value of the argument once its' calculated through the function or assigned.

Interpreting functions that a user entered is a tremendous task so a dedicated library – mXparser – has been used for it. Thankfully it supports variables, so one can provide it a string such as “2*x+7” along with a value for the x as an argument and it’s going to calculate that. It also supports checking whether a statement (such as 6>7, or more importantly x>7) is true – that’s how the conditional formulas are implemented. As you can see, using a single formula for all points is actually pretty easy – just use a for loop to go through every point, get its X value, put it as an argument in the formula the user provided and save the output as Y.

mXparser offers a .checkSyntax() function that you can use to check if the syntax was understood by the application – if not then we show a pop-up saying that something’s wrong. It’s very important that you check the syntax that mXparser uses – there are examples on this wikipedia page under “main features/usage examples”. As I noted earlier for example, modulo is denoted by # instead of %.

The “assign specific values” option simply generates a textbox for each point end once you’re done assigns the value of the textbox into the point’s Y attribute.

The generation of the graph is a relatively simple use of a collection of (randomly colored for better visibility of which argument goes where) shapes (circle, arc and two lines for arrows). I’ve had an issue where if several arguments went into the same value it was hard to see them, so I decided to make the arrowheads slightly randomized – it doesn’t look all that great but now you can see that multiple arguments land on the same value and by checking the color you can confirm which ones these are.

Before drawing the graph the list of points is sorted by the X value to make it more readable. The arc is drawn by getting the middle point between two arguments and putting the center of the arc there (then setting it to start at 0 degrees and go up to 180 degrees). 

 All the graphical user interfaces use JavaFX  – a library that was once built-in for Java (up till version 1.8) but now it has to be downloaded separately. 
