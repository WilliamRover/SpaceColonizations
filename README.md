# SPACE COLONIZATION
We are the 3 idiotic students who are studying in Lappeenranta right now. We see project, we do it with a lot of back pain but also with tears of joy.
![asteroid_fail.png](jb-image:img_1776447081064_50a7f0b59dbba)
*in-game image*

## **CORE CONCEPT**

The player is put on a ship and their job is to manage it.
The ship is continuously flying and engaging in missions until the ship explodes.
In the main menu, there is a load game option, but in reality it is only one game, meaning the saved files are just the same game but at a certain checkpoint.
If the ship explodes or all crew are down, the game is over and all the data is wiped. Players can still load previous checkpoints.
The game is turn-based, meaning if the player performs an action, the next turn will be only the threat’s turn to move. Transferring crews between stations will not count as performing an action.

## **VIDEO DEMONSTRATION**
[A demonstration video. Definitely not a rick roll link](https://www.youtube.com/watch?v=X25ZJ84NOMo&t=1032s)

## **UML DIAGRAM**
![SpaceColony.drawio-(1).png](jb-image:img_1776446833301_c38aaa30afca18)

## **ACTIVITIES**
For every screen you see in the game is an Activity class. It mainly consist of initialization of View objects, and connect the button with the back end logic using the on click listener. Also implement core logic if the Activity is activated (for example enemy attack friendly in FightEnemyActivity)

## **ADAPTER**
Created for every available **recycler view available** in the xml file. This is to auto repeat the card (for example crew card) without to hardcode every single available data.

## **FRAGMENTS**
Created if a UI component is being **reused**. Most reasonable use of fragment in this game is the ship fragment, as it's a big fragment that contains a huge amount of functions and is reused in FightEnemyActivity and MapActivity.

## **OLD CREWMANAGER EXPLANATION**

The original implementation of CrewManager.java made use of serialization instead of json. But it has a few ghost object bugs that we could not fix. With some combinations of clicking continue and new game, some ghost crew exist from previous assignments and sometimes the view adapters break and don't show anything in stations even though crew are there. The old version is also included in project files as a text file in the station folder.


## **STATIONS**

One parent class, 'Station', handles most of the functions of different stations. Child classes have their own implementations for efficiency and also methods for station specific roles like damage related methods in Turret, healing related methods in medbay and training related functions in TrainingCenter. 
Stations have an attribute 'isUsable' which is used in crew assignment and halting stations' functions if the station is broken and to open up spots for repairmen.


## **CREW**

Crew superclass contains getters and setters for all of its attributes. All functions of Crew are handled by the parent class. Child classed do not have their own methods. Missions and Stations check for the specific subclass of crew when handing out bonusses.
the most notable attributes are currentStation and canWork. 
currentStation - useful for avoiding duplicate assignments and in file saving.
canWork - useful when assigning tasks


## **STATISTICS**

Keeps track of various things like number of living crew and number of successful missions. It is a singleton class for ease of use across the project. Contains getters and setters for all tracked statistics and some supporting functions for file saving.


## **AI USAGE**
### Umesh
Android studio integrated Gemini was used for understanding proper implementation of Runnables and Handlers for Station functions. Also used for solving deserializing singleton class (reaResolve function at the end of Barracks.java).

NOTE: Serialization related functions are not used in the final version of our project but they still remain in place as proof

### Thuan
AI was used to assist with the new Json save and load system, adapter and fragments, with the latter 2 are new concept and needed AI to understand how to use it quickly.