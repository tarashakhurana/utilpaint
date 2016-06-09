# paint-the-picture
A Java Graphics based applet that facilitates drawing with basic tools, inserting images and saving the created artwork.

**Features:**

All available features of the applet are listed as follows.

1. Tools that have been made a part of ‘Paint the Picture’ are Line, Oval, Rectangle and Pen. 

  * Line tool lets the user draw straight lines from the press point of the mouse to its release point, in any direction*. 

  * Oval tool lets the user draw either a vertical or horizontal ellipse. The ellipse is drawn within the bounds of the imaginary rectangle formed by the the press point and release point of the mouse, in any direction*. 

  * Rectangle tool draws rectangles in a similar manner in any direction*. 

  * Pen tool is a free hand drawing tool for making any kind of strokes or curves.

2. Thickness of the Pen tool and Line tool can be controlled with the help of a slider which enables the tools’ thickness ranging from 1pt to 50pts.

3. Clear All lets the user clear the entire work area and begin drawing again.

4. A Color Palette consisting of twelve random color choices is added to the applet. Out of all colors in the palette, white color is kept permanent so as to facilitate erasing a particular portion of the image using a combination of the Pen tool, white color and required thickness on the slider.

5. Open Image option lets the user open an existing image from the system in any image format and add it to the work area. The image is contracted/enlarged to fit the work area properly. This option opens up an Open dialog box for the user to browse through files and folders.

6. Save Image option lets the user save the current (and entire) work area with any drawings/inserted pictures/inserted and edited pictures as a png format image file. The destination of the file to be saved is decided by the Save dialog box that opens up for browsing through files and folders.

7. Status of the mouse is shown in the status bar of the applet. When the mouse is out of the applet screen, the status is displayed as, ‘Mouse exited applet area’ and ‘Mouse entered applet area’ otherwise.
