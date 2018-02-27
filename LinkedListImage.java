import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;
public class LinkedListImage implements CompressedImageInterface {
    public LinkedList[] array;
    public int width;
    public int height;

    public class Node{
        private Integer element;
        private Node next;
        public Node(Integer e, Node n){
            element = e;
            next = n;
        }
        public int getElement(){return element;}
        public Node getNext(){return next;}
        public void setNext(Node n){next = n;}
    }

    public class LinkedList{
        private Node head = null;
        private Node tail = null;
        private int size = 0;
        public int size(){return size;}
        public boolean isEmpty(){return size==0;}
        public void addLast(Integer e){
            Node newest = new Node(e, null);
            if(isEmpty())
                head = newest;
            else
                tail.setNext(newest);
            tail = newest;
            size++;
        }
    }

	public LinkedListImage(String filename) throws IOException
	{
        File text = new File(filename);
        Scanner sc = new Scanner(text);
        String[] line1 = sc.nextLine().split(" ");
        width = parseInt(line1[0]);
        height = parseInt(line1[1]);
        array = new LinkedList[height];
        for (int i=0; i<height; i++){
            String[] line = sc.nextLine().split(" ");
            array[i] = new LinkedList();
            for(int j=0; j<width; j++){
                if(j==0 && line[0].equals("0"))
                    {array[i].addLast(0);}
                if(j==0 && line[0].equals("0") && line[1].equals("1"))
                	{array[i].addLast(0);}
                if(j>0 && line[j].equals("0") && line[j-1].equals("1"))
                    {array[i].addLast(j);}
                if(j>0 && j<width-1 && line[j].equals("0") && line[j+1].equals("1"))
                    {array[i].addLast(j);}
                if(j>0 && j==width-1 && line[j].equals("0"))
                    {array[i].addLast(j);}
            }
        }
	}

    public LinkedListImage(boolean[][] grid, int width1, int height1)
    {
        width = width1;
        height = height1;
        array = new LinkedList[height1];;
        for (int i=0; i<height; i++){
            array[i] = new LinkedList();
            for(int j=0; j<width; j++){
                if(j==0 && grid[i][j]==false)
                    {array[i].addLast(j);}
                if(j==0 && grid[i][j]==false && grid[i][j+1]==true)
                	{array[i].addLast(j);}
                if(j>0 && grid[i][j]==false && grid[i][j-1]==true)
                    {array[i].addLast(j);}
                if(j>0 && j<width-1 && grid[i][j]==false && grid[i][j+1]==true)
                    {array[i].addLast(j);}
                if(j>0 && j==width-1 && grid[i][j]==false)
                    {array[i].addLast(j);}
            }
        }
    }


    public boolean getPixelValue(int x, int y) throws PixelOutOfBoundException
    {
        if (x > height-1 || y > width-1 || x < 0 || y < 0){
            throw new PixelOutOfBoundException("Invalid Input");
        }
        else{
            Node node = array[x].head;
            while (node!=null){
                if (node.element <= y && y <= node.next.element) return false;
                node = node.next.next;
            }
            return true;
        }
    }

    public void setPixelValue(int x, int y, boolean val) throws PixelOutOfBoundException
    {
    	if (x >= height || y >= width || x < 0 || y < 0){
			throw new PixelOutOfBoundException("");
		}
		if(val==getPixelValue(x,y)){return;}
		if(val==false){
			Node node = array[x].head;
			if(node == null){
				array[x].addLast(y);
				array[x].addLast(y);
				return;
			}
			if(node != null && y==node.element-1){
				node.element-=1;
				return;
			}
			if(node != null && y<node.element-1){
				Node a = new Node(y, node);
				Node b = new Node(y, a);
				array[x].head = b;
				return;
			}
			boolean flag = false;
			while(flag==false){
				if(y>=node.element && y<=node.next.element){
					flag = true;
				}
				if(y==node.next.element+1){
					if(node.next.next!=null && node.next.next.element==y+1){
						node.next = node.next.next.next;
						flag=true;
				}
					else{
						Node a = new Node(y, node.next.next);
						node.next = a;
						flag=true;
					}
				}
				else if(node.next.element<y && node.next.next!=null && node.next.next.element-1>y){
					Node a = new Node(y, node.next.next);
					Node b = new Node(y, a);
					node.next.next = b;
					flag = true;
				}
    			else if(node.next.element<y && node.next.next!=null && node.next.next.element-1==y){
    				Node a = new Node(y, node.next.next.next);
    				node.next.next=a;
    				flag=true;
    			}
    			else if(node.next.element<y && node.next.next==null){
    				Node a = new Node(y, null);
    				Node b = new Node(y, a);
    				node.next.next=b;
    				flag=true;
    			}
				else{node=node.next.next;}
			}
    	}
    	else{
    		Node node = array[x].head;
    		if(node==null){
    			return;
    		}
    		if(y<node.element){
    			return;
    		}
    		boolean flag=false;
    		while(flag==false){
    			if(y==node.element && node.element!=node.next.element){
    				node.element+=1;
    				flag=true;
    			}
    			else if(y==node.next.element && node.element!=node.next.element){
    				node.next.element-=1;
    				flag=true;
    			}
    			else if(y==node.element && node.element==node.next.element){
    				if(node.element==array[x].head.element){array[x].head=node.next.next;}
    				else{
    					Node newnode = array[x].head;
    					while(newnode.next.element!=node.element){
    						newnode=newnode.next;
    					}
    					newnode.next = node.next.next;
    				}
    				flag=true;
    			}
    			else if(y>node.element && y< node.next.element){
    				Node a = new Node(y+1, node.next);
					Node b = new Node(y-1, a);
					node.next = b;
					flag=true;
    			}
    			else if(y>node.next.element && node.next.next!= null && y<node.next.next.element){
    				flag = true;
    			}
    			else if(y>node.next.element && node.next.next==null){
    				flag = true;
    			}
    			else{
    				node=node.next.next;
    			}
    		}
    	}
    }

