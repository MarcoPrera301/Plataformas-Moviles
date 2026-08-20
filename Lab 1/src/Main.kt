//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

fun main()
{

    var finish: Boolean=false
    val products: List<Product> = listOf(Product("ps5"),
                                         Product("nintendo switch"),
                                         Product("xbox"),
                                         Product("pc"),
                                         Product("laptop"))

    println("=====Bienvenido al gestor de inventario=====")
    println("\n\n\n")

    while(finish==false) {
        println("\n")
        println("MENU DE OPCIONES:")
        println("1 = Muestra de Inventario")
        println("2 = Buscar producto")
        println("3 = Modificar producto")
        println("4 = Productos Disponibles")
        println("5 = Salir")

        var selection: String = readln()
        var number: Int? = selection.toIntOrNull()

        when(number){

            1 -> mostrarInventario(products)

            2 -> {
                println("---Ingrese un producto---")
                buscarProducto(readln(), products)
            }

            3 -> {

                    println("---Ingrese el nombre del producto que modificará:---\n")
                    val name: String=readln()
                    println("---Ingrese la nueva cantidad de producto---\n")
                    val numero: Int?= readln().toIntOrNull()

                    modificarProducto(name,numero, products)
                 }

            4 -> productosDisponibles(products)

            5 -> finish=true

        }
    }
}

fun mostrarInventario(products: List<Product>)
{
    println("\n")
    for(i in products)
    {
        println(i.toString())
    }
}

fun buscarProducto(producto: String , products: List<Product>)
{
    println("\n")
    for(i in products){
        if (i.name==producto.lowercase()) println(i.toString())
    }
}

fun modificarProducto(name: String, number: Int?, products: List<Product>)
{
    println("\n")
    if (number==null || number<0 || number>100) {
        println("Ingresó una cantidad invalida de productos (0-100)")
    }
    else if(number==0){
        for (i in products){
            if (i.name==name.lowercase()){
                i.quantity=number
                i.available=false
            }
        }
    }
    else{
        for (i in products){
            if (i.name==name.lowercase()) i.quantity=number
        }
    }
}

fun productosDisponibles(products: List<Product>)
{
    println("\n")
    var contador: Int=0
    for(i in products){
        if(i.available==true){
            println(i.toString())
            contador++
        }
    }

    println("Hay $contador productos disponibles.")
}