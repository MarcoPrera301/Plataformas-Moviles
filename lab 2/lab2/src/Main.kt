//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
data class ItemData(
    val originalPos: Int,
    val originalValue: Any,
    val type: ElementType,
    val info: String
)
{
    override fun toString(): String {
        return "'$originalValue' estaba en la posición $originalPos, es de tipo $type e info es $info"
    }
}

enum class ElementType {
    CADENA,
    ENTERO,
    BOOLEANO,
    DESCONOCIDO
}

fun main() {
    val entrada: List<Any?>? = listOf(10, "Enero", null, true)

    val MutableList: MutableList<ItemData>?=processList(entrada)

    if(MutableList!=null) {
        for (i in MutableList) {
            println(i)
            println("")
        }
    }
    else println("la lista es null")

}

fun processList(inputList: List<Any?>?): MutableList<ItemData>? {

    var count: Int=0
    var newList: MutableList<ItemData> = mutableListOf()
    var nullflag: Boolean= true

    if (inputList != null) {

        for(i in inputList){

            val item: ItemData=makeItem(i, count)

            if(item.originalValue!="null" && item.type!=ElementType.DESCONOCIDO) {
                nullflag = false
                newList.add(item)
            }


            count++

        }

        if (nullflag==false) {
            return newList
        }
        else {
            newList.clear()
            return newList
        }
    }

    else{
        return null
    }
}

fun makeItem(element: Any?, count1: Int): ItemData{

        when(element) {

            is String -> {


                val item = ItemData(count1, element, ElementType.CADENA, "l${element.length}")
                return item
            }

            is Int -> {

                val str: String

                if (element % 10 == 0) {
                    str = "m10"
                } else if (element % 5 == 0) {
                    str = "m5"
                } else if (element % 2 == 0) {
                    str = "m2"
                } else {
                    str = "-"
                }

                val item = ItemData(count1, element, ElementType.ENTERO, str)
                return item
            }

            is Boolean -> {

                val str: String
                if (element) str = "verdadero" else str = "falso"

                val item = ItemData(count1, element, ElementType.BOOLEANO, str)
                return item
            }

            else -> {
                val item = ItemData(count1, if(element==null) "null" else element, ElementType.DESCONOCIDO, "desconocido")
                return item
            }
        }
}