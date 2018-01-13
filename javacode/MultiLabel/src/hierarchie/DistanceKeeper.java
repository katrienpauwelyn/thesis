/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hierarchie;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *Klasse om de afstanden voor de RHam hierarchieen bij te houden. 
 * @author katie
 */
public class DistanceKeeper {
    public String originalClass;
    public HashMap<String, Integer> distances;
    
  
    public DistanceKeeper(String cla){
        originalClass = cla;
        distances = new HashMap();
    }
  
    
    public DistanceKeeper(String cla, ArrayList<String> classes){
        originalClass = cla;
        distances = new HashMap();
        for(String s: classes){
            distances.put(s.replace("-", ":").replace("/",":"), 0);
        }
    }
    
    
    
    public boolean isClass(String cl){
        return originalClass.equals(cl);
    }
   
    //returnt de klasse die het dichtst bij originalClass ligt
    public String getClosest(){
        int d = Integer.MAX_VALUE;
        String c = "";
        for(String s: distances.keySet()){
            if(distances.get(s)<d){
                d=distances.get(s);
                c=s;
            }
        }
        return c;
    }
   
    //vergroot de afstand van originalClass naar c met 1
    public void incrementClass(String c){
        c = c.replace("-", ":").replace("/",":");
        int d = 0;
        try{ 
            d = distances.get(c);
        } catch(Exception e){//extra failsafe
            System.out.println("exception");
            System.out.println(c);
            for(String s: distances.keySet()){
                System.out.println(s);
            }
            throw new Error();
        }
        
        distances.put(c, d+1);
    }
   
    //returnt de afstand van originalClass naar c
    public int getDistance(String c){
        c = c.replace("-", ":").replace("/",":");
        return distances.get(c);
    }
   
    /**
     * @pre newNodes.get(0) bevat altijd de klasse die het eerst toegevoegd geweest is, dus dit is
     * (by default) de klasse waartot de afstand berekend moet worden
     * @param newNodes
     * @return de dichtste knoop die uit newNodes komt. 
     */
    public Node closestNode(ArrayList<Node> newNodes){
        int distance = Integer.MAX_VALUE;
        Node closest=null;
        for(Node n: newNodes){
            String c = n.getClasses().get(0);
            if(distance > getDistance(c)){
                closest = n;
                distance = getDistance(c);
            }
        }
        return closest;
    }
    
    
}