    public int[] numberOfBlackPixels()
    {
        int[] b_count = new int[height];
        for (int i = 0; i < height; i++){
            Node node = array[i].head;
            int sum=0;
            while (node != null){
                sum = sum +  1+ node.next.element - node.element;
                node = node.next.next;
            }
            b_count[i]=sum;
        }
        return b_count;
    }
    
    public void invert()
    {   
        LinkedList[] array_inv = new LinkedList[height];
        for(int i=0; i<height; i++){
            Node node = array[i].head;
            array_inv[i] = new LinkedList();
            if(node!=null && node.element==0){
            	node=node.next;
                while(node.next!=null){
                    if(node.next!=null){
                        array_inv[i].addLast(node.element+1);
                        if(node.next.element<width-1){array_inv[i].addLast(node.next.element-1);}
                        else if(node.next.next!=null && node.next.next.element==width-1 ){array_inv[i].addLast(node.next.element-1);}else{array_inv[i].addLast(width-1);}
                        node=node.next.next;                    }
                }
                if(node.next==null && node.element!=width-1)
                	{array_inv[i].addLast(node.element+1); array_inv[i].addLast(width-1);}
                Node p = array_inv[i].head;
                while(p!=null && p.next!=null && p.next.next!=null && p.next.next.next!=null){p=p.next;}
                if(p!=null && p.element==width-1){p.next=null;}
                array[i]=array_inv[i];
            }
            else if(node!=null && node.element!=0){
                array_inv[i].addLast(0);
                while(node!=null){
                    if(node!=null){
                        array_inv[i].addLast(node.element-1);
                        if(node.next!=null && node.next.element<width-1){array_inv[i].addLast(node.next.element+1);}
                        if(node.next!=null && node.next.next==null && node.next.element!=width-1){array_inv[i].addLast(width-1);}
                        if (node.next!=null){node=node.next.next;}
                    }
                }
                array[i]=array_inv[i];
            }
            else if(node==null){
                array[i].head = new Node(0, null);
                array[i].head.next = new Node(width-1, null);
            }
        }

    }
    
