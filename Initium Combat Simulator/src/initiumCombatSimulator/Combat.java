package initiumCombatSimulator;

import javax.swing.JTextArea;

/**
 * Combat Class - this Object will run combat between two Entities.
 * @author Evanosity
 *
 */
public class Combat {
	private Entity player;
	private Entity npc;
	private JTextArea output;
	/**
	 * public Combat - main constructor for combat objects.
	 * @param player - the player.
	 * @param npc - the enemy that the player will be fighting against.
	 */
	public Combat(Entity yourCharacter, Entity enemy, JTextArea haha){
		player=yourCharacter;
		npc=enemy;
		output=haha;
	}
	
	/**
	 * public void runSim - this method runs the same battle a given number of times to see what the chances of winning are.
	 * @param runs - the number of battles to do.
	 */
	public void runSim(int runs){
		//Prepare to simulate!
		output.append("=== "+player.getName().toUpperCase()+ " VS " +npc.getName().toUpperCase()+" ===\n\n");
		int playerWins=0;
		int npcWins=0;
		int turns=0;
		
		//this loops controls the simulation.
		for(int i=0; i!=runs; i++){
			while(!player.isDead()&&!npc.isDead()){
				turns++;
				attack(0, player, npc);
				//the attacker swings first. If the NPC dies, he does not get a swing.
				if(!npc.isDead()){
					attack(0, npc, player);
				}
			}
			
			//check to see if the fight has been won.
			if(player.isDead()){
				npcWins++;
			}
			else if(npc.isDead()){
				playerWins++;
			}
			
			//after the fight is done, reset the HP and durability of each player.
			player.resetAll();
			npc.resetAll();
		}
		
		//This reveals the outcome to the user after the simulation is complete.
		output.append(player.getName()+" wins: "
				+ playerWins+"\n\n"
				+ player.getName()+" win %: "
				+ (((double)playerWins/runs)*100)+"\n\n"
				+ npc.getName()+" wins: "
				+ npcWins+"\n\n"
				+ npc.getName()+" win %: "
				+ (((double)npcWins/runs)*100)+"\n\n"
				+ "Average Turns: "+turns/runs+"\n\n"
				+ "Total Runs: "+runs+"\n\n"
				+ "");
	}
	
	/**
	 * public void attack - this method rolls an attack against an enemy.
	 * @param hand - the hand under which the attacker is attacking with. 0 is left, 1 is right.
	 * @param Attacker - the attacking entity
	 * @param Defender - the defending entity
	 */
	public void attack(int hand, Entity Attacker, Entity Defender){
		//System.out.println(Attacker.getName()+" swings at "+Defender.getName());
		if(Attacker.rollDex()>=Defender.rollDex()){
			//System.out.println("HIT!");
			Weapon swinger=null;
			if(hand==0){
				//System.out.println("\n\n"+Attacker.getName()+" swinging with: "+Attacker.getWeapon("lefthand").getName());
				swinger=Attacker.getWeapon("lefthand");
				if(Attacker.getWeapon("righthand").dualWield()){
					attack(1, Attacker, Defender);
				}
			}
			else if(hand==1){
				//System.out.println("\n\n"+Attacker.getName()+"swinging with: "+Attacker.getWeapon("righthand").getName());
				swinger=Attacker.getWeapon("righthand");
				//if(Attacker.getWeapon("lefthand").dualWield()){
				//	attack(0, Attacker, Defender);
				//}
			}
			int damage=swinger.rollDamage();	
			//System.out.println("damage: "+damage);				
			int totalResistance=0;		
			if(Defender.getWeapon("lefthand").isBlock()){
				totalResistance+=(int)Defender.getWeapon("lefthand").getDamageReduction()*Defender.getWeapon("lefthand").findWeakSpot(swinger);
				//System.out.println(Defender.getWeapon("lefthand").getName()+" blocks.");
				//System.out.println("totalresistance: "+totalResistance);
			}
			if(Defender.getWeapon("righthand").isBlock()){
				totalResistance+=(int)Defender.getWeapon("righthand").getDamageReduction()*Defender.getWeapon("righthand").findWeakSpot(swinger);
				//System.out.println(Defender.getWeapon("righthand").getName()+" blocks.");
				//System.out.println("totalresistance: "+totalResistance);
			}
			
			Armor bodyArmor=Defender.hitWhere();
			if(bodyArmor.isBlock()){
				totalResistance+=(int)bodyArmor.getDamageReduction()*bodyArmor.findWeakSpot(swinger);
				//System.out.println(bodyArmor.getName()+" blocks.");
				//System.out.println("totalresistance: "+totalResistance);
				if(bodyArmor.getSlot().equals("chest")){
					if(Defender.getArmor("shirt").isBlock()){
						totalResistance+=(int)Defender.getArmor("shirt").getDamageReduction()*Defender.getArmor("shirt").findWeakSpot(swinger);
						//System.out.println(Defender.getArmor("shirt").getName()+" blocks.");
						//System.out.println("totalresistance: "+totalResistance);
					}
				}
			}
			
			if(Defender.getArmor("rightring").isBlock()){
				totalResistance+=(int)Defender.getArmor("rightring").getDamageReduction()*Defender.getArmor("rightring").findWeakSpot(swinger);
				//System.out.println(Defender.getArmor("rightring").getName()+" blocks.");
				//System.out.println("totalresistance: "+totalResistance);
			}
			if(Defender.getArmor("leftring").isBlock()){
				totalResistance+=(int)Defender.getArmor("leftring").getDamageReduction()*Defender.getArmor("leftring").findWeakSpot(swinger);
				//System.out.println(Defender.getArmor("leftring").getName()+" blocks.");
				//System.out.println("totalresistance: "+totalResistance);
			}
			if(Defender.getArmor("neck").isBlock()){
				totalResistance+=(int)Defender.getArmor("neck").getDamageReduction()*Defender.getArmor("neck").findWeakSpot(swinger);
				//System.out.println(Defender.getArmor("neck").getName()+" blocks.");
				//System.out.println("totalresistance: "+totalResistance);
			}
			
			if(damage<=totalResistance){
				//System.out.println("Fully Blocked!");
			}
			else if(damage>totalResistance){
				//System.out.println(Defender.getName()+" took "+(damage-totalResistance)+" damage!");
				Defender.takeDamage(damage-totalResistance);
				//System.out.println(Defender.getName()+" current hp: "+Defender.getCurrentHP());

			}
		}
	}
}