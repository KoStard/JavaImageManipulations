# JavaImageManipulations
Some manipulations with images in java.

* blur - averaging
* negative
* decay - making pixels less different by squaring their distance percentage from 127.5 (e.g. 50 - 103)
* normalize - normalize pictures with {A, R/(R+G+B), G/(R+G+B), B/(R+G+B)} - you the normalizeTest.png for testing and you have to get the normalizeResult.png
* noise - added standard random noise with addition to the current pixel value (overlay). This has colored mode and range definition.

Here you can find status logger, which lets log the process progress with percentage system or showing the finished part - includes step size selection, which will let you determine logging frequency.