    public void performAnd(CompressedImageInterface img) throws BoundsMismatchException
    {
    	LinkedListImage imgin = (LinkedListImage) img;
    	if(this.width!=imgin.width || this.height!=imgin.height)
    		throw new BoundsMismatchException("Input should be of same dimensions!");
		imgin.invert();
    	this.invert();
    	this.performOr(imgin);
    	this.invert();
    	imgin.invert();
	}  
    public void performOr(CompressedImageInterface img) throws BoundsMismatchException
    {
    	LinkedListImage imgin = (LinkedListImage) img;
    	if(this.width!=imgin.width || this.height!=imgin.height)
    		throw new BoundsMismatchException("Input should be of same dimensions!");  	

    	for(int i=0; i<height; i++){
    		Node a = array[i].head;
    		Node b = imgin.array[i].head; int last = -1;
    		LinkedList temp = new LinkedList();
    		while(a!=null && b!=null){
    			if(a.element<=b.next.element && a.next!=null && b.next!=null && a.next.element>=b.element){
    				if(a.element<b.element){temp.addLast(b.element);}else{temp.addLast(a.element);}
    				if(a.next.element>b.next.element){temp.addLast(b.next.element);}else{temp.addLast(a.next.element);}
    				if(a.next.next!=null && a.next.next.element<=b.next.element && a.next.next.next!=null && a.next.next.next.element>=b.element)
    					a=a.next.next;
    				else if(b.next.next!=null  && b.next.next.element<=a.next.element && b.next.next.next.element>=a.element)
    					b=b.next.next;
    				else{
    					a=a.next.next;
    					b=b.next.next;
    				}
    			}
    			else{
    				if(a.next!=null && a.next.next!=null && b.next!=null && a.next.next.element<=b.next.element && a.next.next.next!=null && a.next.next.next.element>=b.element)
    					a=a.next.next;
    				else if(a.next!=null && a.next.next!=null && a.next.next.next!=null && a.next.next.next.element<b.element)
    					a=a.next.next.next.next;
    				else if(b.next!=null && b.next.next!=null && b.next.next.element<=a.next.element && b.next.next.next!=null && b.next.next.next.element>=a.element)
    					b=b.next.next;
    				else if(b.next!=null && b.next.next!=null && b.next.next.next!=null && b.next.next.next.element<a.element)
    					b=b.next.next.next.next;
    				else{
    					if(a.next!=null){a=a.next.next;}
    					if(b.next!=null){b=b.next.next;}
    				}
    			}
    		}
    		array[i]=temp;
    	}
    }
    
    public void performXor(CompressedImageInterface img) throws BoundsMismatchException
    {
    	LinkedListImage imgin = (LinkedListImage) img;
    	if(this.width!=imgin.width || this.height!=imgin.height)
    		throw new BoundsMismatchException("Input should be of same dimensions!");  
    	boolean[][] grid1 = new boolean[this.width][this.height];
    	boolean[][] grid2 = new boolean[this.width][this.height];
    	LinkedListImage img1 = new LinkedListImage(grid1, this.width, this.height);
    	LinkedListImage img2 = new LinkedListImage(grid2, imgin.width, imgin.height);
    	for (int i = 0; i < this.height; i++){
    		LinkedList temp1 = new LinkedList();
    		LinkedList temp2 = new LinkedList();
    		Node node1 = this.array[i].head;
    		Node node2 = imgin.array[i].head;
    		while(node1!=null){
    			temp1.addLast(node1.element);
    			node1=node1.next;
    		}
    		while(node2!=null){
    			temp2.addLast(node2.element);
    			node2=node2.next;
    		}
    		img1.array[i]=temp1;
    		img2.array[i]=temp2;

        } 
    	img1.invert();
    	imgin.invert();
    	this.performAnd(imgin);
    	img2.performAnd(img1);
    	this.performOr(img2);
    	imgin.invert();
    }
    
    public String toStringUnCompressed()
    {
		StringBuilder str = new StringBuilder("");
        str.append(width + " " + height + ",");
        for (int i=0; i<height; i++){
            Node node = array[i].head; char v = '1';
            int j=0;
            if(node!=null){
                while(node!=null){
                    while(j<=node.element-Character.getNumericValue(v)){
                        str.append(" "+v);
                        j++;
                    }
                    node = node.next;
                    if(v=='1'){v='0';}
                    else if(v=='0'){v='1';}
                }
                while(j<width){str.append(" "+'1');j++;}
            }
            else{for(int k=0; k<width; k++){str.append(" 1");}}
            if(i<height-1){str.append(",");}
        }
        //System.out.print(str);
        return str.toString();
    }
    
    public String toStringCompressed()
    {   
        StringBuilder str = new StringBuilder("");
		str.append(Integer.toString(width));
        str.append(" "+height+",");
        for (int i=0; i<height; i++){
            Node node = array[i].head;
            if(node!=null){
                str.append(" "+node.element);
                while(node.next!=null){
                    str.append(" "+node.next.element);
                    node = node.next;
                }
            }
            if(i<height-1){str.append(" "+"-1,");} 
            else{str.append(" "+"-1");}
        }
        //System.out.println(str);
        return str.toString();
    }
    public String print1(int n){
    					 StringBuilder str = new StringBuilder("");
				Node node = array[n].head;
            if(node!=null){
                str.append(" "+node.element);
                while(node.next!=null){
                    str.append(" "+node.next.element);
                    node = node.next;
                }
            }
            if(n<height-1){str.append(" "+"-1,");} 
            else{str.append(" "+"-1");}
            return str.toString();

    }

