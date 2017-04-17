/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hierarchie;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
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
    
    public void incrementClass(String c){
        c = c.replace("-", ":").replace("/",":");
        int d = 0;
        try{ 
            d = distances.get(c);
        } catch(Exception e){
            System.out.println("exception");
            System.out.println(c);
            for(String s: distances.keySet()){
                System.out.println(s);
            }
            throw new Error();
        }
        
        distances.put(c, d+1);
    }
    
    public int getDistance(String c){
        c = c.replace("-", ":").replace("/",":");
        return distances.get(c);
    }
    
    /**
     * @pre newNodes bevat enkel de startknoop, dus maar 1 knoop
     * @param newNodes
     * @return de dichtste knoop die uit newNodes komt. newNodes bevat enkel de startknopen (dus de
     * afstand wordt tot deze startknoop berekend)
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
