# Initium-Combat-Sim
Combat Simulator for Initium

By Evanosity

If you are running it as a .jar file
Inside the location where you are running the jar file from, create a folder hierarchy as follow: /resources/entities/fileName.txt
All text files to be used by the program should be saved there as a basic .txt file.

Text File Format

Line 1: Name of the entity

Line 2: Strength of the entity

Line 3: Dexterity of the entity

Line 4: Intelligence of the entity

Line 5: Max HP of the entity

Line 6: Helm Defense Stats

Line 7: Chest Defense Stats

Line 8: Shirt Defense Stats

Line 9: Gloves Defense Stats

Line 10: Legs defense stats

Line 11: Boots defense stats

Line 12: Left Ring defense stats

Line 13: Right Ring defense stats

Line 14: Neck defense stats

Line 15: Left hand Offense stats

Line 16: Left hand Defense stats

Line 17: Right hand Offense stats

Line 18: Right hand Defense stats

Defense Stats are formatted as follows:
Dexterity Penalty/Block Chance/Damage Resistance/Bludge Modifier/Pierce Modifier/Slash Modifier/Name
All of these can be decimal numbers.

Offense Stats are formatted as follows:
Number of Dice/Dice Sides/Crit Multiplier/Crit Chance/damage types
The damage types is one letter for each damage type, ordering does not matter. B for bludge, P for pierce and S for slash.

The program will really not enjoy if you don’t format the files correctly, so I strongly encourage that you do.

When the program is launched, it will prompt you to enter an Attacker, a Defender and a number of simulations to do. Generally, 5000 runs is enough to give a good approximation of what your win chance is, although the more runs performed the more accurate it will be. 
