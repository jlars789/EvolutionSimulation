<h1> Evolution Simulation </h1>

<p> Code used for displaying and working with participants of the 2019 STEAM Club Workshop.
Topic: Visual Introduction to Artificial Intelligence </p>

<h3> Summary </h3> 

<p> Colored spheres fight to survive by not starving. Food is in the form of small pink squares. Creatures will create slightly mutated (randomized) offspring after
both eating enough food and surviving long enough.
Creatures will move around, moving semi-randomly, changing direction when hitting a wall. They will chase food within their range, then return to their normal path.
Each creature is generated with completely randomized stats. 
These stats include: </p>

<ul>
<li> <code>size</code> (radius in pixels) </li>
<li> <code>mitosisTime</code> (base time required before split) </li>
<li> <code>speed</code> (amount of pixels moved per frame) </li>
<li> <code>range</code> (maximum distance food can be before creature will chase it) </li>
<li> <code>metabolism</code> (how much food will contribute towards offspring creation, and how quickly hunger will deplete) </li>
</ul>

<h3> Purpose </h3>

<p> Give a visual representation of Evolutionary Algorithms by attempting to solve the problem of what the most important statistic is of a creature in
this fictional environment. This occurs by weak creatures failing to compete for food and dying out and strong creatures overpopulating and dominating the food
sources, also known as natural selection. </p>

<h3> Results </h3> 

<p> Speed and range were consistently the highest stats. </p>

<h3> How to Run </h3>

<p> Fork must be placed individually into a Java Project </p>
