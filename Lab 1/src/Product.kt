class Product(var name: String)
{
    var available: Boolean=true
    var quantity: Int=10


    override fun toString(): String
    {
        return "Producto: $name \n Cantidad: $quantity \n"
    }





}