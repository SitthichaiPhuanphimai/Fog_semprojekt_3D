import java.sql.*;
import java.util.ArrayList;

public class Item
{

    private float length;
    private float width;
    private float height;
    private int quantity;
    private int typeId;


    public Item(float length, float width, float height, int quantity, int typeId)
    {
        this.length = length;
        this.width = width;
        this.height = height;
        this.quantity = quantity;
        this.typeId = typeId;
    }

    public float getLength() {
        return length;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", quantity=" + quantity +
                '}';
    }

    public static ArrayList<Item> getItemList(String id)
    {

         String USER = "dev";
         String PASSWORD = "3r!DE32*/fDe";
         String URL = "jdbc:mysql://164.92.165.237:3306/fog?user=dev&useSSL=true&requireSSL=true";

        ArrayList<Item> itemsList = new ArrayList<>();

        String sql = "SELECT material_list.order_id, material.description, unit.unit, " +
                "material_type.id, material_length.length, material_list.quantity, material.price_per_unit " +
                "FROM material_list " +
                "JOIN material ON material_list.material_id = material.id " +
                "JOIN unit ON material.unit_id = unit.id " +
                "JOIN material_type ON material.material_type_id = material_type.id " +
                "JOIN material_length ON material.material_length_id = material_length.id " +
                "WHERE material_list.order_id = ?";

        try (Connection conn = DriverManager.getConnection(URL,USER,PASSWORD))
        {
             PreparedStatement statement = conn.prepareStatement(sql);

             statement.setString(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {

                    int typeID = rs.getInt("id");

                    if (typeID <= 3)
                    {
                        String description = rs.getString("description");

                        String[] parts = description.split("x");
                        String y = parts[0];
                        String z = parts[1].split(" ")[0];

                        float width = Float.parseFloat(y) / 10;
                        float height = Float.parseFloat(z) / 10;

                        int quant = rs.getInt("quantity");

                        int length = rs.getInt("length");


                        Item item = new Item(length, width, height, quant, typeID);
                        itemsList.add(item);
                    }

                 
                }
            }

        } catch (SQLException e) {
            System.out.println("Error in the database");
            e.printStackTrace();
        }

        return itemsList;
    }
}
