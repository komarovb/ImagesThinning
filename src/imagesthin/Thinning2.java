/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagesthin;

/**
 *
 * @author Borys
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class Thinning2 {
	
   private static int getR(int in) {
	   return (int)((in << 8) >> 24) & 0xff;
   }
   private static int getG(int in) {
	   return (int)((in << 16) >> 24) & 0xff;
   }
   private static int getB(int in) {
	   return (int)((in << 24) >> 24) & 0xff;
   }
   private static int toRGB(int r,int g,int b) {
	   return (int)((((r << 8)|g) << 8)|b); 
   }
   private static boolean end=false;
   private static int [][] weights = {{128,1,2},{64,-100,4},{32,16,8}};
   private static int [] a0 = {3,6,7,12,14,15,24,28,30,31,48,56,60, 62, 63, 96, 112, 120, 124, 126, 
		   						127, 129, 131, 135,143, 159, 191, 192, 193, 195, 199, 207, 223, 224, 225, 227,
		   						231, 239, 240, 241, 243, 247, 248, 249, 251, 252, 253, 254},
		   				a1 = {7, 14, 28, 56, 112, 131, 193, 224},
		   				a2 = {7, 14, 15, 28, 30, 56, 60, 112, 120, 131, 135,
		   						193, 195, 224, 225, 240},
		   				a3 = {7, 14, 15, 28, 30, 31, 56, 60, 62, 112, 120, 124, 131, 135, 143, 
		   						193, 195, 199, 224, 225, 227, 240, 241, 248},
		   				a4 = {7,14,15,28,30,31,56,60,62,63,112,120, 124, 126, 131, 135, 143, 159, 193, 195,
		   						199, 207, 224, 225, 227, 231, 240, 241, 243, 248, 249, 252},
		   				a5 = {7,14,15,28,30,31,56,60,62,63,112,120, 124, 126, 131, 135, 143, 159, 191, 193, 195, 199, 
		   						207, 224, 225, 227, 231, 239, 240, 241, 243, 248, 249, 251, 252, 254},
		   				a1pix = {3, 6, 7, 12, 14, 15, 24, 28, 30, 31, 48, 56, 60, 62, 63, 96, 112,
		   						120, 124, 126, 127, 129, 131, 135, 143, 159, 191, 192, 193, 195, 199, 207, 223, 224,
		   						225, 227, 231, 239, 240, 241, 243, 247, 248, 249, 251, 252, 253, 254};
   
    public static BufferedImage thinning(BufferedImage in) {
//    	File file = new File("bob.jpg");
        int MinPix = 280,MaxPix = 0,p=0,r =0,g = 0,b = 0,threshold = 70,pix = 0;
        int[][] map;
        int h=in.getHeight();
        int w=in.getWidth();
        map = new int[h][w];
        for (int y=0; y<h; y++){
            for (int x=0; x<w;x++){
                p = in.getRGB(x, y);
                pix = (getR(p)+getG(p)+getB(p))/3;
                
                if(pix<MinPix)
                {
                    MinPix=pix;
                }
                if(pix>MaxPix)
                {
                    MaxPix=pix;
                }
            }
        }
       for (int y=0; y<h; y++){
           for (int x=0; x<w;x++)
           {
               p = in.getRGB(x, y);
               r = getR(p);
               g = getG(p);
               b = getB(p);
               
               pix=(r+g+b)/3;
               //For the third test
               if(MaxPix==0&&MinPix==0){
                   MaxPix=255;
               }
               //----------
               pix=(pix-MinPix)*(255/(MaxPix-MinPix));
               if(pix<threshold)
               {
                   pix=0;
               }
               else{
                   pix=255;
               }
               if(pix==0){
                   map[y][x]=1;
               }
               else{
                   map[y][x]=0;
               }
               in.setRGB(x, y, toRGB(pix,pix,pix));
           }
       }
       while(!end){
           end=true;
           phase0(map,w,h);
            	for (int i = 1; i <= 5; i++) {
					phasex(map,w,h,i);
				}
            	phase6(map,w,h);
            	phaseLast(map,w,h);
       }
//       printMap(map,w,h);
       for (int y=1; y<h-1; y++){
           for (int x=1; x<w-1;x++){
               pix=0;
               if(map[y][x]==1)
                   in.setRGB(x, y, toRGB(pix,pix,pix));
               else{
                   in.setRGB(x, y, toRGB(255,255,255));
               }
           }
       }
       end=false;
       return in;
    }
    
    private static void phase0(int[][] map,int w,int h){
    	for (int y=1; y<h-1; y++){
            for (int x=1; x<w-1;x++){
            	if(map[y][x]==1){
            		if(calcAndCompareWeight(map,y,x,a0)){
	            		map[y][x]=2;
	            	}
            	}
            } 
    	}
    }
    
    private static void phasex(int[][] map,int w,int h,int n){
    	int[] delete = {3,4,5,6,7},ax;
    	int phaseN = 0,seq=0;
    	boolean flag = false;
    	switch (n) {
		case 1:
			phaseN=1;
			ax=a1;
			break;
		case 2:
			phaseN=2;
			ax=a2;
			break;
		case 3:
			phaseN=3;
			ax=a3;
			break;
		case 4:
			phaseN=4;
			ax=a4;
			break;
		case 5:
			phaseN=5;
			ax=a5;
			break;

		default:
			ax=a0; //Just in case of some error
			break;
		}
    	for (int y=1; y<h-1; y++){
            for (int x=1; x<w-1;x++){
            	if(map[y][x]==2){
            		seq=calcN(map,y,x,1);
	            	for(int i=0;i<phaseN;i++){
		            	if(delete[i]==seq){
		            		flag=true;
		            		break;
		            	}	
		            }
            		if(flag){
            			if(calcAndCompareWeight(map,y,x,ax))
            			{
            				map[y][x]=0;
            				end=false;
            			}
            			flag=false;
            		}
            		else{
            			flag=false;
            		}
            	}
            }
    	}	
    }
    private static void phase6(int[][] map,int w,int h){
    	for (int y=1; y<h-1; y++){
            for (int x=1; x<w-1;x++){
            	if(map[y][x]==2){
            		map[y][x]=1;
            	}
            }
    	}
    }
    private static void phaseLast(int[][] map,int w,int h){
    	for (int y=1; y<h-1; y++){
            for (int x=1; x<w-1;x++){
            	if(map[y][x]==1){
            		if(calcAndCompareWeight(map,y,x,a1pix)){
	            		map[y][x]=0;
	            	}
            	}
            } 
    	}
    }
    private static void printMap(int[][] map,int w,int h){
    	for (int y=1; y<h-1; y++){
            for (int x=1; x<w-1;x++){
            	System.out.print(map[y][x]);
            } 
            System.out.println();
    	}
    	System.out.println("--------------------------------------------");
    }
    private static boolean calcAndCompareWeight(int[][] map, int y, int x, int[] a){
    	//Calculating weight
    	int w=0;
    	//left side
    	if(map[y-1][x-1]!=0){
    		w+=weights[0][0];
    	}
    	if(map[y][x-1]!=0){
    		w+=weights[1][0];
    	}
    	if(map[y+1][x-1]!=0){
    		w+=weights[2][0];
    	}
    	//right side
    	if(map[y-1][x+1]!=0){
    		w+=weights[0][2];
    	}
    	if(map[y][x+1]!=0){
    		w+=weights[1][2];
    	}
    	if(map[y+1][x+1]!=0){
    		w+=weights[2][2];
    	}
    	//middle
    	if(map[y-1][x]!=0){
    		w+=weights[0][1];
    	}
    	if(map[y+1][x]!=0){
    		w+=weights[2][1];
    	}
    	
    	//Comparing to deleting array
    	for (int i = 0; i < a.length; i++) {
			if(a[i]==w){
				return true;
			}
		}
		return false;
    }
    private static int calcN(int[][] map, int y, int x,int param) {
    	//In this function we are going through our neigbours in such way:
    	/*
    	 * param = 1 - calculating longest sequence of neighbours
    	 * param = 2 - calculating just number of neighbours
    	 * 
    	 * 1 2 3 
    	 * 8 0 4
    	 * 7 6 5
    	 * 
    	 * where 0 - is our pixel for which we are looking for neighbours
    	 * */
    	int seq=0,maxs=0,vary,varx,lm=0;
    	for(int k=1;k<=8;k++){
	    	for(int l=k+1;l<=8+k;l++){
	    		//To determine y change for every step
	    		int n=0;
	    		//formula to convert from l 1->16 to n 1->8
	    		if(l<=8)
	    			n=l;
	    		else
	    			n=l-8;
	    		if(n<4)
	    			vary=y-1;
	    		else if(n==4||n==8)
	    			vary=y;
	    		else
	    			vary=y+1;
	    		//To determine x change for every step
	    		if(n==1||n>6)
	    			varx=x-1;
	    		else if(n==2||n==6)
	    			varx=x;
	    		else
	    			varx=x+1;
	    		if(map[vary][varx]!=0){
	    			seq++;
	    		}
	    		else if(param==1){
	    			lm=Math.max(lm, seq);
	    			seq=0;
	    		}
	    	}
	    	lm=Math.max(lm, seq);
	    	seq=0;
	    	if(param==1)
	    		maxs=Math.max(maxs, lm);
    	}
    	maxs=Math.max(maxs, lm);
    	if(param==1)
    		return maxs;
    	else
    		return seq;
	}
}