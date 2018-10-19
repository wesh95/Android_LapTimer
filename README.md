# Android_LapTimer

This Application explores the topic of handling Android lifecycle events. The task was to properly use Activity, Fragment and Asynctask to support user interactions like screen rotation and navigation. The application developed is a timer app for people to record time when preparing for a speech. The requirements were:

1. The timer app must have two screens: a control screen and a list screen. In the portrait mode, the user navigates between two screens by clicking some button (self-defined Button, navigation bar or back button). In the landscape mode, two screens show up at same time with the control screen on the left and the list screen on the right.

2. In the control screen, there must be a TextView shows the time (format: hh:mm:ss). At the beginning, the timer shows “00:00:00”.

3. There must three buttons on the control screen: start button, lap button and reset button, which control the timer.

4.  After clicking the start button, the time in the TextView starts to count up, with an interval of 1 second. After the timer starts counting, the start button changes to a stop button. Clicking the stop button will pause the timer, and the button changes back to a start button. Clicking the start button again will continue the counting.

5. Clicking the lap button will add one timestamp to the list screen. The user can record multiple timestamps, with an index in front of each timestamp, e.g.

00:00:10

00:01:00

00:02:20

...

Each time record should take a single line.

 6. Clicking the reset button will reset everything in the both screens. The timer will be reset to 00:00:00, The timestamp list on the list screen must be cleared. After resetting the app, the user can start another round of time counting and recording.

 7. The time counting must remain consistent. Rotating the screen or navigating to the record list will NOT stop/pause/reset the timer, and will NOT clear the timestamp list. The timer should keep counting up even after the screen rotation and navigating back from the list screen.

8. Implement the time counting with AsyncTask, do not use Java.util or other 3rd party timer.

SCREENSHOTS:

Initial state of timer (left). Timer running with AsyncTask (right)

![image ](https://github.com/wesh95/Android_LapTimer/blob/master/LapTimer_Screenshots/hw2_1.JPG)
![image](https://github.com/wesh95/Android_LapTimer/blob/master/LapTimer_Screenshots/hw2_2.JPG)

Lap Times Screen (left). Landscape mode (right)

![image](https://github.com/wesh95/Android_LapTimer/blob/master/LapTimer_Screenshots/hw2_3.JPG)
![image](https://github.com/wesh95/Android_LapTimer/blob/master/LapTimer_Screenshots/hw2_4.JPG)
