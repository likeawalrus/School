package ghp.Dungeon_Tactics.combat;


import java.util.Random;
import java.util.Vector;

public class init_manager {
	
	public Vector<init_struct> init_list = new Vector<>();
	
	init_manager()
	{
		
	}
	
	public void gen_inits(character_list c)
	{
		
		
		Random r = new Random();
		
		for(int i=0; i<=c.char_list.size()-1; i=i+1) //put all characters in list
		{
			init_struct ist = new init_struct(0,0,0);
			int ri = r.nextInt(20);
			ist.itotal = c.char_list.get(i).init+ ri;
			ist.ibonus = c.char_list.get(i).init;
			ist.cl_id = i;
			
			//System.out.println("random numbers " + ri);
			
			init_list.add(ist);
			
		}
		
		/*for(int i=0; i<=init_list.size()-1; i++)
		{
		System.out.println("init list values before sort" + init_list.get(i).itotal);
		
		}
		*/
		
		boolean s = true;
		while(s) //sort list (bubble sort)
		{
			s = false;
			for(int i=0; i < init_list.size() - 1; i++)
			{
				if(init_list.get(i).itotal < init_list.get(i+ 1).itotal)
				{
					 
					init_struct temp = new init_struct(0,0,0);
					temp = init_list.get(i);
					init_list.set(i, init_list.get(i+1));
					init_list.set(i+1, temp);
					temp = null;
					
					s = true;
				}

			}
		} //while(s)
		
	} //gen_inits

}
