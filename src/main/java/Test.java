import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

import java.sql.SQLException;
import java.util.ArrayList;

public class Test
{
	public static void main(String[] args) throws SQLException {

		JavaCSG csg = JavaCSGFactory.createDefault();

		ArrayList<Item> itemlist = Item.getItemList("9");

		ArrayList<Geometry3D>csgList = generate(itemlist,csg);


		int counterx = 0;
		int countery = 0;
		for(Geometry3D g : csgList)
		{
			var box = g;
			 box = csg.translate3D(counterx,countery,0).transform(box);

			csg.view(box, csgList.lastIndexOf(g));

			counterx += 50;
			countery += 10;

		}

	}

	public static ArrayList<Geometry3D> generate(ArrayList<Item> itemlist, JavaCSG csg)
	{
		ArrayList<Geometry3D> csgList = new ArrayList<>();

		for (Item item :itemlist)
		{
			for (int i =0; i<item.getQuantity();i++)
			{
				var box = csg.box3D(item.getLength()*100,item.getWidth(),item.getHeight(),false);
				csgList.add(box);
			}

		}

		return csgList;
	}
}

