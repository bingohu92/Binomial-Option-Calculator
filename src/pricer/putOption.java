package pricer;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;


public class putOption {

	
	putOption(double sPrice, double volat, double strike, double maturity, int step, double cRate, int type){
		double a = Math.exp(cRate * (maturity / step));
		double upRate = 0.0;
	    double downRate = 0.0;
	    double np = 0.0;
	    timeSlot = maturity / step;
	    myVol = volat;
	    stockPrice = sPrice;
	    myStrike = strike;
	    myMaturity = maturity;
	    myStep = step;
		mycRate = cRate;
		myType = type;
	    
	    if (type == 1){
	    	upRate = Math.exp(volat * Math.sqrt(maturity / step));
	    	downRate = 1.0 / upRate;
	    	np = (a - downRate) / (upRate - downRate);
	    }
	    else if (type == 2){
	    	upRate = Math.exp(cRate * (maturity / step) / 2) * Math.exp(volat * Math.sqrt(maturity / step));
	        downRate = Math.exp(cRate * (maturity / step) / 2) / Math.exp(volat * Math.sqrt(maturity / step));
	        np = (a - downRate) / (upRate - downRate);
	    }
	    up = upRate;
	    down = downRate;
	    double nq = 1.0 - np;
	    
	    Queue<Node> myque = new LinkedList<Node>();
	    
	    int num_of_node = step + 1;
	    double myStockPrice[] = new double [num_of_node];
	    for (int i = 0; i < num_of_node; i++){
	        Node tem = new Node();
	        tem.step = step;
	        tem.stockPrice = sPrice * Math.pow(upRate,step - i) * Math.pow(downRate, i);
	        tem.optionPrice = Math.max((strike - tem.stockPrice), 0);
	        tem.left = null;
	        tem.right = null;
	        myque.add(tem);
	        myStockPrice[i] = tem.stockPrice;
	    }
	    
	    while(myque.size() > 1){
	        
	        Node n1 = myque.poll();
	        Node n2 = myque.peek();
	        
	        if (n1.step == n2.step){
	            
	            Node newnode = new Node();
	            newnode.left = n1;
	            newnode.right = n2;
	            newnode.step = n1.step - 1;
	            newnode.stockPrice = n1.stockPrice / upRate;
	            newnode.optionPrice = Math.max((np * n1.optionPrice + nq * n2.optionPrice) / a, strike - newnode.stockPrice);

	            myque.add(newnode);
	        }
	    }
	    head = new Node();
	    head = myque.peek();
	    optionPrice = head.optionPrice;
	}
	
	public double combination(int a, int b){
	    double temp = 1;
	    for (int i = 0; i < b; i++){
	        temp *= a;
	        temp /= (i + 1);
	        a--;
	    }
	    return temp;
	}
	
	public double getOptionPrice(){return new BigDecimal(optionPrice).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();}
	
	public double getDelta(){
		if (head.left != null && head.right != null){
	        double temp = (head.left.optionPrice - head.right.optionPrice) / (head.left.stockPrice - head.right.stockPrice);
	        return new BigDecimal(temp).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	    else
	        return 0;
	}
	
	public double getDeltaHelper(Node n){
		if (n.left != null && n.right != null){
	        double temp = (n.left.optionPrice - n.right.optionPrice) / (n.left.stockPrice - n.right.stockPrice);
	        return new BigDecimal(temp).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	    else
	        return 0;
	}
	
	
	public double GetGammaCal(){
	    if (head.left != null && head.right != null && head.left.left != null && head.right.right != null){
	    	double temp = (getDeltaHelper(head.left) - getDeltaHelper(head.right)) / (0.5 * (head.left.left.stockPrice - head.right.right.stockPrice));
	    	return new BigDecimal(temp).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
	    }
	    else
	        return 0;
	}
	
	public double GetTheta(){
		if (head.left != null && head.right != null)
	        return new BigDecimal((head.left.right.optionPrice - head.optionPrice) / (timeSlot * 2)).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
	    else
	        return 0;
	}
	
	public double GetVega(){
		putOption temp = new putOption(stockPrice, (myVol + 0.01), myStrike, myMaturity, myStep, mycRate, myType);
		return new BigDecimal((temp.getOptionPrice() - optionPrice) * 100).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public double getUp(){return new BigDecimal(up).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();}
	
	public double getDown(){return new BigDecimal(down).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();}
	
	
	private
	Node head;
	double up = 0.0;
	double down = 0.0;
	double stockPrice = 0.0;
	double optionPrice = 0.0;
	double timeSlot = 0.0;
	double myVol = 0.0;
	double myStrike = 0.0;
	double myMaturity = 0.0;
	int myStep = 0;
	double mycRate = 0.0;
	int myType = 0;
	
	public static void main(String[] args) {
		putOption c1 = new putOption(100, 0.2, 110, 1, 26, 0.05, 1);
		System.out.println("Option price is :" + c1.getOptionPrice());
		System.out.println("Delta: " + c1.getDelta());
		System.out.println("Gamma: " + c1.GetGammaCal());
		System.out.println("Theta: " + c1.GetTheta());
		System.out.println("Vega: " + c1.GetVega());
	} 
	
}