    public static void main(String[] args) throws IOException{
    	// testing all methods here :
    	boolean success = true;

    	// check constructor from file
    	CompressedImageInterface img1 = new LinkedListImage("sampleInputFile.txt");

    	// check toStringCompressed
    	String img1_compressed = img1.toStringCompressed();
    	String img_ans = "16 16, -1, 5 7 -1, 3 7 -1, 2 7 -1, 2 2 6 7 -1, 6 7 -1, 6 7 -1, 4 6 -1, 2 4 -1, 2 3 14 15 -1, 2 2 13 15 -1, 11 13 -1, 11 12 -1, 10 11 -1, 9 10 -1, 7 9 -1";
    	success = success && (img_ans.equals(img1_compressed));
        //System.out.println(img1.toStringUnCompressed());
        //System.out.println(img1.toStringCompressed());
    	if (!success)
    	{
    		System.out.println("Constructor (file) or toStringCompressed ERROR");
    		return;
    	} 
    	// check getPixelValue
    	boolean[][] grid = new boolean[16][16];
    	for (int i = 0; i < 16; i++){
    		for (int j = 0; j < 16; j++)
    		{
                try
                {
        			grid[i][j] = img1.getPixelValue(i, j);               
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
    		}
        }
        /*for (int i = 0; i < 16; i++){
            for (int j = 0; j < 16; j++){
                System.out.print(grid[i][j]+" ");
            }
            System.out.println();
        }*/
    	// check constructor from grid
    	CompressedImageInterface img2 = new LinkedListImage(grid, 16, 16);
    	String img2_compressed = img2.toStringCompressed();
    	success = success && (img2_compressed.equals(img_ans));	
    	if (!success)
    	{
    		System.out.println("Constructor (array) or toStringCompressed ERROR");
    		return;
    	}else {;}
    	// check Xor
        try
        {	
        	img1.performXor(img2);    
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
    	for (int i = 0; i < 16; i++)
    		for (int j = 0; j < 16; j++)
    		{
                try
                {
        			success = success && (!img1.getPixelValue(i,j));                
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
    		}

    	if (!success)
    	{
    		System.out.println("performXor or getPixelValue ERROR");
    		return;
    	}
    	
    	// check setPixelValue
    	for (int i = 0; i < 16; i++)
        {
            try
            {
    	    	img1.setPixelValue(i, 0, true);            
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }
        /*
        CompressedImageInterface img3 = new LinkedListImage("sampleInputFile.txt");
        System.out.println(img3.toStringUnCompressed());
        System.out.println(img3.toStringCompressed());
        try{img3.setPixelValue(5,5,true);img3.setPixelValue(10, 14,true);}
        catch(PixelOutOfBoundException e){System.out.println("Errorrrrrrrr");}
        System.out.println(img3.toStringUnCompressed());
        System.out.println(img3.toStringCompressed());*/
    	// check numberOfBlackPixels
    	//System.out.println(img1.toStringUnCompressed());
    	//System.out.println(img1.toStringCompressed());


    	if (!success)
    	{
    		System.out.println("setPixelValue or numberOfBlackPixels ERROR");
    		return;
    	}

    	// check invert
        img1.invert();
        for (int i = 0; i < 16; i++)
        {
            try
            {
                success = success && !(img1.getPixelValue(i, 0));            
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }
        if (!success)
        {
            System.out.println("invert or getPixelValue ERROR");
            return;
        }

    	// check Or
        try
        {
            img1.performOr(img2);        
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && img1.getPixelValue(i,j);
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performOr or getPixelValue ERROR");
            return;
        }

        // check And
        try
        {
            img1.performAnd(img2);    
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && (img1.getPixelValue(i,j) == img2.getPixelValue(i,j));             
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performAnd or getPixelValue ERROR");
            return;
        }

    	// check toStringUnCompressed
        String img_ans_uncomp = "16 16, 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1, 1 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1, 1 1 1 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1 1, 1 1 0 0 0 1 1 1 1 1 1 1 1 1 1 1, 1 1 0 0 1 1 1 1 1 1 1 1 1 1 0 0, 1 1 0 1 1 1 1 1 1 1 1 1 1 0 0 0, 1 1 1 1 1 1 1 1 1 1 1 0 0 0 1 1, 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1, 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1, 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1, 1 1 1 1 1 1 1 0 0 0 1 1 1 1 1 1";
        success = success && (img1.toStringUnCompressed().equals(img_ans_uncomp)) && (img2.toStringUnCompressed().equals(img_ans_uncomp));
        //System.out.println(img_ans_uncomp);
        if (!success)
        {
            System.out.println("toStringUnCompressed ERROR");
            //return;
        }
        else
            System.out.println("ALL TESTS SUCCESSFUL! YAYY!");
    }
}