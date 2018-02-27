class check
{
	public static void main(String[] args)
	{
		try
		{
			for(int k=0;k<100;++k)
			{
				int h=160,w=160;
				boolean img[][]=new boolean[h][w];
				boolean img2[][]=new boolean[h][w];
				for(int i=0;i<h;++i)
				{
					for(int j=0;j<w;++j)
					{
						boolean pixel=false;
						if(Math.random()>=0.5)
						{
							pixel=true;
						}
						img[i][j]=pixel;
						pixel=false;
						if(Math.random()>=0.5)
						{
							pixel=true;
						}
						img2[i][j]=pixel;
					}
				}
				CompressedImageInterface a=new LinkedListImage(img,w,h);
				CompressedImageInterface b=new LinkedListImage(img2,w,h);
				//System.out.println(a.toStringUnCompressed());
				//System.out.println(b.toStringUnCompressed());
				//System.out.println(a.toStringCompressed());
				//System.out.println(b.toStringCompressed());
				for(int i=0;i<h;++i)
				{
					for(int j=0;j<w;++j)
					{
						if(img[i][j]!=a.getPixelValue(i,j))
						{
							System.out.println("fuck getPixelValue");
							System.exit(0);
						}
						if(img2[i][j]!=b.getPixelValue(i,j))
						{
							System.out.println("fuck getPixelValue");
							System.exit(0);
						}
					}
				}
				System.out.println("getPixel Done");
				for(int i=0;i<h;i++)
				{
					for(int j=0;j<w;++j)
					{
						if(Math.random()>=0.5)
						{	//System.out.print(i+",");System.out.print(j+"true\n"+a.toStringCompressed()+"\n");
							a.setPixelValue(i,j,true);
							img[i][j]=true;
						}
						else
						{	//System.out.print(i+",");System.out.print(j+"false\n"+a.toStringCompressed()+"\n");
							a.setPixelValue(i,j,false);
							img[i][j]=false;
						}
					}
				}
				System.out.println("Values set");
				for(int i=0;i<h;++i)
				{
					for(int j=0;j<w;++j)
					{
						if(img[i][j]!=a.getPixelValue(i,j))
						{	System.out.println(img[i][j]);
							System.out.println(a.getPixelValue(i,j));
							System.out.println("fuck setPixelValue");
							System.exit(0);
						}
					}
				}
				System.out.println("SetPixel Done");
				//System.out.println(a.toStringCompressed());
				a.invert();
				//System.out.println(a.toStringUnCompressed());
				for(int i=0;i<h;++i)
				{
					for(int j=0;j<w;++j)
					{
						img[i][j]=(!img[i][j]); //System.out.print(img[i][j]+" ");
					}
					//System.out.println();
				}
				for(int i=0;i<h;++i)
				{
					for(int j=0;j<w;++j)
					{
						if(img[i][j]!=a.getPixelValue(i,j))
						{	System.out.println(i);System.out.println(j);
							System.out.println("fuck invert");
							System.exit(0);
						}
					}
				}
				System.out.println("invert Done");
				int[] black=new int[h];
				for(int i=0;i<h;i++)
				{
					black[i]=0;
					for(int j=0;j<w;++j)
					{
						if(img[i][j]==false)
						{
							black[i]++;
						}
					}
				}
				int p=0;
				int[] temp=a.numberOfBlackPixels();
				for(int i=0;i<h;++i)
				{
					if(temp[i]!=black[i])
					{	System.out.println(i);
						p=i;
						System.out.println("fuck numberOfBlackPixels");
						System.exit(0);
					}
				}


				System.out.println("numberOfBlackPixels Done");
				for(int i=0;i<h;i++)
				{
					for (int j=0;j<w;++j)
					{
						img[i][j]=(img[i][j]|img2[i][j]);
					}
				}
				//LinkedListImage d=(LinkedListImage) a;
				//System.out.println(d.print1(p));
				//System.out.println(a.toStringUnCompressed());
				//System.out.println(b.toStringUnCompressed());
				//System.out.println(a.toStringCompressed());
				//System.out.println(b.toStringCompressed());
				a.performOr(b);
				//System.out.println(a.toStringUnCompressed());
				//System.out.println(a.toStringCompressed());
				for(int i=0;i<h;i++)
				{
					for (int j=0;j<w;++j)
					{
						if(img[i][j]!=a.getPixelValue(i,j))
						{	//System.out.println(i+","+j);
							System.out.println("fuck or");
							System.exit(0);
						}
					}
				}

				System.out.println("or Done");
				for(int i=0;i<h;++i)
				{
					for(int j=0;j<w;++j)
					{
						boolean pixel=false;
						if(Math.random()>=0.5)
						{
							pixel=true;
						}
						img2[i][j]=pixel;
					}
				}
				b=new LinkedListImage(img2,w,h);
				for(int i=0;i<h;i++)
				{
					for (int j=0;j<w;++j)
					{
						img[i][j]=(img[i][j]&img2[i][j]);
					}
				}
				a.performAnd(b);
				for(int i=0;i<h;i++)
				{
					for (int j=0;j<w;++j)
					{
						if(img[i][j]!=a.getPixelValue(i,j))
						{
							System.out.println("fuck and");
							System.exit(0);
						}
					}
				}
				System.out.println("and Done");
				for(int i=0;i<h;++i)
				{
					for(int j=0;j<w;++j)
					{
						boolean pixel=false;
						if(Math.random()>=0.5)
						{
							pixel=true;
						}
						img2[i][j]=pixel;
					}
				}
				b=new LinkedListImage(img2,w,h);
				for(int i=0;i<h;i++)
				{
					for (int j=0;j<w;++j)
					{
						img[i][j]=(img[i][j]^img2[i][j]);
					}
				}
				a.performXor(b);
				for(int i=0;i<h;i++)
				{
					for (int j=0;j<w;++j)
					{
						if(img[i][j]!=a.getPixelValue(i,j))
						{
							System.out.println("fuck xor");
							System.exit(0);
						}
					}
				}
				System.out.println("Xor Done");
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}